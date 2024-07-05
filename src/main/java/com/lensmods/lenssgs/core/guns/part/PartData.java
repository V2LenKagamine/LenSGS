package com.lensmods.lenssgs.core.guns.part;

import com.lensmods.lenssgs.api.material.MaterialList;
import com.lensmods.lenssgs.api.part.IGunPart;
import com.lensmods.lenssgs.api.part.IPartData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class PartData implements IPartData {
    private static final Map<ResourceLocation,PartData> CACHE = new HashMap<>();
    private final IGunPart part;
    private final ItemStack craftingBoi;

    private PartData(IGunPart part) {
        this(part, ItemStack.EMPTY);
    }

    private PartData(IGunPart part, ItemStack craftingItem) {
        this.part = part;
        this.craftingBoi = craftingItem.copy();
        if (!this.craftingBoi.isEmpty()) {
            this.craftingBoi.setCount(1);
        }
    }

    public static PartData of(IGunPart part) {
        ResourceLocation name = part.getId();
        if(CACHE.containsKey(name)) {
            return CACHE.get(name);
        }
        PartData inst = new PartData(part);
        CACHE.put(name,inst);
        return inst;
    }
    public static PartData of(IGunPart part, ItemStack crafty) {
       return new PartData(part,crafty);
    }

    @Nullable public static PartData from(ItemStack crafty) {
        IGunPart part = PartManager.from(crafty);
        if(part == null) {
            return null;
        }
        return of(part,crafty);
    }

    @Nullable
    @Override
    public IGunPart get() {
        return part;
    }

    @Override
    public ResourceLocation getId() {
        return part.getId();
    }

    @Override
    public ItemStack getItem() {
        return craftingBoi;
    }

    @Override
    public MaterialList getMaterials() {
        return part.getMaterials(this);
    }
}
