package com.lensmods.lenssgs.core.weaponsystems;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.data.MaterialMap;
import com.lensmods.lenssgs.core.data.MaterialStats;
import com.lensmods.lenssgs.core.datacomps.*;
import com.lensmods.lenssgs.core.items.AmmoBaseItem;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.init.LenConfig;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.lensmods.lenssgs.init.LenItems;
import com.lensmods.lenssgs.init.LenSounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.sounds.SoundSource;
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
        return safeGunStats(stacc) ? stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max()/AMMO_POINTS_MUL : 0;
    }
    public static float getVelMult(ItemStack stacc) {
        return safeGunStats(stacc) && safeGunLastAmmo(stacc) ? Math.clamp(stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getVelocityMult() + stacc.get(LenDataComponents.LAST_AMMO).getStats().getVelocityMult(),0.05f ,5f) : 1;
    }

    public static int getPierce(ItemStack stacc) {
        return safeGunStats(stacc) && safeGunLastAmmo(stacc)  ? Math.clamp(stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getPierce() + stacc.get(LenDataComponents.LAST_AMMO).getStats().getPierce(),0 ,25 ) : 0;
    }

    public static Double getGrav(ItemStack stacc) {
        return safeGunStats(stacc) && safeGunLastAmmo(stacc)  ? Math.clamp(stacc.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getGravMod() + stacc.get(LenDataComponents.LAST_AMMO).getStats().getGravMod(),-0.01d,0.01d) : 0;
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
    public static boolean safeGunComp(ItemStack stacc) {
        return stacc.getOrDefault(LenDataComponents.GUN_COMP,null) !=null;
    }

    public static void attemptReload(LivingEntity ent, ItemStack item, Level world) {
        if(item.getOrDefault(LenDataComponents.GUN_STAT_TRAITS,null)!=null && item.getOrDefault(LenDataComponents.AMMO_COUNTER,null)!= null) {
            if (LenUtil.getMatchingStackList((Player) ent, LenItems.AMMO_BASE.get()).isEmpty()) {
                return;
            }
            ItemStack ammo = LenUtil.getMatchingStackList((Player) ent, LenItems.AMMO_BASE.get()).getFirst();
            int ammoCurrent = item.get(LenDataComponents.AMMO_COUNTER);
            GunStats stats = item.get(LenDataComponents.GUN_STAT_TRAITS).getStats();
            if(ammo.getOrDefault(LenDataComponents.AMMO_COUNTER,null)==null) {
                LensSGS.L3NLOGGER.error("Someone is trying to reload with no ammo stats, bad!:{}",ent);
                return;
            }
            if(ammoAmountLeft(ammo) != item.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max()) {
                int ammoInAmmo = ammo.get(LenDataComponents.AMMO_COUNTER);
                var ammoTraits = ammo.getOrDefault(LenDataComponents.GUN_STAT_TRAITS,null);
                int removed = Math.min(stats.getAmmo_max() - ammoCurrent, ammoInAmmo);
                item.set(LenDataComponents.AMMO_COUNTER, ammoCurrent + removed);
                if(ammoTraits == null) {
                    if (!(((Player) ent).isCreative())) {
                        ammo.set(LenDataComponents.AMMO_COUNTER, ammoInAmmo - removed);
                    }
                } else {
                    boolean noConsume = false;
                    boolean mend = false;
                    if(ammoTraits.getTraits().stream().anyMatch(trait -> trait.trait().equals(MaterialStats.ECOLOGICAL))) {
                        int lvl = ammoTraits.getTraits().stream().filter(trait -> trait.trait().equals(MaterialStats.ECOLOGICAL)).findFirst().get().level();
                        noConsume = LenUtil.randBetween(0,100) <= (lvl*5f);
                    }
                    if(ammoTraits.getTraits().stream().anyMatch(trait -> trait.trait().equals(MaterialStats.ETERNAL))) {
                        int lvl = ammoTraits.getTraits().stream().filter(trait -> trait.trait().equals(MaterialStats.ETERNAL)).findFirst().get().level();
                        mend = LenUtil.randBetween(0,100) <= (lvl*5f);
                    }
                    if (!(((Player) ent).isCreative() || noConsume)) {
                        ammo.set(LenDataComponents.AMMO_COUNTER, ammoInAmmo - removed);
                    }
                    if(!(((Player) ent).isCreative()) && mend) {
                        ammo.set(LenDataComponents.AMMO_COUNTER, Math.min(ammo.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max(),ammoInAmmo + removed));
                    }
                }
                item.set(LenDataComponents.LAST_AMMO, ammo.get(LenDataComponents.GUN_STAT_TRAITS));
                world.playSound(null,ent.position().x,ent.position().y,ent.position().z, LenSounds.GUN_RELOAD, SoundSource.PLAYERS,1f,1f);
                return;
            }
            else {return;}
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
        boolean ammo = gun.getItem() instanceof AmmoBaseItem;

        List<TraitLevel> finalTraits = new ArrayList<>();
        int newAmmoMax = ammo ? 250 * AMMO_POINTS_MUL :AMMO_POINTS_MUL;
        int bonusAmmoMax =0;
        float ammoMul =1;
        float totalAmmoMul = 1f;

        int newPeirce = 0;
        int bonusPeirce =0;
        float peirceMul =1;
        float totalPeirceMul = 1f;

        int newFR = ammo ? 0 : 10;
        int bonusFR =0;
        float frmul =1;
        float totalFrMul = 1f;

        float newInacc = ammo ? 0.5f : 1.25f;
        float bonusInacc =0;
        float inaccMod =1;
        float totalInaccMul = 1f;

        float newProj = ammo ? 1 : 0;
        float bonusProj=0;
        float projMul =1;
        float totalProjMul =1f;

        float newdmgMin=ammo ? 1f:0;
        float newdmgMax=ammo ? 1.5f:0;
        float bonusDmgMin =0;
        float bonusDmgMax =0;
        float damageMulMax =1f;
        float damageMulMin =1f;
        float totalMaxMul=1f;
        float totalMinMul = 1f;

        float newVelMul= ammo ? 0.06f : 0;
        float bonusVel =0;
        float velMul =1f;
        float totalVelMul =1f;

        double newGrav=ammo?0.002d:0d;
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

        List<ModelColorPair> partColorList = new ArrayList<>();
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
                        break;
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
                        break;
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
                        break;
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
                        break;
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
                        break;
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
                        break;
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
                        break;
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
                        break;
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
                        break;
                    }
                }
            }
            for (TraitLevel trait : MaterialMap.loadedMats(provider).get(part.getMaterial()).getTraitLevelList()) {
                if(!(trait.allowedParts().contains(part.getName()) || trait.allowedParts().stream().anyMatch(test -> test.equals("all")))) {continue; }
                /*
                switch (trait.trait()) {
                }
                 */
                if(finalTraits.contains(trait)) {
                    int curLev = finalTraits.stream().filter(traitL -> traitL.trait().equals(trait.trait())).findFirst().get().level();
                    finalTraits.remove(trait);
                    finalTraits.add(new TraitLevel(trait.trait(), trait.level() + curLev));
                }
                else {
                    finalTraits.add(trait);
                }
            }
            switch (part.getSubType()) { //The unit of a switch case. Yes these are hardcoded. Dont wanna do data-stuff for them. maybe TODO?
                case AllowedParts.RECEIVER_PISTOL: {
                    totalMinMul -= 0.2f * (float) LenConfig.pistol_stat_mul;
                    totalMaxMul -= 0.1f * (float) LenConfig.pistol_stat_mul;
                    totalAmmoMul -= 0.5f * (float) LenConfig.pistol_stat_mul;
                    bonusInacc -= 2.5f * (float) LenConfig.pistol_stat_mul;
                    break;
                }
                case AllowedParts.RECEIVER_STANDARD: {
                    bonusInacc -= 1f;
                    bonusDmgMax += 0.25f * (float) LenConfig.rifle_stat_mul;
                    bonusDmgMin += 0.75f * (float) LenConfig.rifle_stat_mul;
                    bonusFR += (int)Math.floor(5 * (float) LenConfig.rifle_stat_mul);
                    totalFrMul += 0.25f * (float) LenConfig.rifle_stat_mul;
                    totalAmmoMul -= 0.65F * (float) LenConfig.rifle_stat_mul;
                    break;
                }
                case AllowedParts.RECEIVER_BULLPUP: {
                    bonusInacc -= 2f;
                    bonusFR -= (int)Math.floor(2*(float) LenConfig.bullpup_stat_mul);
                    bonusDmgMax -= 0.5f*(float) LenConfig.bullpup_stat_mul;
                    bonusDmgMin -= 1.5f*(float) LenConfig.bullpup_stat_mul;
                    break;
                }
                case AllowedParts.ACTION_MANUAL: {
                    totalAmmoMul -= 0.9f;
                    totalMaxMul += 0.75f;
                    totalMinMul += 0.80f;
                    totalFrMul +=0.5f;
                    bonusDmgMax += 3f;
                    bonusDmgMin += 2f;
                    break;
                }
                case AllowedParts.ACTION_SINGLE: {
                    totalMaxMul += 0.2f;
                    totalMinMul += 0.3f;
                    totalFrMul += 0.15f;
                    bonusDmgMax += 2f;
                    bonusDmgMin += 1.5f;
                    break;
                }
                case AllowedParts.ACTION_AUTOMATIC: {
                    totalFrMul -=0.15f;
                    break;
                }
                case AllowedParts.BARREL_STUB: {
                    totalInaccMul += 0.1f;
                    totalFrMul -= 0.2f;
                    totalMaxMul -= 0.1f;
                    break;
                }
                case AllowedParts.BARREL_SHORT: {
                    totalInaccMul += 0.05f;
                    totalFrMul -= 0.1f;
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
                    totalFrMul += 0.1f;
                    totalMaxMul += 0.05f;
                    break;
                }
                case AllowedParts.BARREL_EXTENDED: {
                    totalInaccMul -= 0.1f;
                    totalFrMul += 0.2f;
                    totalMaxMul += 0.1f;
                    break;
                }
                case AllowedParts.STOCK_SHORT: {
                    bonusInacc -= 1.25f;
                    totalInaccMul -= 0.025f;
                    totalFrMul -= 0.05f;
                    break;
                }
                case AllowedParts.STOCK_FULL: {
                    bonusInacc -= 2.5f;
                    totalInaccMul -= 0.05f;
                    totalFrMul -= 0.025f;
                    break;
                }
                case AllowedParts.MAGAZINE_SHORT: {
                    bonusAmmoMax += 14 * AMMO_POINTS_MUL;
                    totalAmmoMul -= 0.1f;
                    totalFrMul -= 0.125f;
                    bonusInacc -= 1f;
                    break;
                }
                case AllowedParts.MAGAZINE_NORMAL: {
                    bonusAmmoMax += 24 * AMMO_POINTS_MUL;
                    break;
                }
                case AllowedParts.MAGAZINE_EXTENDED: {
                    bonusAmmoMax += 34 * AMMO_POINTS_MUL;
                    totalAmmoMul += 0.1f;
                    totalFrMul += 0.125f;
                    bonusInacc += 1f;
                    break;
                }
                case AllowedParts.MAGAZINE_BELT: {
                    bonusAmmoMax += 99 * AMMO_POINTS_MUL;
                    totalAmmoMul += 0.25f;
                    totalFrMul += 0.25f;
                    bonusInacc += 3f;
                    break;
                }
                case AllowedParts.CASING_SMALL: {
                    totalAmmoMul += 0.4f;
                    totalMaxMul -= 0.4f;
                    totalMinMul -= 0.4f;
                    break;
                }
                case AllowedParts.CASING_NORMAL: {
                    //Sorry nothing
                    break;
                }
                case AllowedParts.CASING_LARGE: {
                    totalAmmoMul -= 0.4f;
                    totalMaxMul += 0.4f;
                    totalMinMul += 0.4f;
                    break;
                }
                case AllowedParts.CASING_SHELL: {
                    totalAmmoMul -= 0.1f;
                    totalMaxMul += 0.1f;
                    totalMinMul += 0.1f;
                    totalFrMul += 0.1f;
                    break;
                }
                case AllowedParts.ROUND_STANDARD: {
                    //Sorry nothing.
                    break;
                }
                case AllowedParts.ROUND_BUCKSHOT: {
                    bonusProj += 5;
                    totalMaxMul -= 0.6f;
                    totalMinMul -= 0.6f;
                    bonusInacc += 8f;
                    break;
                }
                case AllowedParts.ROUND_BIRDSHOT: {
                    bonusProj += 8;
                    totalMaxMul -= 0.7f;
                    totalMinMul -= 0.7f;
                    bonusInacc += 12f;
                    break;
                }
                case AllowedParts.PROPELLANT_LIGHT: {
                    totalAmmoMul += 0.125f;
                    totalMaxMul -= 0.05f;
                    totalMinMul -= 0.05f;
                    bonusDmgMax -= 0.5f;
                    bonusDmgMin -= 0.5f;
                    bonusInacc -=1f;
                    bonusPeirce -=1;
                    break;
                }
                case AllowedParts.PROPELLANT_NORMAL: {
                    //EEEEEEEE
                    break;
                }
                case AllowedParts.PROPELLANT_HEAVY: {
                    totalAmmoMul -= 0.125f;
                    totalMaxMul += 0.05f;
                    totalMinMul += 0.05f;
                    bonusDmgMax += 0.5f;
                    bonusDmgMin += 0.5f;
                    bonusInacc +=1f;
                    bonusPeirce +=1;
                    break;
                }
                default: {break;} //Sorry Nothing.
            }
            //rendering thing
            partColorList.add(new ModelColorPair(part.getSubType(),MaterialMap.loadedMats(provider).get(part.getMaterial()).getColor()));
        }
        //Calculation out of order, because we need proj. count to slow down the shots, lag reasons.
        int finalAmmo = Math.max((int)Math.floor(((((newAmmoMax + bonusAmmoMax)  * ((ammoMul/ (mulammoParts != 0 ? mulammoParts : 1)))))*Math.clamp(totalAmmoMul,0.05f,10f))), AMMO_POINTS_MUL);
        int finalPeirce = Math.max((int)Math.floor((((newPeirce + bonusPeirce) * (( peirceMul / (mulpierceParts != 0 ? mulpierceParts : 1)))))*Math.clamp(totalPeirceMul,0.05f,10f)),0);
        float finalInaccuracy = Math.max((((newInacc + bonusInacc) * (( inaccMod / (mulinaccParts != 0 ? mulinaccParts : 1)))) * Math.clamp(totalInaccMul,0.05f,10f)),ammo ? 0 : -10f);
        float finalProjectileCount = Math.max((((newProj + bonusProj) * ((projMul /(mulprojParts  != 0 ? mulprojParts : 1)))) * Math.clamp(totalProjMul,0.05f,10f)),ammo ? 1 : 0);
        float finalVel = Math.max((((newVelMul + bonusVel)* (( velMul/ (mulvelParts != 0 ? mulvelParts : 1))))*Math.clamp(totalVelMul,0.05f,10f)),ammo ? 0.05f : 0 );
        //Calculate Firerate from projectiles, then give bonus damage based on fire rate, slow fire = more damage bonus. But also More Projectiles == less Bonus.
        float projectileMalice = (float)(Math.ceil((finalProjectileCount-1)*1.5f));
        int finalFr = Math.max((int)Math.floor((((newFR  + bonusFR)* ((frmul/ (mulfireParts != 0 ? mulfireParts : 1))))) * Math.clamp(totalFrMul,0.05f,10f)),ammo ? (int)projectileMalice: 2 );
        float bonusFromFr = (((ammo?0.05f:0.1f)*finalFr)/(finalProjectileCount!=0? finalProjectileCount : 1f));
        float finalMaxDmg =Math.max((((newdmgMax + bonusDmgMax + bonusFromFr) * ((damageMulMax/ (muldmgMaxParts != 0 ? muldmgMaxParts : 1))))* Math.clamp(totalMaxMul,0.05f,10f)),ammo ? 0.5f : 0f)*(float)LenConfig.global_damage_mult;
        float finalMinDmg =Math.max((((newdmgMin + bonusDmgMin + bonusFromFr)  * ((damageMulMin/ (muldmgMinParts != 0 ? muldmgMinParts : 1))))* Math.clamp(totalMinMul,0.05f,10f)),ammo ? 0.25f : 0f)*(float)LenConfig.global_damage_mult;

        double finalGrav = Math.clamp((((newGrav+ bonusGrav) * (gravMod/ (mulgravParts != 0 ? mulgravParts : 1)))*Math.clamp(totalGravMul,0.05f,10f)),-0.02d,0.02d);
        if(finalMinDmg > finalMaxDmg) {
            finalMaxDmg = finalMinDmg;
        }
        gun.set(LenDataComponents.PART_COLOR_LIST,partColorList);
        GunStats finalStats =new GunStats( //mostly for debug
                finalAmmo,
                finalPeirce,
                finalFr,
                finalInaccuracy,
                finalProjectileCount,
                finalMaxDmg,
                finalMinDmg,
                finalVel,
                finalGrav);
        gun.set(LenDataComponents.GUN_STAT_TRAITS,new GunStatTraitPair(finalStats,finalTraits));
        if(!ammo && safeGunStats(gun)) {
            int lastcount = gun.get(LenDataComponents.AMMO_COUNTER);
            gun.set(LenDataComponents.AMMO_COUNTER,Math.min(lastcount,finalAmmo));
        }
    }
}
