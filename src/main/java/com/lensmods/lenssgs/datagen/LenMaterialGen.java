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
import static com.lensmods.lenssgs.datagen.LenTagKeys.*;

public class LenMaterialGen {

    public static final List<String> PROPELLANT_ONLY = List.of(AllowedParts.PROPELLANT);
    public static final List<String> NOT_PROPELLANT = List.of(AllowedParts.CASING,AllowedParts.ROUND,AllowedParts.ACTION,AllowedParts.MAGAZINE,AllowedParts.BARREL,AllowedParts.RECEIVER,
            STOCK);

    public static final List<GunMaterial> DEFAULT_MATS = List.of(
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
            new GunMaterial("fish",FISH_STATS,FISH_TRAITS,Tags.Items.FOODS_RAW_FISH,Color.LIGHTPINK,List.of(ROUND)),
            new GunMaterial("fire_charge",FIRE_CHARGE_STATS,FIRE_CHARGE_TRAITS,Ingredient.of(Items.FIRE_CHARGE),Color.LIGHTPINK,List.of(ROUND)),

            new GunMaterial("void_metal",VOID_STATS,VOID_TRAITS,Ingredient.of(LenItems.VOIDMETAL),Color.VOIDMETAL,NOT_PROPELLANT),
            new GunMaterial("wyrm_steel",WYRM_STATS,WYRM_TRAITS,Ingredient.of(LenItems.WYRMSTEEL),Color.MEDIUMPURPLE,NOT_PROPELLANT),
            new GunMaterial("blitz_gold",BLITZ_STATS,BLITZ_TRAITS,Ingredient.of(LenItems.BLITZGOLD),Color.GOLDENROD,NOT_PROPELLANT),
            //Common Mats
            new GunMaterial("bronze",BRONZE_STATS,null,BRONZE_TAG,Color.BRONZE,NOT_PROPELLANT),
            new GunMaterial("refined_obsidian",REF_OBSIDIAN_STATS,REF_OBSIDIAN_TRAITS,REFINED_OB_TAG,Color.OBSIDIAN,NOT_PROPELLANT),
            new GunMaterial("refined_glowstone",REF_GLOWSTONE_STATS,REF_GLOWSTONE_TRAITS,REFINED_GLOW_TAG,Color.GLOWSTONE,NOT_PROPELLANT),
            new GunMaterial("steel",STEEL_STATS,null,STEEL_TAG,Color.STEEL,NOT_PROPELLANT),
            new GunMaterial("osmium",OSMIUM_STATS,null,OSMIUM_TAG,Color.OSMIUM,NOT_PROPELLANT),
            new GunMaterial("tin",TIN_STATS,null,TIN_TAG,Color.LIGHTGRAY,NOT_PROPELLANT),
            new GunMaterial("lead",LEAD_STATS,null,LEAD_TAG,Color.LEAD,NOT_PROPELLANT),
            new GunMaterial("uranium",URANIUM_STATS,URANIUM_TRAITS,URANIUM_TAG,Color.DARKGREEN,NOT_PROPELLANT),
            new GunMaterial("aluminum",ALUMINUM_STATS,null,ALUMINUM_TAG,Color.ALICEBLUE,NOT_PROPELLANT),
            new GunMaterial("electrum",ELECTRUM_STATS,ELECTRUM_TRAITS,ELECTRUM_TAG,Color.LIGHTYELLOW,NOT_PROPELLANT),
            new GunMaterial("plutonium",PLUTONIUM_STATS,PLUTONIUM_TRAITS,PLUTONIUM_TAG,Color.INDIANRED,NOT_PROPELLANT),
            new GunMaterial("silver",SILVER_STATS,SILVER_TRAITS,SILVER_TAG,Color.SILVER,NOT_PROPELLANT),
            //Sgear Mats
            new GunMaterial("blaze_gold",BLAZEGOLD_STATS,BLAZEGOLD_TRAITS,BLAZEGOLD_TAG,Color.ORANGERED,NOT_PROPELLANT),
            new GunMaterial("crimson_iron",CRIMSON_IRON_STATS,CRIMSON_IRON_TRAITS,CRIMSON_IRON_TAG,Color.CRIMSON,NOT_PROPELLANT),
            new GunMaterial("crimson_steel",CRIMSON_STEEL_STATS,CRIMSON_STEEL_TRAITS,CRIMSON_STEEL_TAG,Color.CRIMSON.blendWith(Color.RED),NOT_PROPELLANT),
            new GunMaterial("azure_electrum",AZURE_ELECTRUM_STATS,AZURE_ELECTRUM_TRAITS,AZURE_ELECTRUM_TAG,Color.DEEPSKYBLUE,NOT_PROPELLANT),
            new GunMaterial("azure_silver",AZURE_SILVER_STATS,AZURE_SILVER_TRAITS,AZURE_SILVER_TAG,Color.LIGHTSKYBLUE,NOT_PROPELLANT),
            new GunMaterial("tyran_steel",TYRAN_STEEL_STATS,TYRAN_STEEL_TRAITS,TYRAN_STEEL_TAG,Color.MEDIUMPURPLE,NOT_PROPELLANT)
    );

    public static RegistrySetBuilder DEFAULT_MATERIALS_BUILDER = new RegistrySetBuilder().add(LenDataReg.GUN_MAT_KEY, boot -> DEFAULT_MATS.forEach(mat ->{
            boot.register(ResourceKey.create(LenDataReg.GUN_MAT_KEY,ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,mat.getMatName())),mat);
    }));
}
