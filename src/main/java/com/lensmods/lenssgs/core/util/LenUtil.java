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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class LenUtil {
    public static List<EntityHitResult> rayTraceEntityList(Level worldIn, Entity projectile, Vec3 startVec, Vec3 endVec, AABB boundingBox, Predicate<Entity> filter) {
        List<EntityHitResult> entityRayTracelist = new ArrayList<>();

        for(Entity entity1 : worldIn.getEntities(projectile, boundingBox, filter)) {
            AABB axisalignedbb = entity1.getBoundingBox().inflate(0.3F);
            Optional<Vec3> optional = axisalignedbb.clip(startVec, endVec);
            if (optional.isPresent()) {
                entityRayTracelist.add(new EntityHitResult(entity1));
            }
        }
        return entityRayTracelist;
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

    public static float randBetween(float min, float max)
    {
        return LensSGS.RANDY.nextFloat(min,max);
    }
    public static ResourceLocation modId(String key) {
        if(key.contains(":")) {return ResourceLocation.tryParse(key);}
        return ResourceLocation.tryParse(LensSGS.MODID + ":" + key);
    }

    public static GunComp swapData(GunPartHolder dat, GunComp original) {
        List<GunPartHolder> swapped = new ArrayList<>(original.getPartList().size());
        for(GunPartHolder part : original.getPartList()) {
            if(part.getName().equals(dat.getName())) {
                swapped.add(dat);
            }else {
                swapped.add(part);
            }
        }
        return new GunComp(swapped);
    }

    public static Component translatableOf(String s) {
        return Component.translatable(LensSGS.MODID +"." + s);
    }
    public static Component spaceAppend(String s) {
        return Component.literal(" " + s);
    }

    public static void showGunData(ItemStack stack, Item.TooltipContext context, List<Component> tooltips, TooltipFlag tooltipFlag) {
        if (!(stack.getItem() instanceof IModdable)) {return;}
        GunStats stats = stack.getOrDefault(LenDataComponents.GUN_STATS,new GunStats());
        if (stats.equals(new GunStats())) {
            tooltips.add(translatableOf("gunfake1"));
            tooltips.add(translatableOf("gunfake2"));
        }
        tooltips.add(translatableOf("mindmg").copy().append(spaceAppend(stats.getDamageMin().toString()))
                .append(translatableOf("maxdmg")).append(spaceAppend(stats.getDamageMin().toString())));
        tooltips.add(translatableOf("firerate").copy().append(spaceAppend(stats.getFirerate().toString()))
                .append(translatableOf("ammomax")).append(spaceAppend(stats.getAmmo_max().toString())));
        tooltips.add(translatableOf("velocity_mult").copy().append(spaceAppend(stats.getVelocityMult().toString()))
                .append(translatableOf("grav_mult")).append(spaceAppend(stats.getGravMod().toString())));
        tooltips.add(translatableOf("ammo_current").copy().append(spaceAppend(String.valueOf(WeaponAmmoStats.ammoAmountLeft(stack)))));
    }
}
