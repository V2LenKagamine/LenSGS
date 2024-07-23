package com.lensmods.lenssgs.core.data.recipes;

import com.lensmods.lenssgs.core.items.AmmoBaseItem;
import com.lensmods.lenssgs.core.weaponsystems.WeaponAmmoStats;
import com.lensmods.lenssgs.datagen.LenTagKeys;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.lensmods.lenssgs.init.LenRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.List;

public class RefillRecipe extends CustomRecipe {
    private List<Ingredient> inputs;
    private Item output;
    private int toRestore;

    public RefillRecipe(Collection<Ingredient> inputs, Item output, int toRestore) {
        super(CraftingBookCategory.EQUIPMENT);
        this.inputs=inputs.stream().toList();
        this.output = output;
        this.toRestore = toRestore;
    }
    public RefillRecipe(Collection<Ingredient> inputs, ItemStack output, int toRestore) {
        super(CraftingBookCategory.EQUIPMENT);
        this.inputs=inputs.stream().toList();
        this.output = output.getItem();
        this.toRestore = toRestore;
    }

    public int getToRestore() {
        return toRestore;
    }
    public ItemStack getOutput() {
        return new ItemStack(output,1);
    }

    public List<Ingredient> getInputs() {
        return inputs;
    }

    @Override
    public boolean matches(CraftingInput given, Level p_345375_) {
        boolean ammo= false;
        boolean restore= false;
        for(ItemStack item : given.items()) {
            if(item.is(Items.AIR)) {continue;}
            if(item.is(LenTagKeys.REFILLS_AMMO_TAG)&& inputs.stream().anyMatch(ing-> ing.test(item))){restore=true;continue;}
            if(item.getItem() instanceof AmmoBaseItem) {ammo=true;continue;}
            return false;
        }
        return ammo && restore;
    }

    @Override
    public ItemStack assemble(CraftingInput given, HolderLookup.Provider provider) {
        ItemStack mainboi = ItemStack.EMPTY;
        int amnt =0;
        for(ItemStack item : given.items()){
            if (item.getItem() instanceof AmmoBaseItem){mainboi=item.copy();continue;}
            amnt++;
        }
        if(mainboi.getOrDefault(LenDataComponents.AMMO_COUNTER,null)!=null && mainboi.getOrDefault(LenDataComponents.GUN_STAT_TRAITS,null)!=null) {
            if (mainboi.get(LenDataComponents.AMMO_COUNTER) < mainboi.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max()) {
                var current = mainboi.get(LenDataComponents.AMMO_COUNTER);
                int lacking = mainboi.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max() - current;
                int perRestore = (int)Math.max(Math.floor((this.toRestore/100f)*(float)mainboi.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max()), WeaponAmmoStats.AMMO_POINTS_MUL);
                int torestore = lacking < 0 ? mainboi.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max() : Math.min(lacking,amnt*perRestore);
                mainboi.set(LenDataComponents.AMMO_COUNTER,torestore+current);
                return mainboi;
            }
        }
        return mainboi.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LenRecipes.REFILL_RECIPE_SERIALIZER.get();
    }
}
