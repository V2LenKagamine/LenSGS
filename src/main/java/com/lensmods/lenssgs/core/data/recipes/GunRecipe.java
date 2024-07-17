package com.lensmods.lenssgs.core.data.recipes;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.data.AllowedPartsMap;
import com.lensmods.lenssgs.core.data.MaterialMap;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.*;

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
        Collection<Ingredient> inp = new ArrayList<>(inputs);
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
                if(item.getItem() == Items.AIR) {continue;}
                if (item.getItem() instanceof GunPartBaseItem) {
                    String partName = item.getOrDefault(LenDataComponents.GUN_PART_HOLDER,null)!=null ? item.get(LenDataComponents.GUN_PART_HOLDER).getName(): "FUCK";
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
            return craftingInput.items().stream().anyMatch(stack -> stack.getItem() instanceof GunBaseItem);
        }
        if (output.getItem() instanceof AmmoBaseItem && !swapping) {
            List<String> weHave = new ArrayList<>();
            boolean reloading =false;
            for (ItemStack item : craftingInput.items()) { //Making Ammo check if its valid.
                if(item.getItem() == Items.AIR) {continue;}
                if(item.is(LenTagKeys.REFILLS_AMMO_TAG)) {
                    reloading = true;
                    continue;
                }
                String partName = item.getOrDefault(LenDataComponents.GUN_PART_HOLDER,null)!=null ? item.get(LenDataComponents.GUN_PART_HOLDER).getName(): "FUCK";
                if(item.getItem() instanceof AmmoBaseItem || (!weHave.contains(partName) && AllowedParts.AMMO_MANDITORY.contains(partName))) {
                    weHave.add(partName);
                    continue; //Yep, we need this part.
                }
                return false;
            }
            if(weHave.size() == AllowedParts.AMMO_MANDITORY.size() || reloading) {
                return true;
            }
        }
        if (output.getItem() instanceof AmmoBaseItem && swapping) {
            return craftingInput.items().stream().anyMatch(stack -> stack.getItem() instanceof AmmoBaseItem);
        }
        if (output.getItem() instanceof GunPartBaseItem) {
            ItemStack crafter = new ItemStack(Items.AIR);
            ItemStack match = crafter;
            for (ItemStack item : craftingInput.items()) {
                if (item.getItem() instanceof PartCraftingItem) {
                    crafter = item.copy();
                    continue; //Yea we need that
                }
                if (item.getItem() != Items.AIR) {
                    match = item.copy();
                    break;
                }
            }
            if(crafter.getOrDefault(LenDataComponents.PART_TYPE,null) != null && match.getItem() != Items.AIR) {
                 return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        String whatrewedoin = "nothing";
        ItemStack mainboi =ItemStack.EMPTY;
        label:
        for(ItemStack maygun : craftingInput.items()) {
            switch (maygun.getItem()) {
                case GunBaseItem gunBaseItem when swapping:{
                    whatrewedoin = "gunmod";
                    mainboi = maygun.copy();
                    break label;}
                case AmmoBaseItem ammoBaseItem:{
                    whatrewedoin = "ammomod";
                    mainboi = maygun.copy();
                    break label;}
                case PartCraftingItem partCraftingItem when !swapping:{
                    whatrewedoin = "makinpart";
                    mainboi = maygun.copy();
                    break label;}
                case GunPartBaseItem gunPartBaseItem when !swapping:{
                    whatrewedoin = "makinmain";
                    mainboi = maygun.copy();
                    break label;}
                default:
                    break;
            }
        }
        Map<String,GunMaterial> theMap = MaterialMap.loadedMats(provider);
        List<ItemStack> allButMain = new ArrayList<>(craftingInput.items());
        allButMain.remove(mainboi);
        switch (whatrewedoin) {
            case "ammomod":
            case "gunmod": {
                return swapOrAdd(craftingInput,mainboi,provider);
            }
            case "makinpart" : {
                ItemStack out = new ItemStack(LenItems.PART_BASE.asItem());
                GunMaterial bestmatch = null;
                for(ItemStack stacc : allButMain) {
                    var mayhaps = provider.lookup(LenDataReg.GUN_MAT_KEY).get().listElements().filter(
                            data -> (Arrays.stream(data.value().getIngredient().getItems()).filter(item -> item.is(stacc.getItem())))
                            .anyMatch(maybe -> stacc.is(maybe.getItem()))).findFirst();
                    if(mayhaps.isPresent())
                    {
                        bestmatch = mayhaps.get().value();
                    }
                }
                if(bestmatch != null) {
                    String partT = mainboi.get(LenDataComponents.PART_TYPE);
                    String partS = mainboi.get(LenDataComponents.PART_SUB_TYPE);
                    if (AllowedPartsMap.loadedMats(provider).get(bestmatch).stream().noneMatch(type -> type.equals(partS) || type.equals(partT) || type.equals("all"))){
                        return new ItemStack(Items.AIR); //This part isnt allowed
                    }
                    out.set(LenDataComponents.GUN_PART_HOLDER, new GunPartHolder(mainboi.get(LenDataComponents.PART_TYPE), mainboi.get(LenDataComponents.PART_SUB_TYPE), bestmatch.getMatName()));
                }
                return out;
            }
            case "makinmain": {
                ItemStack out = new ItemStack(output.getItem());
                List<GunPartHolder> hold= new ArrayList<>();
                for(ItemStack part : craftingInput.items()) {
                    hold.add(part.get(LenDataComponents.GUN_PART_HOLDER));
                }
                out.set(LenDataComponents.GUN_COMP,new GunComp(hold));
                WeaponAmmoStats.recalculateGunData(out,provider);
                if(output.getItem() instanceof AmmoBaseItem) {
                    out.set(LenDataComponents.AMMO_COUNTER,out.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max());
                }
                return out;
            }
            default: case "nothing": {
                //Fuck. Bad. Log error.
                LensSGS.L3NLOGGER.error("You shouldn't see this in game, if you do, tell coderman.");
                break;
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
            if(boi.getItem() instanceof AmmoBaseItem || boi.getItem() instanceof GunBaseItem || boi.is(LenTagKeys.REFILLS_AMMO_TAG)) {
                list.set(i,ItemStack.EMPTY);
            } else{
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
    private ItemStack swapOrAdd(CraftingInput craftingInput,ItemStack mainboi,HolderLookup.Provider prov) {
        for(ItemStack ing : craftingInput.items()) {
            if(ing.is(LenTagKeys.REFILLS_AMMO_TAG) && mainboi.getItem() instanceof AmmoBaseItem) {
                if(mainboi.getOrDefault(LenDataComponents.AMMO_COUNTER,null)!=null && mainboi.getOrDefault(LenDataComponents.GUN_STAT_TRAITS,null)!=null) {
                    if (mainboi.get(LenDataComponents.AMMO_COUNTER) < mainboi.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max()) {
                        var current = mainboi.get(LenDataComponents.AMMO_COUNTER);
                        int lacking = mainboi.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max() - current;
                        int torestore = lacking < 0 ? mainboi.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max() : Math.min(lacking,32*WeaponAmmoStats.AMMO_POINTS_MUL);
                        mainboi.set(LenDataComponents.AMMO_COUNTER,torestore+current);
                        return mainboi;
                    }
                }
            }
            if(ing.getItem() instanceof GunPartBaseItem) {//Ok, adding or swapping part here.
                if(mainboi.getItem() instanceof GunBaseItem) {
                    if(ing.getOrDefault(LenDataComponents.GUN_PART_HOLDER,null)!=null) {
                        if(!AllowedParts.ANY_GUN_PART.contains(ing.get(LenDataComponents.GUN_PART_HOLDER).getName())) {return ItemStack.EMPTY;}
                    }
                }else if (mainboi.getItem() instanceof AmmoBaseItem) {
                    if(ing.getOrDefault(LenDataComponents.GUN_PART_HOLDER,null)!=null) {
                        if(!AllowedParts.ANY_BULLET_PART.contains(ing.get(LenDataComponents.GUN_PART_HOLDER).getName())) {return ItemStack.EMPTY;}
                    }
                }
                GunComp gunData = mainboi.get(LenDataComponents.GUN_COMP);
                for (GunPartHolder part : gunData.getPartList()) {
                    if(ing.get(LenDataComponents.GUN_PART_HOLDER).getName().equals(part.getName())) { //Ok, we already have a part, swap it.
                        GunPartHolder temp = ing.get(LenDataComponents.GUN_PART_HOLDER);
                        mainboi.set(LenDataComponents.GUN_COMP,LenUtil.swapData(temp,gunData));
                        WeaponAmmoStats.recalculateGunData(mainboi,prov);
                        return mainboi;
                    }
                }//Adding, right?
                mainboi.set(LenDataComponents.GUN_COMP, LenUtil.addData(ing.get(LenDataComponents.GUN_PART_HOLDER),gunData));
                WeaponAmmoStats.recalculateGunData(mainboi,prov);
                return mainboi;
            }
        }
        return ItemStack.EMPTY; //Not good.
    }
}
