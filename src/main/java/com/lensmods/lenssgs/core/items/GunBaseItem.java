package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class GunBaseItem extends Item{

    private Random randy = new Random();

    public GunBaseItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

}
