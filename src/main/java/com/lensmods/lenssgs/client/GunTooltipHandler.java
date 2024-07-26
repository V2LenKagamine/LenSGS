package com.lensmods.lenssgs.client;

import com.lensmods.lenssgs.api.IModdable;
import com.lensmods.lenssgs.core.data.MaterialMap;
import com.lensmods.lenssgs.core.data.MaterialStats;
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

import java.util.*;

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
                data -> (Arrays.stream(data.value().getIngredient().getItems())).anyMatch(maybe -> maybe.is(stacc.getItem()))).findFirst();

        gunmat.ifPresent(gunMaterialReference -> {
            renderGunMaterial(e, stacc, gunMaterialReference.value());
            renderGunTraits(e, stacc, gunMaterialReference.value());
            appendToolTips(e, stacc);
        });

        if (stacc.getItem() instanceof IModdable) {
            showGunData(stacc, e.getToolTip(), e.getEntity().level().registryAccess(), e);
            appendToolTips(e, stacc);
        }

        if (stacc.is(LenTagKeys.REFILLS_AMMO_TAG)) {
            e.getToolTip().add(Component.translatable("refills_ammo").withStyle(ChatFormatting.GRAY));
        }
    }

    private static void appendToolTips(ItemTooltipEvent e, ItemStack stacc) {
        boolean showComp = WeaponAmmoStats.safeGunComp(stacc);
        e.getToolTip().add(LenUtil.translatableOf("display_stats").copy().withColor(KeyManager.DISPLAY_STATS.get().isDown() ? Color.LIGHTBLUE.getColor() : Color.LIGHTCORAL.getColor())
                .append(spaceAppend(LenUtil.translatableOf("display_traits")).copy().withColor(KeyManager.DISPLAY_TRAITS.get().isDown() ? Color.LIGHTBLUE.getColor() : Color.LIGHTCORAL.getColor()))
                .append(showComp ? spaceAppend(LenUtil.translatableOf("display_composition").copy().withColor(KeyManager.DISPLAY_CONSTRUCTION.get().isDown() ? Color.LIGHTBLUE.getColor() : Color.LIGHTCORAL.getColor())) : Component.empty()));
        e.getToolTip().add(KeyManager.DISPLAY_STATS.get().getTranslatedKeyMessage().copy().withColor(KeyManager.DISPLAY_STATS.get().isDown() ? Color.LIGHTBLUE.getColor() : Color.LIGHTCORAL.getColor())
                .append(spaceAppend(KeyManager.DISPLAY_TRAITS.get().getTranslatedKeyMessage().copy().withColor(KeyManager.DISPLAY_TRAITS.get().isDown() ? Color.LIGHTBLUE.getColor() : Color.LIGHTCORAL.getColor())))
                .append(showComp ? spaceAppend(KeyManager.DISPLAY_CONSTRUCTION.get().getTranslatedKeyMessage().copy().withColor(KeyManager.DISPLAY_CONSTRUCTION.get().isDown() ? Color.LIGHTBLUE.getColor() : Color.LIGHTCORAL.getColor())) : Component.empty()));
        e.getToolTip().add(Component.literal("Press ").append(KeyManager.CYCLE_NEXT.get().getTranslatedKeyMessage()).append(Component.literal(" or ")).append(KeyManager.CYCLE_BACK.get().getTranslatedKeyMessage()).append(Component.literal(" to cycle part types.")).copy().withStyle(ChatFormatting.GRAY));

    }

    private static void renderGunMaterial(ItemTooltipEvent e, ItemStack stacc, GunMaterial mat) {
        if (!(KeyManager.DISPLAY_STATS.get().isDown())) {
            e.getToolTip().add(translatableOf("gunmat_useful").copy().withStyle(ChatFormatting.GRAY));
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
            for (StatMod stat : stats.stream().sorted().toList()) {
                boolean invert =stat.stat().equals(MaterialStats.FIRE_RATE) ||stat.stat().equals(MaterialStats.GRAVITY_MOD) ||stat.stat().equals(MaterialStats.INACCURACY_DEG);
                e.getToolTip().add(translatableOf(stat.stat()).copy().append(spaceAppend(translatableOf(stat.modType())))
                        .append(invert?maliceAppend(String.valueOf(stat.val())):bonusAppend(String.valueOf(stat.val()))));
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
            e.getToolTip().add(translatableOf(parts.get(index)).copy().withStyle(ChatFormatting.GRAY));
            List<TraitLevel> traits = mat.getTraitLevelList().stream().filter(key -> (key.allowedParts().contains(parts.get(index)) || key.allowedParts().contains("all"))).toList();
            Map<String,Integer> toDisplay = new HashMap<>();
            for (TraitLevel trait : traits.stream().sorted().toList()) {
                if(toDisplay.containsKey(trait.trait())) {
                    int temp = toDisplay.get(trait.trait());
                    toDisplay.remove(trait.trait());
                    toDisplay.put(trait.trait(),temp+trait.level());
                } else {
                    toDisplay.put(trait.trait(),trait.level());
                }
            }
            toDisplay.forEach((key,value) ->  {
                    boolean invert = key.equals(MaterialStats.FIRE_RATE) || key.equals(MaterialStats.GRAVITY_MOD) || key.equals(MaterialStats.INACCURACY_DEG);
                e.getToolTip().add(translatableOf(key).copy().withColor(Color.LIGHTCYAN.getColor()).append(invert?maliceAppend(String.valueOf(value)):bonusAppend(String.valueOf(value))));});
        }
    }

    private static void showGunData(ItemStack stack, List<Component> tooltips, HolderLookup.Provider prov, ItemTooltipEvent e) {
        if (!(stack.getItem() instanceof IModdable)) {
            return;
        }
        if (KeyManager.DISPLAY_STATS.get().isDown()) {
            GunStatTraitPair stats = stack.getOrDefault(LenDataComponents.GUN_STAT_TRAITS, null);
            if (WeaponAmmoStats.safeGunStats(stack) && WeaponAmmoStats.safeGunLastAmmo(stack)) {
                GunStats realStats = stats.getStats();
                GunStats lastAmmo = stack.get(LenDataComponents.LAST_AMMO).getStats();
                tooltips.add(translatableOf("mindmg").copy().withColor(Color.LIGHTBLUE.getColor()).append(spaceAppend(truncateFloat(realStats.getDamageMin()))).append(bonusAppend(truncateFloat(lastAmmo.getDamageMin())))
                        .append(spaceAppend(translatableOf("maxdmg"))).withColor(Color.LIGHTBLUE.getColor()).append(spaceAppend(truncateFloat(realStats.getDamageMax()))).append(bonusAppend(truncateFloat(lastAmmo.getDamageMax()))));
                tooltips.add(translatableOf("firerate").copy().withColor(Color.LIGHTBLUE.getColor()).append(spaceAppend(truncateFloat(realStats.getFirerate()))).append(maliceAppend(truncateFloat(lastAmmo.getFirerate())))
                        .append(spaceAppend(translatableOf("ammomax"))).withColor(Color.LIGHTBLUE.getColor()).append(spaceAppend(truncateFloat(realStats.getAmmo_max() / WeaponAmmoStats.AMMO_POINTS_MUL))));
                tooltips.add(translatableOf("velocity_mult").copy().withColor(Color.LIGHTBLUE.getColor()).append(spaceAppend(truncateFloat(realStats.getVelocityMult()))).append(bonusAppend(truncateFloat(lastAmmo.getVelocityMult())))
                        .append(spaceAppend(translatableOf("grav_mult"))).withColor(Color.LIGHTBLUE.getColor()).append(spaceAppend(truncateDouble(realStats.getGravMod()))).append(maliceAppend(truncateDouble(lastAmmo.getGravMod()))));
                tooltips.add(translatableOf("inacc").copy().withColor(Color.LIGHTBLUE.getColor()).append(spaceAppend(truncateFloat(realStats.getInaccuracy()))).append(maliceAppend(truncateFloat(lastAmmo.getInaccuracy())))
                        .append(spaceAppend(translatableOf("proj_count"))).withColor(Color.LIGHTBLUE.getColor()).append(spaceAppend(truncateFloat(lastAmmo.getProjCount()))).append(bonusAppend(truncateFloat(realStats.getProjCount())))
                        .append(spaceAppend(translatableOf("pierce"))).withColor(Color.LIGHTBLUE.getColor()).append(spaceAppend(truncateFloat(lastAmmo.getPierce()))).append(bonusAppend(truncateFloat(realStats.getPierce()))));
                tooltips.add(translatableOf("ammo_current").copy().withColor(Color.LIGHTBLUE.getColor()).append(spaceAppend(truncateFloat(WeaponAmmoStats.ammoAmountLeft(stack) / WeaponAmmoStats.AMMO_POINTS_MUL))));
            } else if (WeaponAmmoStats.safeGunStats(stack)) {
                GunStats realStats = stats.getStats();
                tooltips.add(translatableOf("mindmg").copy().withColor(Color.LIGHTBLUE.getColor()).append(bonusAppend(truncateFloat(realStats.getDamageMin())))
                        .append(spaceAppend(translatableOf("maxdmg"))).withColor(Color.LIGHTBLUE.getColor()).append(bonusAppend(truncateFloat(realStats.getDamageMax()))));
                tooltips.add(translatableOf("firerate").copy().withColor(Color.LIGHTBLUE.getColor()).append(maliceAppend(truncateFloat(realStats.getFirerate())))
                        .append(spaceAppend(translatableOf("ammomax"))).withColor(Color.LIGHTBLUE.getColor()).append(bonusAppend(truncateFloat(realStats.getAmmo_max() / WeaponAmmoStats.AMMO_POINTS_MUL))));
                tooltips.add(translatableOf("velocity_mult").copy().withColor(Color.LIGHTBLUE.getColor()).append(bonusAppend(truncateFloat(realStats.getVelocityMult())))
                        .append(spaceAppend(translatableOf("grav_mult"))).withColor(Color.LIGHTBLUE.getColor()).append(maliceAppend(truncateDouble(realStats.getGravMod()))));
                tooltips.add(translatableOf("inacc").copy().withColor(Color.LIGHTBLUE.getColor()).append(maliceAppend(truncateFloat(realStats.getInaccuracy())))
                        .append(spaceAppend(translatableOf("proj_count"))).withColor(Color.LIGHTBLUE.getColor()).append(bonusAppend(truncateFloat(realStats.getProjCount())))
                        .append(spaceAppend(translatableOf("pierce"))).withColor(Color.LIGHTBLUE.getColor()).append(bonusAppend(truncateFloat(realStats.getPierce()))));
                tooltips.add(translatableOf("ammo_current_ammo").copy().withColor(Color.LIGHTBLUE.getColor()).append(bonusAppend(truncateFloat(WeaponAmmoStats.ammoAmountLeft(stack) / WeaponAmmoStats.AMMO_POINTS_MUL))));
            }
        }
        if (KeyManager.DISPLAY_TRAITS.get().isDown()) {
            if(WeaponAmmoStats.safeGunComp(stack)) {
                Map<String,Integer> toDisplay = new HashMap<>();
                var partH = stack.get(LenDataComponents.GUN_COMP);
                for (GunPartHolder part : partH.getPartList()) {
                    GunMaterial mat = MaterialMap.loadedMats(prov).get(part.getMaterial());
                    if (mat != null) {
                        List<TraitLevel> mattraits = mat.getTraitLevelList().stream().filter(key -> key.allowedParts().contains(part.getName()) || key.allowedParts().contains("all")).toList();
                        for (TraitLevel trait : mattraits.stream().sorted().toList()) {
                            if(toDisplay.containsKey(trait.trait())) {
                                int temp = toDisplay.get(trait.trait());
                                toDisplay.remove(trait.trait());
                                toDisplay.put(trait.trait(),temp+trait.level());
                            } else {
                                toDisplay.put(trait.trait(),trait.level());
                            }
                        }
                    }
                }
                toDisplay.forEach((key,value) ->  e.getToolTip().add(translatableOf(key).copy().withColor(Color.LIGHTCYAN.getColor()).append(spaceAppend(String.valueOf(value)))));
            }
        }
        if (stack.getOrDefault(LenDataComponents.GUN_PART_HOLDER, null) != null) {
            var partH = stack.get(LenDataComponents.GUN_PART_HOLDER);
            GunMaterial mat = MaterialMap.loadedMats(prov).get(partH.getMaterial());
            if (mat != null) {
                List<StatMod> matstats = mat.getStatModList().stream().filter(key -> key.allowedParts().contains(partH.getName())).toList();
                List<TraitLevel> mattraits = mat.getTraitLevelList().stream().filter(key -> (key.allowedParts().contains(partH.getName())) || key.allowedParts().contains("all")).toList();
                if (KeyManager.DISPLAY_STATS.get().isDown()) {
                    for (StatMod stat : matstats) {
                        boolean invert =stat.stat().equals(MaterialStats.FIRE_RATE) ||stat.stat().equals(MaterialStats.GRAVITY_MOD) ||stat.stat().equals(MaterialStats.INACCURACY_DEG);
                        e.getToolTip().add(translatableOf(stat.stat()).copy().append(spaceAppend(translatableOf(stat.modType())))
                                .append(invert?maliceAppend(String.valueOf(stat.val())):bonusAppend(String.valueOf(stat.val()))));
                    }
                }
                if (KeyManager.DISPLAY_TRAITS.get().isDown()) {
                    Map<String,Integer> toDisplay = new HashMap<>();
                    for (TraitLevel trait : mattraits.stream().sorted().toList()) {
                        if(toDisplay.containsKey(trait.trait())) {
                            int temp = toDisplay.get(trait.trait());
                            toDisplay.remove(trait.trait());
                            toDisplay.put(trait.trait(),temp+trait.level());
                        } else {
                            toDisplay.put(trait.trait(),trait.level());
                        }
                    }
                    toDisplay.forEach((key,value) ->  {
                        boolean invert = key.equals(MaterialStats.FIRE_RATE) || key.equals(MaterialStats.GRAVITY_MOD) || key.equals(MaterialStats.INACCURACY_DEG);
                        e.getToolTip().add(translatableOf(key).copy().withColor(Color.LIGHTCYAN.getColor()).append(invert?maliceAppend(String.valueOf(value)):bonusAppend(String.valueOf(value))));});
                }
            }
        } else if (KeyManager.DISPLAY_CONSTRUCTION.get().isDown() && WeaponAmmoStats.safeGunComp(stack)) {
            for (GunPartHolder partH : stack.get(LenDataComponents.GUN_COMP).getPartList()) {
                GunMaterial mat = MaterialMap.loadedMats(prov).get(partH.getMaterial());
                if (mat != null) {
                    e.getToolTip().add(partH.getTotalName().copy().withColor(mat.getColor().getColor()));
                }
            }
        } else if (!WeaponAmmoStats.safeGunComp(stack)) {
            tooltips.add(translatableOf("gunfake1"));
            tooltips.add(translatableOf("gunfake2"));
        }
    }
}
