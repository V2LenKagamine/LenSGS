package com.lensmods.lenssgs.core.entity.effects;

import com.lensmods.lenssgs.init.LenEnts;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ShatterEffect extends MobEffect {
    public ShatterEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
        if(livingEntity.hasEffect(LenEnts.SHATTER_EFFECT)) {
            amplifier += livingEntity.getEffect(LenEnts.SHATTER_EFFECT).getAmplifier();
        }
        super.onEffectAdded(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}