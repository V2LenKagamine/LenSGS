package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.datacomps.GunMaterial;
import com.lensmods.lenssgs.core.util.Color;
import com.lensmods.lenssgs.init.LenDataReg;
import com.lensmods.lenssgs.init.LenItems;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

import static com.lensmods.lenssgs.core.data.AllowedParts.*;
import static com.lensmods.lenssgs.core.data.MaterialStats.*;

public class LenMaterialGen {

    public static final List<String> PROPELLANT_ONLY = List.of(AllowedParts.PROPELLANT);
    public static final List<String> NOT_PROPELLANT = List.of(AllowedParts.CASING,AllowedParts.ROUND,AllowedParts.ACTION,AllowedParts.MAGAZINE,AllowedParts.BARREL,AllowedParts.RECEIVER,
            STOCK);

    public static final List<GunMaterial> VANILLA_MATS = List.of(
            new GunMaterial("wood",WOOD_STATS,WOOD_TRAITS,Tags.Items.RODS_WOODEN,Color.BURLYWOOD,List.of(STOCK,ROUND)),
            new GunMaterial("stone", STONE_STATS, null,Tags.Items.COBBLESTONES,Color.GREY,NOT_PROPELLANT),
            new GunMaterial("copper", COPPER_STATS, COPPER_TRAITS,Tags.Items.INGOTS_COPPER,Color.COPPER,NOT_PROPELLANT),
            new GunMaterial("iron",IRON_STATS,null,Tags.Items.INGOTS_IRON,Color.IRON,NOT_PROPELLANT),
            new GunMaterial("gold",GOLD_STATS,null,Tags.Items.INGOTS_GOLD,Color.GOLD,NOT_PROPELLANT),
            new GunMaterial("diamond",DIAMOND_STATS,DIAMOND_TRAITS,Tags.Items.GEMS_DIAMOND,Color.LIGHTCYAN,NOT_PROPELLANT),
            new GunMaterial("emerald",EMERALD_STATS,EMERALD_TRAITS,Tags.Items.GEMS_EMERALD,Color.LIMEGREEN,NOT_PROPELLANT),
            new GunMaterial("netherite",NETHERITE_STATS,null,Tags.Items.INGOTS_NETHERITE,Color.NETHERITE,NOT_PROPELLANT),
            new GunMaterial("gunpowder",GUNPOWDER_STATS,null,Tags.Items.GUNPOWDERS,Color.GRAY,PROPELLANT_ONLY),

            new GunMaterial("blaze",BLAZEROD_STATS,BLAZEROD_TRAITS,Tags.Items.RODS_BLAZE,Color.SUNGLOW,ROUND,BARREL,PROPELLANT),
            new GunMaterial("prismarine",PRISMARINE_STATS,PRISMARINE_TRAITS, Ingredient.of(Items.PRISMARINE_SHARD),Color.DARKCYAN,List.of(ROUND)),
            new GunMaterial("pirsmarine_gem",PRISMARINE_CRYSTAL_STATS,PRISMARINE_CRYSTAL_TRAITS,Tags.Items.GEMS_PRISMARINE,Color.LIGHTCYAN,ROUND),
            new GunMaterial("tnt",TNT_STATS,TNT_TRAITS,Ingredient.of(Blocks.TNT),Color.CORAL,PROPELLANT_ONLY),
            new GunMaterial("slime",SLIME_STATS,SLIME_TRAITS,Tags.Items.SLIMEBALLS,Color.LIMEGREEN,ROUND,STOCK),
            new GunMaterial("magma_cream",MAGMA_STATS,MAGMA_TRAITS,Ingredient.of(Items.MAGMA_CREAM),Color.ORANGERED,List.of(ROUND,STOCK)),
            new GunMaterial("quartz",QUARTZ_STATS,QUARTZ_TRAITS,Tags.Items.GEMS_QUARTZ,Color.WHITESMOKE,ROUND),
            new GunMaterial("breeze",BREEZE_ROD_STATS,BREEZE_ROD_TRAITS,Tags.Items.RODS_BREEZE,Color.LIGHTBLUE,BARREL,PROPELLANT),
            new GunMaterial("amethyst",AMETHYST_STATS,AMETHYST_TRAITS,Tags.Items.GEMS_AMETHYST,Color.MEDIUMPURPLE,ROUND),
            new GunMaterial("dragonsbreath",DRAGONS_BREATH_STATS,DRAGONS_BREATH_TRAITS,Ingredient.of(Items.DRAGON_BREATH),Color.DEEPPINK,List.of(ROUND)),
            new GunMaterial("netherstar",NETHER_STAR_STATS,NETHER_STAR_TRAITS,Ingredient.of(Items.NETHER_STAR),Color.WHITE,List.of(ROUND,PROPELLANT)),
            new GunMaterial("copper_rod",COPPER_ROD_STATS,COPPER_ROD_TRAITS,Ingredient.of(Items.LIGHTNING_ROD),Color.COPPER,List.of(ROUND)),
            new GunMaterial("end_rod",END_ROD_STATS,END_ROD_TRAITS,Ingredient.of(Items.END_ROD),Color.LIGHTYELLOW,List.of(ROUND,BARREL)),

            new GunMaterial("void_metal",VOID_STATS,VOID_TRAITS,Ingredient.of(LenItems.VOIDMETAL),Color.BLACK,NOT_PROPELLANT),
            new GunMaterial("wyrm_steel",WYRM_STATS,WYRM_TRAITS,Ingredient.of(LenItems.WYRMSTEEL),Color.MEDIUMPURPLE,NOT_PROPELLANT),
            new GunMaterial("blitz_gold",BLITZ_STATS,BLITZ_TRAITS,Ingredient.of(LenItems.BLITZGOLD),Color.GOLDENROD,NOT_PROPELLANT)
    );

    public static RegistrySetBuilder VANILLA_MATERIALS_BUILDER = new RegistrySetBuilder().add(LenDataReg.GUN_MAT_KEY, boot -> VANILLA_MATS.forEach(mat ->{
            boot.register(ResourceKey.create(LenDataReg.GUN_MAT_KEY,ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,mat.getMatName())),mat);
    }));
}
