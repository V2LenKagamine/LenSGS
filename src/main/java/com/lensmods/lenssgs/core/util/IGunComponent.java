package com.lensmods.lenssgs.core.util;

import com.lensmods.lenssgs.api.item.GunType;
import com.lensmods.lenssgs.api.material.MaterialList;
import com.lensmods.lenssgs.api.part.PartType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;

public interface IGunComponent<D> {
    Ingredient getIngredient();
    MaterialList getMaterials(D instance);
    boolean craftAllowed(D instance, PartType partType, GunType gunType, @Nullable Container inv);
    default boolean craftAllowed(D instance, PartType partType,GunType gunType) {
        return craftAllowed(instance,partType,gunType);
    }
    Component getDisplayName(@Nullable D instance, PartType type, ItemStack gear);
}
