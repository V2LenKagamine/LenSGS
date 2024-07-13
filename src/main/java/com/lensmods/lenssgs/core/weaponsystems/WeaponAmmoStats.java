package com.lensmods.lenssgs.core.weaponsystems;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.data.MaterialMap;
import com.lensmods.lenssgs.core.data.MaterialStats;
import com.lensmods.lenssgs.core.datacomps.*;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.lensmods.lenssgs.init.LenItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
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
        return safeGunStats(stacc) ? stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max() : 0;
    }
    public static float getVelMult(ItemStack stacc) {
        return safeGunStats(stacc) && safeGunLastAmmo(stacc) ? Math.clamp(stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getVelocityMult() + stacc.get(LenDataComponents.LAST_AMMO).getStats().getVelocityMult(),0.05f ,5f) : 1;
    }

    public static int getPierce(ItemStack stacc) {
        return safeGunStats(stacc) && safeGunLastAmmo(stacc)  ? Math.clamp(stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getPierce() + stacc.get(LenDataComponents.LAST_AMMO).getStats().getPierce(),0 ,25 ) : 0;
    }

    public static Double getGrav(ItemStack stacc) {
        return safeGunStats(stacc) && safeGunLastAmmo(stacc)  ? Math.clamp(stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getGravMod() + stacc.get(LenDataComponents.LAST_AMMO).getStats().getGravMod(),-1,1) : 0;
    }

    public static float getAccuracy(ItemStack stacc) {
        return safeGunStats(stacc) && safeGunLastAmmo(stacc) ? Math.clamp(stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getInaccuracy() + stacc.get(LenDataComponents.LAST_AMMO).getStats().getInaccuracy(),0, 180) : 0;
    }

    public static int getProjAmt(ItemStack stacc) {
        if(safeGunLastAmmo(stacc)&& safeGunStats(stacc)) {
            float baseProjCount = Math.clamp(stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getProjCount() + stacc.get(LenDataComponents.LAST_AMMO).getStats().getProjCount(),1,50);
            return LensSGS.RANDY.nextFloat(0, 1) < baseProjCount ? Mth.floor(baseProjCount) : Mth.ceil(baseProjCount);
        }
        return 1;
    }
    public static float rollDmg(ItemStack stacc) {
        if(safeGunStats(stacc)&& safeGunLastAmmo(stacc)) {
            float maxDmg = stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getDamageMax() + stacc.get(LenDataComponents.LAST_AMMO).getStats().getDamageMax();
            float minDmg = stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getDamageMin() + stacc.get(LenDataComponents.LAST_AMMO).getStats().getDamageMin();
            return maxDmg > minDmg ? LenUtil.randBetween(minDmg, maxDmg) : minDmg;
        }
        return 0.25f;
    }

    public static boolean safeGunStats(ItemStack stacc) {
        return stacc.getOrDefault(LenDataComponents.GUN_STAT_TRAITS,null) != null;
    }
    public static boolean safeGunLastAmmo(ItemStack stacc) {
        return stacc.getOrDefault(LenDataComponents.LAST_AMMO,null) !=null;
    }

    public static void attemptReload(LivingEntity ent, ItemStack item, Level world) {
        if(item.getOrDefault(LenDataComponents.GUN_STAT_TRAITS,null)!=null && item.getOrDefault(LenDataComponents.AMMO_COUNTER,null)!= null) {
            ItemStack ammo = LenUtil.getMatchingStackList((Player) ent, LenItems.AMMO_BASE.get()).getFirst();
            int ammoCurrent = item.get(LenDataComponents.AMMO_COUNTER);
            GunStats stats = item.get(LenDataComponents.GUN_STAT_TRAITS).getStats();
            if(ammoAmountLeft(ammo) >=1&& ammo.getOrDefault(LenDataComponents.AMMO_COUNTER,null)!=null) {
                int ammoInAmmo = ammo.get(LenDataComponents.AMMO_COUNTER) * AMMO_POINTS_MUL;
                int removed = Math.min(stats.getAmmo_max() - ammoCurrent,ammoInAmmo);
                item.set(LenDataComponents.AMMO_COUNTER,ammoCurrent + removed);
                if(!((Player) ent).isCreative()) {
                    ammo.set(LenDataComponents.AMMO_COUNTER, ammoInAmmo - removed);
                }
                item.set(LenDataComponents.LAST_AMMO,ammo.get(LenDataComponents.GUN_STAT_TRAITS));
                return;
            }
            LensSGS.L3NLOGGER.error("Someone is trying to reload with no ammo stats, bad!:{}",ent);
            return;
        }
        LensSGS.L3NLOGGER.error("Someone is trying to reload a gun with no stats, bad!:{}",ent);
    }

    public static GunStatTraitPair getLastAmmo(ItemStack stacc) {
        if(safeGunStats(stacc)) {
            return stacc.get(LenDataComponents.LAST_AMMO);
        }
        return new GunStatTraitPair();
    }

    public static void recalculateGunData(ItemStack gun, HolderLookup.Provider provider) { //Please only call this during crafting FTLOG.
        GunComp data = gun.getOrDefault(LenDataComponents.GUN_COMP,new GunComp(List.of()));
        if (data.getPartList().isEmpty()) {
            LensSGS.L3NLOGGER.error("Someone is crafting a gun with no parts. Not good.");
        } //WALL OF VARIABLES

        List<TraitLevel> finalTraits = new ArrayList<>(); //Todo: get traits and set appropriately
        int newAmmoMax = AMMO_POINTS_MUL;
        int bonusAmmoMax =0;
        float ammoMul =1;
        float totalAmmoMul = 1f;

        int newPeirce = 0;
        int bonusPeirce =0;
        float peirceMul =1;
        float totalPeirceMul = 1f;

        int newFR = 20;
        int bonusFR =0;
        float frmul =1;
        float totalFrMul = 1f;

        float newInacc =0;
        float bonusInacc =0;
        float inaccMod =1;
        float totalInaccMul = 1f;

        float newProj =1;
        float bonusProj=0;
        float projMul =1;
        float totalProjMul =1f;

        float newdmgMin=1f;
        float newdmgMax=1.5f;
        float bonusDmgMin =0;
        float bonusDmgMax =0;
        float damageMulMax =1f;
        float damageMulMin =1f;
        float totalMaxMul=1f;
        float totalMinMul = 1f;

        float newVelMul=0f;
        float bonusVel =0;
        float velMul =1f;
        float totalVelMul =1f;

        double newGrav=0d;
        double bonusGrav=0d;
        float gravMod=1f;
        float totalGravMul =1f;

        int mulammoParts =0;
        int mulpierceParts = 0;
        int mulfireParts =0;
        int mulinaccParts =0;
        int mulprojParts =0;
        int muldmgMaxParts =0;
        int muldmgMinParts =0;
        int mulvelParts =0;
        int mulgravParts =0;
        //THE WALL ENDS
        for(GunPartHolder part : data.getPartList()) {
            for (StatMod stats : MaterialMap.loadedMats(provider).get(part.getMaterial()).getStatModList()) {
                if (!stats.allowedParts().contains(part.getName())) {
                    continue;
                }
                switch (stats.stat()) {
                    case MaterialStats.AMMO_MAX: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newAmmoMax += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.AVG_MUL)) {
                            ammoMul += stats.val();
                            mulammoParts++;
                        }
                        if(stats.modType().equals(MaterialStats.MUL_TOTAL)){
                            totalAmmoMul+=stats.val();
                        }
                    }
                    case MaterialStats.PIERCE: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newPeirce += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.AVG_MUL)) {
                            peirceMul += stats.val();
                            mulpierceParts++;
                        }
                        if(stats.modType().equals(MaterialStats.MUL_TOTAL)){
                            totalPeirceMul+= stats.val();
                        }
                    }
                    case MaterialStats.FIRE_RATE: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newFR += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.AVG_MUL)) {
                            frmul += stats.val();
                            mulfireParts++;
                        }
                        if(stats.modType().equals(MaterialStats.MUL_TOTAL)){
                            totalFrMul += stats.val();
                        }
                    }
                    case MaterialStats.PROJ_COUNT: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newProj += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.AVG_MUL)) {
                            projMul += stats.val();
                            mulprojParts++;
                        }
                        if(stats.modType().equals(MaterialStats.MUL_TOTAL)){
                            projMul+= stats.val();
                        }
                    }
                    case MaterialStats.MAX_DMG: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newdmgMax += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.AVG_MUL)) {
                            damageMulMax += stats.val();
                            muldmgMaxParts++;
                        }
                        if(stats.modType().equals(MaterialStats.MUL_TOTAL)){
                            totalMaxMul += stats.val();
                        }
                    }
                    case MaterialStats.MIN_DMG: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newdmgMin += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.AVG_MUL)) {
                            damageMulMin += stats.val();
                            muldmgMinParts++;
                        }
                        if(stats.modType().equals(MaterialStats.MUL_TOTAL)){
                            totalMinMul += stats.val();
                        }
                    }
                    case MaterialStats.GRAVITY_MOD: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newGrav += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.AVG_MUL)) {
                            gravMod += stats.val();
                            mulgravParts++;
                        }
                        if(stats.modType().equals(MaterialStats.MUL_TOTAL)){
                            totalGravMul+= stats.val();
                        }
                    }
                    case MaterialStats.VEL_MULT: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newVelMul += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.AVG_MUL)) {
                            velMul += stats.val();
                            mulvelParts++;
                        }
                        if(stats.modType().equals(MaterialStats.MUL_TOTAL)){
                            totalVelMul += stats.val();
                        }
                    }
                    case MaterialStats.INACCURACY_DEG: {
                        if (stats.modType().equals(MaterialStats.ADD)) {
                            newInacc += stats.val();
                        }
                        if (stats.modType().equals(MaterialStats.AVG_MUL)) {
                            inaccMod += stats.val();
                            mulinaccParts++;
                        }
                        if(stats.modType().equals(MaterialStats.MUL_TOTAL)){
                            totalInaccMul += stats.val();
                        }
                    }
                }
            }
        }
        List<ModelColorPair> partColorList = new ArrayList<>();
        for(GunPartHolder part : data.getPartList()) { //Yes we JUST iterated over it, but we need these calculated AFTER the stats are set.
            switch (part.getSubType()) { //The unit of a switch case. Yes these are hardcoded. Dont wanna do data-stuff for them. maybe TODO?
                case AllowedParts.RECIEVER_PISTOL: {
                    totalMinMul -= 0.2f;
                    totalMaxMul -= 0.1f;
                    bonusInacc -= 2.5f;
                    break;
                }
                case AllowedParts.RECIEVER_STANDARD: {
                    totalMinMul += 0.1f;
                    totalMaxMul += 0.1f;
                    break;
                }
                case AllowedParts.RECIEVER_BULLPUP: {
                    bonusInacc -= 0.25f;
                    break;
                }
                case AllowedParts.ACTION_MANUAL: {
                    totalAmmoMul -= 0.9f;
                    totalMaxMul += 0.75f;
                    totalMinMul += 0.80;
                    break;
                }
                case AllowedParts.ACTION_SINGLE: {
                    totalMaxMul += 0.2f;
                    totalMinMul += 0.3f;
                    break;
                }
                case AllowedParts.ACTION_AUTOMATIC: {
                    totalFrMul +=0.2f;
                    break;
                }
                case AllowedParts.BARREL_STUB: {
                    totalInaccMul += 0.1f;
                    totalFrMul += 0.2f;
                    totalMaxMul -= 0.1f;
                    break;
                }
                case AllowedParts.BARREL_SHORT: {
                    totalInaccMul += 0.05f;
                    totalFrMul += 0.1f;
                    totalMaxMul -= 0.05f;
                    break;
                }
                case AllowedParts.BARREL_FAIR: {
                    totalMaxMul += 0.025f;
                    totalMinMul += 0.025f;
                    break;
                }
                case AllowedParts.BARREL_LONG: {
                    totalInaccMul -= 0.05f;
                    totalFrMul -= 0.1f;
                    totalMaxMul += 0.05f;
                    break;
                }
                case AllowedParts.BARREL_EXTENDED: {
                    totalInaccMul -= 0.1f;
                    totalFrMul -= 0.2f;
                    totalMaxMul += 0.1f;
                    break;
                }
                case AllowedParts.STOCK_SHORT: {
                    totalInaccMul -= 0.025f;
                    totalFrMul += 0.05f;
                    break;
                }
                case AllowedParts.STOCK_FULL: {
                    totalInaccMul -= 0.05f;
                    totalFrMul += 0.025f;
                    break;
                }
                case AllowedParts.MAGAZINE_SHORT: {
                    bonusAmmoMax += 5 * AMMO_POINTS_MUL;
                    totalAmmoMul -= 0.1f;
                    totalFrMul += 0.125f;
                    bonusInacc -= 0.25f;
                    break;
                }
                case AllowedParts.MAGAZINE_NORMAL: {
                    bonusAmmoMax += 10 * AMMO_POINTS_MUL;
                    break;
                }
                case AllowedParts.MAGAZINE_EXTENDED: {
                    bonusAmmoMax += 15 * AMMO_POINTS_MUL;
                    totalAmmoMul += 0.1f;
                    totalFrMul -= 0.125f;
                    bonusInacc += 0.25f;
                    break;
                }
                case AllowedParts.MAGAZINE_BELT: {
                    bonusAmmoMax += 25 * AMMO_POINTS_MUL;
                    totalAmmoMul += 0.25f;
                    totalFrMul += 0.2f;
                    bonusInacc -= 2f;
                    break;
                }
                case AllowedParts.CASING_SMALL: {
                    totalAmmoMul += 0.125f*AMMO_POINTS_MUL;
                    totalMaxMul -= 0.125f;
                    totalMinMul -= 0.125f;
                    break;
                }
                case AllowedParts.CASING_NORMAL: {
                    //Sorry nothing
                    break;
                }
                case AllowedParts.CASING_LARGE: {
                    totalAmmoMul -= 0.125f*AMMO_POINTS_MUL;
                    totalMaxMul += 0.125f;
                    totalMinMul += 0.125f;
                    break;
                }
                case AllowedParts.CASING_SHELL: {
                    totalAmmoMul -= 0.05f * AMMO_POINTS_MUL;
                    totalMaxMul += 0.05f;
                    totalMinMul += 0.05f;
                    totalAmmoMul -= 0.05f;
                    break;
                }
                case AllowedParts.ROUND_STANDARD: {
                    //Sorry nothing.
                    break;
                }
                case AllowedParts.ROUND_BUCKSHOT: {
                    bonusProj += 5;
                    totalMaxMul -= 0.75f;
                    totalMinMul -= 0.75f;
                    break;
                }
                case AllowedParts.ROUND_BIRDSHOT: {
                    bonusProj += 8;
                    totalMaxMul -= 0.8f;
                    totalMinMul -= 0.8f;
                    break;
                }
                case AllowedParts.PROPELLANT_LIGHT: {
                    totalAmmoMul += 0.125f * AMMO_POINTS_MUL;
                    totalMaxMul -= 0.05f;
                    totalMinMul -= 0.05f;
                    velMul -= 0.08f;
                    bonusPeirce -=1;
                    break;
                }
                case AllowedParts.PROPELLANT_NORMAL: {
                    //EEEEEEEE
                    break;
                }
                case AllowedParts.PROPELLANT_HEAVY: {
                    totalAmmoMul -= 0.125f * AMMO_POINTS_MUL;
                    totalMaxMul += 0.05f;
                    totalMinMul += 0.05f;
                    velMul += 0.08f;
                    bonusPeirce +=1;
                    break;
                }
                default: {break;} //Sorry Nothing.
            }
            //And then the rendering stuff
            partColorList.add(new ModelColorPair(part.getSubType(),MaterialMap.loadedMats(provider).get(part.getMaterial()).getColor()));
        }
        gun.set(LenDataComponents.PART_COLOR_LIST,partColorList);
        GunStats finalStats =new GunStats( //mostly for debug
                Math.max((int)Math.floor((((newAmmoMax + bonusAmmoMax) * ((ammoMul/ mulammoParts != 0 ? mulammoParts : 1) * ammoMul)))*totalAmmoMul * AMMO_POINTS_MUL),1*AMMO_POINTS_MUL),
                Math.max((int)Math.floor(((newPeirce + bonusPeirce) * (( peirceMul / mulpierceParts != 0 ? mulpierceParts : 1) * peirceMul))*totalPeirceMul),1),
                Math.max((int)Math.floor(((newFR + bonusFR) * (( frmul/ mulfireParts != 0 ? mulfireParts : 1) * frmul))*totalFrMul),1),
                Math.max(((newInacc + bonusInacc) * (( inaccMod / mulinaccParts != 0 ? mulinaccParts : 1) * inaccMod))*totalInaccMul,0),
                Math.max(((newProj + bonusProj) * ((projMul /mulprojParts  != 0 ? mulprojParts : 1) * projMul))*totalProjMul,1),
                Math.max(((newdmgMax + bonusDmgMax) * ((damageMulMax/ muldmgMaxParts != 0 ? muldmgMaxParts : 1)))*totalMaxMul,0.5f),
                Math.max(((newdmgMin + bonusDmgMin) * ((damageMulMin/ muldmgMinParts != 0 ? muldmgMinParts : 1)))*totalMinMul,0.25f),
                Math.max(((newVelMul + bonusVel) * (( velMul/ mulvelParts != 0 ? mulvelParts : 1) * velMul))*totalVelMul,0.05f),
                ((newGrav + bonusGrav) * ((gravMod/ mulgravParts != 0 ? mulgravParts : 1) * gravMod))*totalGravMul);
        gun.set(LenDataComponents.GUN_STAT_TRAITS,new GunStatTraitPair(finalStats,finalTraits));
    }
}
