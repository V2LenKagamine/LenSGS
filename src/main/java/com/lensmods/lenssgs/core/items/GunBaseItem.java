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

    public boolean doesHaveAmmo(ItemStack stacc) {
        return stacc.get(LenDataComponents.AMMO_COUNTER) > 0;
    }

    public int getProjAmt(ItemStack stacc) {
        float baseProjCount = stacc.get(LenDataComponents.PROJ_COUNT);
        return randy.nextFloat(0,1) < baseProjCount ? Mth.floor(baseProjCount) : Mth.ceil(baseProjCount);
    }
    public float rollDmg(ItemStack stacc) {
        float minDmg = stacc.get(LenDataComponents.DMG_MIN);
        float maxDmg = stacc.get(LenDataComponents.DMG_MAX);
        return LenUtil.RandBetween(randy,minDmg,maxDmg);
    }

}
