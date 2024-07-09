package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.datacomps.GunMaterial;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;


public class LenDataReg {
    public static final ResourceKey<Registry<GunMaterial>> GUN_MAT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"gun_materials"));

    public static void register(DataPackRegistryEvent.NewRegistry e) {
        e.dataPackRegistry(GUN_MAT_KEY,GunMaterial.GUN_MAT_CODEC,GunMaterial.GUN_MAT_CODEC);
    }
}
