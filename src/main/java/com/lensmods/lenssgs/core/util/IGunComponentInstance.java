package com.lensmods.lenssgs.core.util;

import com.lensmods.lenssgs.api.material.MaterialList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface IGunComponentInstance<T extends IGunComponent<?>>{
    @Nullable
    T get();

    ResourceLocation getId();
    ItemStack getItem();
    MaterialList getMaterials();

}
