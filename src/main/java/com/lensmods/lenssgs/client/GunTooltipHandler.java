package com.lensmods.lenssgs.client;

import com.lensmods.lenssgs.api.IModdable;
import com.lensmods.lenssgs.core.data.MaterialMap;
import com.lensmods.lenssgs.core.datacomps.*;
import com.lensmods.lenssgs.core.util.Color;
import com.lensmods.lenssgs.core.util.KeyManager;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.core.weaponsystems.WeaponAmmoStats;
import com.lensmods.lenssgs.datagen.LenTagKeys;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.lensmods.lenssgs.init.LenDataReg;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.lensmods.lenssgs.core.util.LenUtil.*;

public final class GunTooltipHandler {
    public static final GunTooltipHandler INSTANCE = new GunTooltipHandler();

    private GunTooltipHandler() {
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onTooltip(ItemTooltipEvent e) {
        ItemStack stacc = e.getItemStack();

        if (e.getEntity() == null) {
            return;
        }
        Optional<Holder.Reference<GunMaterial>> gunmat = e.getEntity().level().registryAccess().lookup(LenDataReg.GUN_MAT_KEY).get().listElements().filter(
                data -> (Arrays.stream(data.value().getIngredient().getItems()).filter(item -> stacc.is(item.getItem())))
                        .anyMatch(maybe -> stacc.is(maybe.getItem()))).findFirst();

        gunmat.ifPresent(gunMaterialReference -> renderGunMaterial(e, stacc, gunMaterialReference.value()));
        gunmat.ifPresent(gunMaterialReference -> renderGunTraits(e, stacc, gunMaterialReference.value()));

        if (KeyManager.DISPLAY_STATS.get().isDown() && stacc.getItem() instanceof IModdable) {
            showGunData(stacc, e.getToolTip(),e.getEntity().level().registryAccess(),e);
        }

        if (stacc.is(LenTagKeys.REFILLS_AMMO_TAG)) {
            e.getToolTip().add(Component.translatable("refills_ammo").withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    private static void renderGunMaterial(ItemTooltipEvent e, ItemStack stacc, GunMaterial mat) {
        if (!(KeyManager.DISPLAY_STATS.get().isDown())) {
            e.getToolTip().add(translatableOf("gunmat_useful").copy().withStyle(ChatFormatting.DARK_GRAY));
            return;
        }
        if (e.getFlags().isAdvanced()) {
            e.getToolTip().add(Component.literal("GunMat ID: " + mat.getMatName()).withStyle(ChatFormatting.DARK_GRAY));
        }
        List<String> parts = new ArrayList<>(mat.getAllowedParts());
        if (!parts.isEmpty()) {
            int index = KeyManager.getMaterialCycleIndex(parts.size());
            List<StatMod> stats = mat.getStatModList().stream().filter(key -> key.allowedParts().contains(parts.get(index))).toList();
            e.getToolTip().add(translatableOf(parts.get(index)).copy().withColor(Color.LIGHTCYAN.getColor()));
            for (StatMod stat : stats) {
                e.getToolTip().add(translatableOf(stat.stat()).copy().append(LenUtil.bonusAppend(String.valueOf(stat.val()))));
            }
        }
    }

    private static void renderGunTraits(ItemTooltipEvent e, ItemStack stacc, GunMaterial mat) {
        if (!(KeyManager.DISPLAY_TRAITS.get().isDown())) {
            return;
        }
        if (e.getFlags().isAdvanced()) {
            e.getToolTip().add(Component.literal("GunMat ID: " + mat.getMatName()).withStyle(ChatFormatting.DARK_GRAY));
        }

        List<String> parts = new ArrayList<>(mat.getAllowedParts());
        if (!parts.isEmpty()) {
            int index = KeyManager.getMaterialCycleIndex(parts.size());
            if (mat.getTraitLevelList() == null) {
                return;
            }
            List<TraitLevel> traits = mat.getTraitLevelList().stream().filter(key -> key.allowedParts().contains(parts.get(index))).toList();
            e.getToolTip().add(translatableOf(parts.get(index)).copy().withColor(Color.LIGHTCYAN.getColor()));
            for (TraitLevel trait : traits) {
                e.getToolTip().add(translatableOf(trait.trait()).copy().append(String.valueOf(trait.level())));
            }
        }
    }

    private static void showGunData(ItemStack stack, List<Component> tooltips, HolderLookup.Provider prov,ItemTooltipEvent e) {
        if (!(stack.getItem() instanceof IModdable)) {
            return;
        }
        GunStatTraitPair stats = stack.getOrDefault(LenDataComponents.GUN_STAT_TRAITS, null);
        if (WeaponAmmoStats.safeGunStats(stack) && WeaponAmmoStats.safeGunLastAmmo(stack)) {
            GunStats realStats = stats.getStats();
            GunStats lastAmmo = stack.get(LenDataComponents.LAST_AMMO).getStats();
            tooltips.add(translatableOf("mindmg").copy().append(spaceAppend(truncateFloat(realStats.getDamageMin()))).append(bonusAppend(truncateFloat(lastAmmo.getDamageMin())))
                    .append(spaceAppend(translatableOf("maxdmg"))).append(spaceAppend(truncateFloat(realStats.getDamageMax()))).append(bonusAppend(truncateFloat(lastAmmo.getDamageMax()))));
            tooltips.add(translatableOf("firerate").copy().append(spaceAppend(truncateFloat(realStats.getFirerate()))).append(bonusAppend(truncateFloat(lastAmmo.getFirerate())))
                    .append(spaceAppend(translatableOf("ammomax"))).append(spaceAppend(truncateFloat(realStats.getAmmo_max() / WeaponAmmoStats.AMMO_POINTS_MUL))));
            tooltips.add(translatableOf("velocity_mult").copy().append(spaceAppend(truncateFloat(realStats.getVelocityMult()))).append(bonusAppend(truncateFloat(lastAmmo.getVelocityMult())))
                    .append(spaceAppend(translatableOf("grav_mult"))).append(spaceAppend(truncateDouble(realStats.getGravMod()))).append(bonusAppend(truncateDouble(lastAmmo.getGravMod()))));
            tooltips.add(translatableOf("inacc").copy().append(spaceAppend(truncateFloat(realStats.getInaccuracy()))).append(bonusAppend(truncateFloat(lastAmmo.getInaccuracy())))
                    .append(spaceAppend("proj_count")).append(spaceAppend(truncateFloat(lastAmmo.getProjCount()))).append(bonusAppend(truncateFloat(realStats.getProjCount()))));
            tooltips.add(translatableOf("ammo_current").copy().append(spaceAppend(truncateFloat(WeaponAmmoStats.ammoAmountLeft(stack) / WeaponAmmoStats.AMMO_POINTS_MUL))));
        } else if (WeaponAmmoStats.safeGunStats(stack)) {
            GunStats realStats = stats.getStats();
            tooltips.add(translatableOf("mindmg").copy().append(spaceAppend(truncateFloat(realStats.getDamageMin())))
                    .append(spaceAppend(translatableOf("maxdmg"))).append(spaceAppend(truncateFloat(realStats.getDamageMax()))));
            tooltips.add(translatableOf("firerate").copy().append(spaceAppend(truncateFloat(realStats.getFirerate())))
                    .append(spaceAppend(translatableOf("ammomax"))).append(spaceAppend(truncateFloat(realStats.getAmmo_max() / WeaponAmmoStats.AMMO_POINTS_MUL))));
            tooltips.add(translatableOf("velocity_mult").copy().append(spaceAppend(truncateFloat(realStats.getVelocityMult())))
                    .append(spaceAppend(translatableOf("grav_mult"))).append(spaceAppend(truncateDouble(realStats.getGravMod()))));
            tooltips.add(translatableOf("inacc").copy().append(spaceAppend(truncateFloat(realStats.getInaccuracy())))
                    .append(spaceAppend("proj_count")).append(spaceAppend(truncateFloat(realStats.getProjCount()))));
            tooltips.add(translatableOf("ammo_current").copy().append(spaceAppend(truncateFloat(WeaponAmmoStats.ammoAmountLeft(stack) / WeaponAmmoStats.AMMO_POINTS_MUL))));
        } else if (stack.getOrDefault(LenDataComponents.GUN_PART_HOLDER,null)!=null) {
            var part = stack.get(LenDataComponents.GUN_PART_HOLDER);
            GunMaterial mat = MaterialMap.loadedMats(prov).get(part.getMaterial());
            if(mat !=null) {
                List<StatMod> matstats = mat.getStatModList().stream().filter(key -> key.allowedParts().contains(part.getName())).toList();
                List<TraitLevel> mattraits = mat.getTraitLevelList().stream().filter(key -> key.allowedParts().contains(part.getName())).toList();
                for (StatMod stat : matstats) {
                    e.getToolTip().add(translatableOf(stat.stat()).copy().append(LenUtil.bonusAppend(String.valueOf(stat.val()))));
                }
                for (TraitLevel trait : mattraits) {
                    e.getToolTip().add(translatableOf(trait.trait()).copy().append(String.valueOf(trait.level())));
                }
            }
        } else {
            tooltips.add(translatableOf("gunfake1"));
            tooltips.add(translatableOf("gunfake2"));
        }
    }
}
