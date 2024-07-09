package com.lensmods.lenssgs.core.weaponsystems;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.data.MaterialStats;
import com.lensmods.lenssgs.core.datacomps.GunComp;
import com.lensmods.lenssgs.core.datacomps.GunPartHolder;
import com.lensmods.lenssgs.core.datacomps.GunStats;
import com.lensmods.lenssgs.core.datacomps.StatMod;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class WeaponAmmoStats {

    public static int AMMO_POINTS_MUL = 10; //How many 'points' is one ammo.

    public static boolean doesHaveAmmo(ItemStack stacc) {
        return stacc.getOrDefault(LenDataComponents.AMMO_COUNTER,0) > 0;
    }
    public static int ammoAmountLeft(ItemStack stacc) {
        return stacc.getOrDefault(LenDataComponents.AMMO_COUNTER,0);
    }
    public static int getAmmoMax(ItemStack stacc) {
        return !(stacc.getOrDefault(LenDataComponents.GUN_STATS,"egg").equals("egg")) ? stacc.get(LenDataComponents.GUN_STATS).getAmmo_max() : 0;
    }
    public static float getVelMult(ItemStack stacc) {
        return !(stacc.getOrDefault(LenDataComponents.GUN_STATS,"egg").equals("egg")) ? stacc.get(LenDataComponents.GUN_STATS).getVelocityMult() : 1;
    }

    public static int getPierce(ItemStack stacc) {
        return safeGunStats(stacc) ? stacc.get(LenDataComponents.GUN_STATS).getPierce() : 0;
    }

    public static Double getGrav(ItemStack stacc) {
        return safeGunStats(stacc) ? stacc.get(LenDataComponents.GUN_STATS).getGravMod() : 0;
    }


    public static boolean safeGunStats(ItemStack stacc) {
        return !(stacc.getOrDefault(LenDataComponents.GUN_STATS,"fuck").equals("fuck"));
    }


    public static float getAccuracy(ItemStack stacc) {
        return !(stacc.getOrDefault(LenDataComponents.GUN_STATS,"egg").equals("egg")) ? stacc.get(LenDataComponents.GUN_STATS).getInaccuracy() : 0;
    }

    public static int getProjAmt(ItemStack stacc) {
        float baseProjCount = stacc.get(LenDataComponents.GUN_STATS).getProjCount();
        return LensSGS.RANDY.nextFloat(0,1) < baseProjCount ? Mth.floor(baseProjCount) : Mth.ceil(baseProjCount);
    }
    public static float rollDmg(ItemStack stacc) {
        float maxDmg = stacc.get(LenDataComponents.GUN_STATS).getDamageMax();
        float minDmg = stacc.get(LenDataComponents.GUN_STATS).getDamageMin();
        return maxDmg > minDmg ? LenUtil.randBetween(minDmg,maxDmg) : minDmg;
    }

    public static void recalculateGunData(ItemStack gun) { //Please only call this during crafting FTLOG.
        GunComp data = gun.getOrDefault(LenDataComponents.GUN_COMP,new GunComp(List.of()));
        if (data.getPartList().isEmpty()) {
            LensSGS.L3NLOGGER.error("Someone is crafting a gun with no parts. Not good.");
        } //WALL OF VARIABLES
        int newAmmoMax = AMMO_POINTS_MUL;
        int bonusAmmoMax =0;
        float ammoMul =1;
        float ammoMulFinal=1;
        int newPeirce = 0;
        int bonusPeirce =0;
        float peirceMul =1;
        float periceMulFinal=1;
        int newFR = 30;
        int bonusFR =0;
        float frmul =1;
        float frMulFinal=1;
        float newInacc =0;
        float bonusInacc =0;
        float inaccMod =1;
        float inaccModFinal =1;
        float newProj =1;
        float bonusProj=0;
        float projMul =1;
        float projMulFinal=1;
        float newdmgMin=1f;
        float newdmgMax=1.5f;
        float bonusDmgMin =0;
        float bonusDmgMax =0;
        float damageMulMax =1f;
        float damageMulMin =1f;
        float damageMulMaxFinal =1f;
        float damageMulMinFinal =1f;
        float newVelMul=0f;
        float bonusVel =0;
        float velMul =1f;
        float velMulFinal =1f;
        double newGrav=0d;
        double bonusGrav=0d;
        float gravMod=1f;
        float gravModFinal =1f;
        //THE WALL ENDS
        for(GunPartHolder part : data.getPartList()) {
            for (StatMod stats : part.getMaterial().getStatModList()) {
                if (!stats.allowedParts().contains(part.getName())) {
                    continue;
                }
                switch (stats.stat()) {
                    case MaterialStats.AMMO_MAX: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newAmmoMax += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.MUL)) {
                            ammoMul += stats.val();
                        }
                    }
                    case MaterialStats.PIERCE: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newPeirce += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.MUL)) {
                            peirceMul += stats.val();
                        }
                    }
                    case MaterialStats.FIRE_RATE: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newFR += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.MUL)) {
                            frmul += stats.val();
                        }
                    }
                    case MaterialStats.PROJ_COUNT: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newProj += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.MUL)) {
                            projMul += stats.val();
                        }
                    }
                    case MaterialStats.MAX_DMG: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newdmgMax += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.MUL)) {
                            damageMulMax += stats.val();
                        }
                    }
                    case MaterialStats.MIN_DMG: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newdmgMin += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.MUL)) {
                            damageMulMin += stats.val();
                        }
                    }
                    case MaterialStats.GRAVITY_MOD: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newGrav += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.MUL)) {
                            gravMod += stats.val();
                        }
                    }
                    case MaterialStats.VEL_MULT: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newVelMul += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.MUL)) {
                            velMul += stats.val();
                        }
                    }
                    case MaterialStats.INACCURACY_DEG: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newInacc += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.MUL)) {
                            inaccMod += stats.val();
                        }
                    }
                }
            }
        }
        int ammoParts =0;
        int pierceParts = 0;
        int fireParts =0;
        int inaccParts =0;
        int projParts =0;
        int dmgMaxParts =0;
        int dmgMinParts =0;
        int velParts =0;
        int gravParts =0;
        for(GunPartHolder part : data.getPartList()) { //Yes we JUST iterated over it, but we need these calculated AFTER the stats are set.
            switch (part.getSubType()) { //The unit of a switch case. Yes these are hardcoded. Dont wanna do data-stuff for them. maybe TODO?
                case AllowedParts.RECIEVER_PISTOL: {
                    damageMulMinFinal += 0.8f;
                    dmgMinParts++;
                    newInacc += 2.5f;
                    inaccParts++;
                    break;
                }
                case AllowedParts.RECIEVER_STANDARD: {
                    damageMulMaxFinal += 1.05f;
                    dmgMaxParts++;
                    damageMulMaxFinal += 1.05f;
                    dmgMinParts++;
                    break;
                }
                case AllowedParts.RECIEVER_BULLPUP: {
                    newInacc -= 0.75f;
                    inaccParts++;
                    break;
                }
                case AllowedParts.ACTION_MANUAL: {
                    ammoMulFinal -= 0.9f;
                    ammoParts++;
                    damageMulMaxFinal += 1.75f;
                    dmgMaxParts++;
                    damageMulMinFinal += 1.85f;
                    dmgMinParts++;
                    break;
                }
                case AllowedParts.ACTION_SINGLE: {
                    damageMulMaxFinal += 1.25f;
                    dmgMaxParts++;
                    damageMulMinFinal += 1.4f;
                    dmgMinParts++;
                    break;
                }
                case AllowedParts.ACTION_AUTOMATIC: {
                    //No bonus for you! maybe automatic later?
                    break;
                }
                case AllowedParts.BARREL_STUB: {
                    inaccModFinal += 1.2f;
                    inaccParts++;
                    gravModFinal += 1.1f;
                    gravParts++;
                    damageMulMaxFinal += 0.75f;
                    dmgMaxParts++;
                    break;
                }
                case AllowedParts.BARREL_SHORT: {
                    inaccModFinal += 1.1f;
                    inaccParts++;
                    gravModFinal += 1.05f;
                    gravParts++;
                    damageMulMaxFinal += 0.875f;
                    dmgMaxParts++;
                    break;
                }
                case AllowedParts.BARREL_FAIR: {
                    damageMulMaxFinal += 1.05f;
                    dmgMaxParts++;
                    damageMulMinFinal += 1.05f;
                    dmgMinParts++;
                    break;
                }
                case AllowedParts.BARREL_LONG: {
                    inaccModFinal += 0.9f;
                    inaccParts++;
                    gravModFinal += 0.95f;
                    gravParts++;
                    damageMulMaxFinal += 1.125f;
                    dmgMaxParts++;
                    break;
                }
                case AllowedParts.BARREL_EXTENDED: {
                    inaccModFinal += 0.8f;
                    inaccParts++;
                    gravModFinal += 0.90f;
                    gravParts++;
                    damageMulMaxFinal += 1.25f;
                    dmgMaxParts++;
                    break;
                }
                case AllowedParts.STOCK_SHORT: {
                    inaccModFinal += 0.95f;
                    inaccParts++;
                    frMulFinal += 1.15f;
                    fireParts++;
                    break;
                }
                case AllowedParts.STOCK_FULL: {
                    inaccModFinal += 0.90f;
                    inaccParts++;
                    frMulFinal += 1.1f;
                    fireParts++;
                    break;
                }
                case AllowedParts.MAGAZINE_SHORT: {
                    bonusAmmoMax += 5 * AMMO_POINTS_MUL;
                    ammoMulFinal += 0.9f;
                    ammoParts++;
                    frMulFinal += 1.1f;
                    fireParts++;
                    bonusInacc -= 0.25f;
                    break;
                }
                case AllowedParts.MAGAZINE_NORMAL: {
                    bonusAmmoMax += 10 * AMMO_POINTS_MUL;
                    ammoMulFinal += 1f;
                    ammoParts++;
                    frMulFinal += 1f;
                    fireParts++;
                    break;
                }
                case AllowedParts.MAGAZINE_EXTENDED: {
                    bonusAmmoMax += 15 * AMMO_POINTS_MUL;
                    ammoMulFinal += 1.1f;
                    ammoParts++;
                    frMulFinal += 0.9f;
                    fireParts++;
                    bonusInacc += 0.25f;
                    break;
                }
                case AllowedParts.MAGAZINE_BELT: {
                    bonusAmmoMax += 25 * AMMO_POINTS_MUL;
                    ammoMulFinal += 1.5f;
                    ammoParts++;
                    frMulFinal += 1.25f;
                    fireParts++;
                    bonusInacc += 1f;
                    break;
                }
                case AllowedParts.CASING_SMALL: {
                    damageMulMaxFinal += 0.75f;
                    dmgMaxParts++;
                    damageMulMinFinal += 0.75f;
                    dmgMinParts++;
                    break;
                }
                case AllowedParts.CASING_NORMAL: {
                    //Sorry nothing.
                    break;
                }
                case AllowedParts.CASING_LARGE: {
                    damageMulMaxFinal += 1.25f;
                    dmgMaxParts++;
                    damageMulMinFinal += 1.25f;
                    dmgMinParts++;
                    break;
                }
                case AllowedParts.CASING_SHELL: {
                    damageMulMaxFinal += 1.1f;
                    dmgMaxParts++;
                    damageMulMinFinal += 1.1f;
                    dmgMinParts++;
                    ammoMulFinal += 0.9f;
                    ammoParts++;
                    break;
                }
                case AllowedParts.ROUND_STANDARD: {
                    //Sorry nothing.
                    break;
                }
                case AllowedParts.ROUND_BUCKSHOT: {
                    bonusProj += 5;
                    damageMulMaxFinal += 0.25f;
                    dmgMaxParts++;
                    damageMulMinFinal += 0.25f;
                    dmgMinParts++;
                    break;
                }
                case AllowedParts.ROUND_BIRDSHOT: {
                    bonusProj += 8;
                    damageMulMaxFinal += 0.1f;
                    dmgMaxParts++;
                    damageMulMinFinal += 0.1f;
                    dmgMinParts++;
                    break;
                }
                case AllowedParts.PROPELLANT_LIGHT: {
                    damageMulMaxFinal += 0.9f;
                    dmgMaxParts++;
                    damageMulMinFinal += 0.9f;
                    dmgMinParts++;
                    velMulFinal += 0.8f;
                    bonusPeirce -=1;
                    break;
                }
                case AllowedParts.PROPELLANT_NORMAL: {
                    //EEEEEEEE
                    break;
                }
                case AllowedParts.PROPELLANT_HEAVY: {
                    damageMulMaxFinal += 1.1f;
                    dmgMaxParts++;
                    damageMulMinFinal += 1.1f;
                    dmgMinParts++;
                    velMulFinal += 1.2f;
                    bonusPeirce +=1;
                    break;
                }
                default: {break;} //Sorry Nothing.
            }
        }

        gun.set(LenDataComponents.GUN_STATS,new GunStats(
                 Math.max((int)Math.floor(((newAmmoMax + bonusAmmoMax) * ammoMul) * (ammoMulFinal/(ammoParts != 0 ? ammoParts : 1))),1),
                Math.max((int) Math.floor(((newPeirce + bonusPeirce) * peirceMul) * (periceMulFinal/(pierceParts != 0 ? pierceParts : 1))),1),
                Math.max((int)Math.floor(((newFR + bonusFR) * frmul) * (frMulFinal/(fireParts != 0 ? fireParts : 1))),1),
                Math.max(((newInacc + bonusInacc) * inaccMod) * (inaccModFinal/(inaccParts != 0 ? inaccParts : 1)),0),
                Math.max(((newProj + bonusProj) * projMul) * (projMulFinal/(projParts != 0 ? projParts : 1)),1),
                Math.max(((newdmgMax + bonusDmgMax) * damageMulMax) * (damageMulMaxFinal/(dmgMaxParts != 0 ? dmgMaxParts : 1)),0.5f),
                Math.max(((newdmgMin + bonusDmgMin) * damageMulMin) * (damageMulMinFinal/(dmgMinParts != 0 ? dmgMinParts : 1)),0.25f),
                Math.max(((newVelMul + bonusVel) * velMul) * ((velMulFinal/velParts != 0 ? velParts : 1)),0.05f),
                ((newGrav + bonusGrav) * gravMod) * ((gravModFinal/gravParts != 0 ? gravParts : 1))));
    }
}
