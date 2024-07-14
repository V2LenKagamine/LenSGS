package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.AllowedParts;
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

    public static final List<String> PROPELLANT_ONLY = List.of(AllowedParts.PROPELLANT);
    public static final List<String> NOT_PROPELLANT = List.of(AllowedParts.CASING,AllowedParts.ROUND,AllowedParts.ACTION,AllowedParts.MAGAZINE,AllowedParts.BARREL,AllowedParts.RECEIVER,
            AllowedParts.STOCK);

    public static final List<GunMaterial> VANILLA_MATS = List.of(
            new GunMaterial("wood",MaterialStats.WOOD,null,Tags.Items.RODS_WOODEN,Color.BURLYWOOD,List.of(AllowedParts.STOCK)),
            new GunMaterial("stone", MaterialStats.STONE_STATS, null,Tags.Items.COBBLESTONES,Color.GREY,NOT_PROPELLANT),
            new GunMaterial("copper", MaterialStats.COPPER_STATS, null,Tags.Items.INGOTS_COPPER,Color.COPPER,NOT_PROPELLANT),
            new GunMaterial("iron",MaterialStats.IRON_STATS,null,Tags.Items.INGOTS_IRON,Color.IRON,NOT_PROPELLANT),
            new GunMaterial("gold",MaterialStats.GOLD_STATS,null,Tags.Items.INGOTS_GOLD,Color.GOLD,NOT_PROPELLANT),
            new GunMaterial("diamond",MaterialStats.DIAMOND_STATS,null,Tags.Items.GEMS_DIAMOND,Color.LIGHTCYAN,NOT_PROPELLANT),
            new GunMaterial("netherite",MaterialStats.NETHERITE_STATS,null,Tags.Items.INGOTS_NETHERITE,Color.NETHERITE,NOT_PROPELLANT),
            new GunMaterial("gunpowder",MaterialStats.GUNPOWDER_STATS,null,Tags.Items.GUNPOWDERS,Color.GRAY,PROPELLANT_ONLY),

            new GunMaterial("blaze",MaterialStats.BLAZEROD_STATS,MaterialStats.BLAZEROD_TRAITS,Tags.Items.RODS_BLAZE,Color.PEACHPUFF,List.of(AllowedParts.ROUND,AllowedParts.BARREL))
    );

    public static RegistrySetBuilder VANILLA_MATERIALS_BUILDER = new RegistrySetBuilder().add(LenDataReg.GUN_MAT_KEY, boot -> VANILLA_MATS.forEach(mat ->{
            boot.register(ResourceKey.create(LenDataReg.GUN_MAT_KEY,ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,mat.getMatName())),mat);
    }));
}
