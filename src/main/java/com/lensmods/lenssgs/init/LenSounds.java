package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LenSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, LensSGS.MODID);

    public static final DeferredHolder<SoundEvent,SoundEvent> GUN_SHOT_PISTOL = SOUNDS.register("pistolshot",
            ()-> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"pistolshot")));
    public static final DeferredHolder<SoundEvent,SoundEvent> GUN_SHOT_BULLPUP = SOUNDS.register("bullpupshot",
            ()-> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"bullpupshot")));
    public static final DeferredHolder<SoundEvent,SoundEvent> GUN_SHOT_RIFLE = SOUNDS.register("rifleshot",
            ()-> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"rifleshot")));

    public static final DeferredHolder<SoundEvent,SoundEvent> GUN_CLICK = SOUNDS.register("gunclick",
            ()-> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"gunclick")));
    public static final DeferredHolder<SoundEvent,SoundEvent> VOID_BOOM = SOUNDS.register("voidreave",
            ()-> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"voidreave")));
}
