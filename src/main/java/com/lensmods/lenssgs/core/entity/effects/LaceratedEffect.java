package com.lensmods.lenssgs.core.entity.effects;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class LaceratedEffect extends MobEffect {
    public LaceratedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Registry<DamageType> dTypeReg = livingEntity.damageSources().damageTypes;
        Holder.Reference<DamageType> dType = dTypeReg.getHolderOrThrow(DamageTypes.MAGIC);
        livingEntity.hurt(new DamageSource(dType), 1.0F);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 50 >> amplifier;
        return i <= 0 || duration % i == 0;
    }
}
