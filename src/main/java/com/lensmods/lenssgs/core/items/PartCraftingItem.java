package com.lensmods.lenssgs.core.items;

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
        return Component.translatable(pStack.getOrDefault(LenDataComponents.PART_TYPE,"INVALID")+"."+pStack.getOrDefault(LenDataComponents.PART_SUB_TYPE,"none") +".partcrafter");
    }
}
