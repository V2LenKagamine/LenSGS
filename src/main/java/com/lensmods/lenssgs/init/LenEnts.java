package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.entity.GenericProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;

public class LenEnts {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, LensSGS.MODID);

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> registerThing(String id, BiFunction<EntityType<T>, Level, T> function)
    {
        return ENTITIES.register(id, () -> EntityType.Builder.of(function::apply, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .setTrackingRange(100)
                .noSummon()
                .fireImmune()
                .setShouldReceiveVelocityUpdates(false)
                .clientTrackingRange(0)
                .build(id));
    }

    public static final DeferredHolder<EntityType<?>, EntityType<GenericProjectile>> GENERIC_PROJ = registerThing("generic_projectile",GenericProjectile::new);
}
