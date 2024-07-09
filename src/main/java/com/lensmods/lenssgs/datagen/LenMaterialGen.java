package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.MaterialStats;
import com.lensmods.lenssgs.core.datacomps.GunMaterial;
import com.lensmods.lenssgs.core.util.Color;
import com.lensmods.lenssgs.init.LenDataReg;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

public class LenMaterialGen {

    public static final List<GunMaterial> VANILLA_MATS = List.of(
            new GunMaterial("copper", MaterialStats.COPPER, Tags.Items.INGOTS_COPPER, null,Color.COPPER),
            new GunMaterial("iron",MaterialStats.IRON,Tags.Items.INGOTS_IRON,null,Color.IRON)
    );

    public static RegistrySetBuilder VANILLA_MATERIALS_BUILDER = new RegistrySetBuilder().add(LenDataReg.GUN_MAT_KEY, boot -> {
        VANILLA_MATS.forEach(mat -> {
            boot.register(ResourceKey.create(LenDataReg.GUN_MAT_KEY,ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,mat.getMatName())),mat);
        });
    });
}
