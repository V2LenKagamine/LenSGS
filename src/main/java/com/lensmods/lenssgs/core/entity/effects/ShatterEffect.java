package com.lensmods.lenssgs.core.entity.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ShatterEffect extends MobEffect {
    public ShatterEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}