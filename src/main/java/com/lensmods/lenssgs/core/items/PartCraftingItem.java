package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PartCraftingItem extends Item {
    public PartCraftingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack pStack) {
        return LenUtil.translatableOf(pStack.getOrDefault(LenDataComponents.PART_SUB_TYPE,"bad")).copy().append(LenUtil.spaceAppend(LenUtil.translatableOf("part_crafter")));
    }
}
