package com.lensmods.lenssgs.core.util;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.api.IModdable;
import com.lensmods.lenssgs.core.datacomps.GunComp;
import com.lensmods.lenssgs.core.datacomps.GunPartHolder;
import com.lensmods.lenssgs.core.datacomps.GunStats;
import com.lensmods.lenssgs.core.weaponsystems.WeaponAmmoStats;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class LenUtil {
    public static List<EntityHitResult> rayTraceEntityList(Level worldIn, Entity projectile, Vec3 startVec, Vec3 endVec, AABB boundingBox, Predicate<Entity> filter) {
        List<EntityHitResult> entityRayTracelist = new ArrayList<>();

        for (Entity entity1 : worldIn.getEntities(projectile, boundingBox, filter)) {
            AABB axisalignedbb = entity1.getBoundingBox().inflate(0.3F);
            Optional<Vec3> optional = axisalignedbb.clip(startVec, endVec);
            if (optional.isPresent()) {
                entityRayTracelist.add(new EntityHitResult(entity1));
            }
        }
        return entityRayTracelist;
    }

    public static List<ItemStack> getMatchingStackList(Player player, ItemLike find) {
        List<ItemStack> found = new ArrayList<>();
        for(ItemStack item : player.getInventory().items) {
            if(item.is(find.asItem())) {

            }
        }
        return found;
    }

    public static MutableComponent withColor(MutableComponent text, Color color) {
        return withColor(text, color.getColor());
    }

    public static MutableComponent withColor(MutableComponent text, int color) {
        return text.withStyle(text.getStyle().withColor(net.minecraft.network.chat.TextColor.fromRgb(color & 0xFFFFFF)));
    }

    public static MutableComponent withColor(MutableComponent text, ChatFormatting color) {
        int colorCode = color.getColor() != null ? color.getColor() : 0xFFFFFF;
        return withColor(text, colorCode);
    }

    public static float randBetween(float min, float max) {
        return LensSGS.RANDY.nextFloat(min, max);
    }

    public static ResourceLocation modId(String key) {
        if (key.contains(":")) {
            return ResourceLocation.tryParse(key);
        }
        return ResourceLocation.tryParse(LensSGS.MODID + ":" + key);
    }

    public static String truncateFloat(float input) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.UP);
        return df.format(input);
    }
    public static String truncateDouble(Double input) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.UP);
        return df.format(input);
    }

    public static GunComp swapData(GunPartHolder dat, GunComp original) {
        List<GunPartHolder> swapped = new ArrayList<>(original.getPartList().size());
        for (GunPartHolder part : original.getPartList()) {
            if (part.getName().equals(dat.getName())) {
                swapped.add(dat);
            } else {
                swapped.add(part);
            }
        }
        return new GunComp(swapped);
    }

    public static Component translatableOf(String s) {
        return Component.translatable(LensSGS.MODID + "." + s);
    }

    public static Component spaceAppend(String s) {
        Component hold = Component.translatable(s);
        return Component.literal(" ").append(hold);
    }
    public static Component spaceAppend(Component s) {
        return Component.literal(" ").append(s);
    }

    public static void showGunData(ItemStack stack, Item.TooltipContext context, List<Component> tooltips, TooltipFlag tooltipFlag) {
        if (!(stack.getItem() instanceof IModdable)) {
            return;
        }
        GunStats stats = stack.getOrDefault(LenDataComponents.GUN_STATS, null);
        if (stats == null) {
            tooltips.add(translatableOf("gunfake1"));
            tooltips.add(translatableOf("gunfake2"));
        } else {
            tooltips.add(translatableOf("mindmg").copy().append(spaceAppend(truncateFloat(stats.getDamageMin())))
                    .append(spaceAppend(translatableOf("maxdmg"))).append(spaceAppend(truncateFloat(stats.getDamageMax()))));
            tooltips.add(translatableOf("firerate").copy().append(spaceAppend(truncateFloat(stats.getFirerate())))
                    .append(spaceAppend(translatableOf("ammomax"))).append(spaceAppend(truncateFloat(stats.getAmmo_max()))));
            tooltips.add(translatableOf("velocity_mult").copy().append(spaceAppend(truncateFloat(stats.getVelocityMult())))
                    .append(spaceAppend(translatableOf("grav_mult"))).append(spaceAppend(truncateDouble(stats.getGravMod()))));
            tooltips.add(translatableOf("ammo_current").copy().append(spaceAppend(truncateFloat(WeaponAmmoStats.ammoAmountLeft(stack)))));
        }
    }
}