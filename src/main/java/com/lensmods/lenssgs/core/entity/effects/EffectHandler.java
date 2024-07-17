package com.lensmods.lenssgs.core.entity.effects;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.init.LenEnts;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = LensSGS.MODID)
public class EffectHandler {
    @SubscribeEvent
    public static void onEntHarm(LivingDamageEvent.Pre e) {
        if(e.getEntity().hasEffect(LenEnts.SHATTER_EFFECT)) {
            float oldamage = e.getOriginalDamage();
            e.setNewDamage(oldamage + (e.getEntity().getEffect(LenEnts.SHATTER_EFFECT).getAmplifier() * 0.125f));
        }
    }
}
