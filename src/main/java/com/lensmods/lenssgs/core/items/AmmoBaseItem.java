package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class AmmoBaseItem extends Item {

    private Random randy;

    public AmmoBaseItem(Properties pProperties) {
        super(pProperties);
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
    public boolean hasGravity(ItemStack stacc) {
        return stacc.get(LenDataComponents.GRAV_MOD) <= 0;
    }
}
