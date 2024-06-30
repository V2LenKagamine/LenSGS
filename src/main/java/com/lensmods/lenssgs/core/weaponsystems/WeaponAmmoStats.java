package com.lensmods.lenssgs.core.weaponsystems;

import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class WeaponAmmoStats {

    public static Random randy = new Random();
    public static boolean doesHaveAmmo(ItemStack stacc) {
        return stacc.get(LenDataComponents.AMMO_COUNTER) > 0;
    }

    public static float getVelMult(ItemStack stacc) {
        return stacc.get(LenDataComponents.VEL_MULT);
    }

    public static int getProjAmt(ItemStack stacc) {
        float baseProjCount = stacc.get(LenDataComponents.PROJ_COUNT);
        return randy.nextFloat(0,1) < baseProjCount ? Mth.floor(baseProjCount) : Mth.ceil(baseProjCount);
    }
    public static float rollDmg(ItemStack stacc) {
        float minDmg = stacc.get(LenDataComponents.DMG_MIN);
        float maxDmg = stacc.get(LenDataComponents.DMG_MAX);
        return LenUtil.RandBetween(randy,minDmg,maxDmg);
    }
}
