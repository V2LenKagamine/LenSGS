package com.lensmods.lenssgs.core.data.recipes;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.datacomps.GunComp;
import com.lensmods.lenssgs.core.datacomps.GunMaterial;
import com.lensmods.lenssgs.core.datacomps.GunPartHolder;
import com.lensmods.lenssgs.core.items.AmmoBaseItem;
import com.lensmods.lenssgs.core.items.GunBaseItem;
import com.lensmods.lenssgs.core.items.GunPartBaseItem;
import com.lensmods.lenssgs.core.items.PartCraftingItem;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.core.weaponsystems.WeaponAmmoStats;
import com.lensmods.lenssgs.datagen.LenTagKeys;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.lensmods.lenssgs.init.LenDataReg;
import com.lensmods.lenssgs.init.LenItems;
import com.lensmods.lenssgs.init.LenRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GunRecipe extends CustomRecipe {
    private List<Ingredient> inputs;
    private ItemStack output;
    private boolean swapping;
    public GunRecipe(Collection<Ingredient> inputs, ItemStack output, boolean swapping) {
        super(CraftingBookCategory.EQUIPMENT);
        this.inputs=inputs.stream().toList();
        this.output = output;
        this.swapping=swapping;
    }
    public List<Ingredient> getInputs() {
        return inputs;
    }
    public ItemStack getOutput() {
        return this.output;
    }
    public boolean getSwapping() {return this.swapping;}
    public GunRecipe(Collection<Ingredient> inputs,ItemStack output) {
        this(inputs.stream().toList(),output,false);
    }
    public static GunRecipe fromItems(Collection<? extends Item> inputs, Item output,boolean swapping) {
        Collection<Ingredient> inp = new ArrayList<>();
        for (Item it : inputs) {
            inp.add(Ingredient.of(it));
        }
        return new GunRecipe(inp,new ItemStack(output),swapping);
    }
    public static GunRecipe fromIngs(Collection<Ingredient> inputs, Item output,boolean swapping) {
        Collection<Ingredient> inp = new ArrayList<>();
        for (Ingredient it : inputs) {
            inp.add(it);
        }
        return new GunRecipe(inp,new ItemStack(output,1),swapping);
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        if(inputs.size() != craftingInput.items().size()) {return false;} //No more, no less.
        for (ItemStack item : craftingInput.items()) {
            boolean itemGood = false;
            for (Ingredient ing : inputs) {
                if (Arrays.stream(ing.getItems()).anyMatch(test -> item.is(test.getItem()))) {
                    itemGood = true;
                    break;
                }
            }
            if (!itemGood) {return false;}
        }
        if (output.getItem() instanceof GunBaseItem && !swapping) {
            List<String> weHave = new ArrayList<>();
            for (ItemStack item : craftingInput.items()) { //Making gun check if its valid.
                if (item.getItem() instanceof GunPartBaseItem) {
                    String partName = item.get(LenDataComponents.GUN_PART_HOLDER).getName();
                    if (!weHave.contains(partName) && AllowedParts.GUN_MANDITORY.contains(partName)) {
                        weHave.add(partName);
                        continue; //Yep, we need this part.
                    }
                    return false;
                }
            }
            if(weHave.size() == AllowedParts.GUN_MANDITORY.size()) {
                return true;
            }
        }
        if (output.getItem() instanceof GunBaseItem && swapping) {
            return craftingInput.items().stream().filter(stack -> stack.getItem() instanceof GunBaseItem).count() > 0;
        }
        if (output.getItem() instanceof AmmoBaseItem && !swapping) {
            List<String> weHave = new ArrayList<>();
            for (ItemStack item : craftingInput.items()) { //Making Ammo check if its valid.
                String partName = item.get(LenDataComponents.GUN_PART_HOLDER).getName();
                if(!weHave.contains(partName) && AllowedParts.AMMO_MANDITORY.contains(partName)) {
                    weHave.add(partName);
                    continue; //Yep, we need this part.
                }
                return false;
            }
            if(weHave.size() == AllowedParts.AMMO_MANDITORY.size()) {
                return true;
            }
        }
        if (output.getItem() instanceof AmmoBaseItem && swapping) {
            return craftingInput.items().stream().filter(stack -> stack.getItem() instanceof AmmoBaseItem).count() > 0;
        }
        if (output.getItem() instanceof GunPartBaseItem) {
            Item mat = Items.AIR;
            for (ItemStack item : craftingInput.items()) {
                if (item.getItem() instanceof PartCraftingItem) {
                    continue; //Yea we need that
                }
                if (mat == Items.AIR) {
                    mat = item.getItem();
                }
                else if (!item.is(mat))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        String whatrewedoin = "nothing";
        ItemStack mainboi =ItemStack.EMPTY;
        for(ItemStack maygun : craftingInput.items()) {
            if (maygun.getItem() instanceof GunBaseItem && swapping) {
                whatrewedoin = "gunmod";
                mainboi = maygun.copy();
                break;
            }else
            if(maygun.getItem() instanceof AmmoBaseItem && swapping) {
                whatrewedoin = "ammomod";
                mainboi = maygun.copy();
                break;
            }else if(maygun.getItem() instanceof PartCraftingItem && !swapping) {
                whatrewedoin = "makinpart";
                mainboi = maygun.copy();
                break;
            }else if (maygun.getItem() instanceof GunPartBaseItem && !swapping) {
                whatrewedoin = "makinmain";
                mainboi = maygun.copy();
                break;
            }
        }
        List<ItemStack> allButMain = new ArrayList<>(craftingInput.items());
        allButMain.remove(mainboi);
        switch (whatrewedoin) {
            case "ammomod":
            case "gunmod": {
                return swapOrAdd(craftingInput,mainboi);
            }
            case "makinpart" : {
                ItemStack out = new ItemStack(LenItems.PART_BASE.asItem());
                GunMaterial bestmatch = provider.lookup(LenDataReg.GUN_MAT_KEY).get()
                        .listElements().filter(data -> (Arrays.stream(data.value().getIngredient().getItems()).anyMatch(item ->
                                item.is(ItemTags.create(LenTagKeys.GUNAMMO_MAT_TAG.location()))))).findFirst().get().value();
                out.set(LenDataComponents.GUN_PART_HOLDER,new GunPartHolder(mainboi.get(LenDataComponents.PART_TYPE), mainboi.get(LenDataComponents.PART_SUB_TYPE),bestmatch));
                return out;
            }
            case "makinmain": {
                ItemStack out = new ItemStack(output.getItem());
                List<GunPartHolder> hold= new ArrayList<>();
                for(ItemStack part : craftingInput.items()) {
                    hold.add(part.get(LenDataComponents.GUN_PART_HOLDER));
                }
                out.set(LenDataComponents.GUN_COMP,new GunComp(hold));
                WeaponAmmoStats.recalculateGunData(out);
                return out;
            }
            default: case "nothing": {
                //Fuck. Bad. Log error.
                LensSGS.L3NLOGGER.error("You shouldn't see this in game, if you do, tell coderman.");
            }
        }
        LensSGS.L3NLOGGER.error("Something went VERY wrong making a gun or part... Maybe tell the coder.");
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LenRecipes.GUN_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> list = NonNullList.withSize(input.ingredientCount(), ItemStack.EMPTY);
        ItemStack gunthing = input.items().stream().filter(item -> item.getItem() instanceof AmmoBaseItem || item.getItem() instanceof GunBaseItem)
                .findFirst().orElseGet(()->new ItemStack(Items.AIR));
        if(gunthing.is(Items.AIR)) { //Oh we aren't changing parts.
            return list;
        }
        GunComp gunDat = gunthing.get(LenDataComponents.GUN_COMP);
        for(int i =0;i <list.size();i++) {
            ItemStack boi = input.getItem(i);
            if(boi.getItem() instanceof AmmoBaseItem || boi.getItem() instanceof GunBaseItem) {
                list.set(i,ItemStack.EMPTY);
            } else {
                for(GunPartHolder part : gunDat.getPartList()) {
                    if(part.getName().equals(boi.get(LenDataComponents.GUN_PART_HOLDER).getName())) { //Return part from gun.
                        ItemStack returned = new ItemStack(LenItems.PART_BASE.get());
                        returned.set(LenDataComponents.GUN_PART_HOLDER,part);
                        list.set(i,returned);
                    }
                }
            }
        }
        return list;
    }
    private ItemStack swapOrAdd(CraftingInput craftingInput,ItemStack mainboi) {
        for(ItemStack ing : craftingInput.items()) {
            if(ing.getItem() instanceof GunPartBaseItem) {//Ok, adding or swapping part here.
                GunComp gunData = mainboi.get(LenDataComponents.GUN_COMP);
                for (GunPartHolder part : gunData.getPartList()) {
                    if(ing.get(LenDataComponents.GUN_PART_HOLDER).getName().equals(part.getName())) { //Ok, we already have a part, swap it.
                        GunPartHolder temp = ing.get(LenDataComponents.GUN_PART_HOLDER);
                        mainboi.set(LenDataComponents.GUN_COMP,LenUtil.swapData(temp,gunData));
                        WeaponAmmoStats.recalculateGunData(mainboi);
                        return mainboi;
                    }
                }
                mainboi.set(LenDataComponents.GUN_COMP, LenUtil.swapData(ing.get(LenDataComponents.GUN_PART_HOLDER),gunData));
                WeaponAmmoStats.recalculateGunData(mainboi);
                return mainboi;
            }
        }
        return ItemStack.EMPTY; //Not good.
    }
}
