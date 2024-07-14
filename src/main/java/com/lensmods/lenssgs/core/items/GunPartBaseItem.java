package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.api.IModdable;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GunPartBaseItem extends Item implements IModdable {
    public GunPartBaseItem(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public Component getName(ItemStack pStack) {
        var maybe = pStack.getOrDefault(LenDataComponents.GUN_PART_HOLDER,null);
        return maybe != null ? pStack.get(LenDataComponents.GUN_PART_HOLDER).getTotalName() : Component.literal("ERRORBAD");
    }
}
