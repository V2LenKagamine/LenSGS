package com.lensmods.lenssgs.api.item;

import com.lensmods.lenssgs.api.part.IPartData;
import com.lensmods.lenssgs.core.util.GunData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Collection;

public interface ICoreItem extends ItemLike {
    default ItemStack construct(Collection<? extends IPartData> parts) {
        ItemStack result = new ItemStack(this);
        GunData.WriteParts(result,parts);
        return result;
    }
}
