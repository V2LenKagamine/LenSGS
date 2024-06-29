package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.entity.GenericProjectile;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class LenDamageTypes {
    public static final ResourceKey<DamageType> FIREARM = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(LensSGS.MODID, "firearm"));

    public static class Sources
    {
        private static Holder.Reference<DamageType> getHolder(RegistryAccess access, ResourceKey<DamageType> damageTypeKey)
        {
            return access.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageTypeKey);
        }

        private static DamageSource source(RegistryAccess access, ResourceKey<DamageType> damageTypeKey, @Nullable Entity directEntity, @Nullable Entity causingEntity)
        {
            return new DamageSource(getHolder(access, damageTypeKey), directEntity, causingEntity);
        }

        public static DamageSource projectile(RegistryAccess access, GenericProjectile projectile, LivingEntity entity)
        {
            return source(access, FIREARM, projectile, entity);
        }
    }
}
