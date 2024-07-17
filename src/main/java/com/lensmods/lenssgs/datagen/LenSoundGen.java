package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.init.LenSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class LenSoundGen extends SoundDefinitionsProvider {
    public LenSoundGen(PackOutput output, ExistingFileHelper helper) {
        super(output, LensSGS.MODID, helper);
    }

    @Override
    public void registerSounds() {
        add(LenSounds.GUN_CLICK, SoundDefinition.definition().with(
                sound(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"gunclick")).preload()).subtitle("lenssgs.gun_click"));
        add(LenSounds.GUN_SHOT_BULLPUP, SoundDefinition.definition().with(
                sound(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"bullpupshot")).preload()).subtitle("lenssgs.gun_shot"));
        add(LenSounds.GUN_SHOT_RIFLE, SoundDefinition.definition().with(
                sound(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"rifleshot")).preload()).subtitle("lenssgs.gun_shot"));
        add(LenSounds.GUN_SHOT_PISTOL, SoundDefinition.definition().with(
                sound(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"pistolshot")).preload()).subtitle("lenssgs.gun_shot"));
        add(LenSounds.VOID_BOOM, SoundDefinition.definition().with(
                sound(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"voidreave")).preload()).subtitle("lenssgs.void_reave"));
    }
}
