package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.entity.GenericProjectile;
import com.lensmods.lenssgs.core.entity.effects.ShatterEffect;
import com.lensmods.lenssgs.core.util.Color;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class LenEnts {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, LensSGS.MODID);

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> registerThing(String id, BiFunction<EntityType<T>, Level, T> function)
    {
        return ENTITIES.register(id, () -> EntityType.Builder.of(function::apply, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .setTrackingRange(20000)
                .noSummon()
                .fireImmune()
                .clientTrackingRange(20000)
                .setShouldReceiveVelocityUpdates(false)
                .build(id));
    }

    public static final DeferredHolder<EntityType<?>, EntityType<GenericProjectile>> GENERIC_PROJ = registerThing("generic_projectile",GenericProjectile::new);

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT,LensSGS.MODID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION,LensSGS.MODID);

    public static final DeferredHolder<MobEffect, ShatterEffect> SHATTER_EFFECT = EFFECTS.register("shatter",()-> new ShatterEffect(MobEffectCategory.HARMFUL, Color.CORAL.getColor()));
    public static final DeferredHolder<MobEffect, ShatterEffect> LACERATE_EFFECT = EFFECTS.register("lacerated",()-> new ShatterEffect(MobEffectCategory.HARMFUL, Color.DARKRED.getColor()));
    public static final Supplier<Potion> LACERATE_POTION = POTIONS.register("laceration_potion", ()-> new Potion(new MobEffectInstance(LACERATE_EFFECT,600)));


}
