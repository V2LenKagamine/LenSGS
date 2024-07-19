package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.LensSGS;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class LenTagKeys extends ItemTagsProvider {
    //CommonTags
    public static final TagKey<Item> BRONZE_TAG = commonTag("ingots/bronze");
    public static final TagKey<Item> REFINED_OB_TAG = commonTag("ingots/refined_obsidian");
    public static final TagKey<Item> REFINED_GLOW_TAG = commonTag("ingots/refined_glowstone");
    public static final TagKey<Item> STEEL_TAG = commonTag("ingots/steel");
    public static final TagKey<Item> OSMIUM_TAG = commonTag("ingots/osmium");
    public static final TagKey<Item> TIN_TAG = commonTag("ingots/tin");
    public static final TagKey<Item> LEAD_TAG = commonTag("ingots/lead");
    public static final TagKey<Item> URANIUM_TAG = commonTag("ingots/uranium");
    public static final TagKey<Item> ALUMINUM_TAG = commonTag("ingots/aluminum");
    public static final TagKey<Item> ELECTRUM_TAG = commonTag("ingots/electrum");
    public static final TagKey<Item> PLUTONIUM_TAG = commonTag("ingots/plutonium");
    public static final TagKey<Item> SILVER_TAG = commonTag("ingots/silver");


    public static TagKey<Item> commonTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c",path));
    }
    //My stuff
    public static final TagKey<Item> GUNAMMO_MAT_TAG = ItemTags.create(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"usable_for_guns"));
    public static final TagKey<Item> REFILLS_AMMO_TAG = ItemTags.create(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"refills_ammo"));

    public LenTagKeys(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags) {
        super(pOutput, pLookupProvider, pBlockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(REFILLS_AMMO_TAG);
        this.tag(REFILLS_AMMO_TAG)
                .addOptionalTag(Tags.Items.GUNPOWDERS)
                .addOptional(ResourceLocation.withDefaultNamespace("tnt"))
                .addOptional(ResourceLocation.withDefaultNamespace("wind_charge"));

        this.tag(GUNAMMO_MAT_TAG);
        this.tag(GUNAMMO_MAT_TAG)
                .addOptionalTag(Tags.Items.RODS_WOODEN)
                .addOptionalTag(Tags.Items.COBBLESTONES)
                .addOptionalTag(Tags.Items.INGOTS_COPPER)
                .addOptionalTag(Tags.Items.INGOTS_IRON)
                .addOptionalTag(Tags.Items.INGOTS_GOLD)
                .addOptionalTag(Tags.Items.GEMS_DIAMOND)
                .addOptionalTag(Tags.Items.INGOTS_NETHERITE)
                .addOptionalTag(Tags.Items.GUNPOWDERS)
                .addOptionalTag(Tags.Items.RODS_BLAZE)
                .addOptionalTag(Tags.Items.SLIMEBALLS)
                .addOptionalTag(Tags.Items.GEMS_PRISMARINE)
                .addOptionalTag(Tags.Items.RODS_BREEZE)
                .addOptionalTag(Tags.Items.GEMS_QUARTZ)
                .addOptionalTag(Tags.Items.GEMS_AMETHYST)
                .addOptionalTag(Tags.Items.GEMS_EMERALD)
                .addOptional(ResourceLocation.withDefaultNamespace("end_rod"))
                .addOptional(ResourceLocation.withDefaultNamespace("magma_cream"))
                .addOptional(ResourceLocation.withDefaultNamespace("lightning_rod"))
                .addOptional(ResourceLocation.withDefaultNamespace("nether_star"))
                .addOptional(ResourceLocation.withDefaultNamespace("prismarine_shard"))
                .addOptional(ResourceLocation.withDefaultNamespace("tnt"))
                .addOptional(ResourceLocation.withDefaultNamespace("dragon_breath"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"void_metal"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"blitz_gold"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"wyrm_steel"))
                .addOptionalTag(BRONZE_TAG)
                .addOptionalTag(REFINED_OB_TAG)
                .addOptionalTag(REFINED_GLOW_TAG)
                .addOptionalTag(STEEL_TAG)
                .addOptionalTag(OSMIUM_TAG)
                .addOptionalTag(TIN_TAG)
                .addOptionalTag(LEAD_TAG)
                .addOptionalTag(URANIUM_TAG)
                .addOptionalTag(ALUMINUM_TAG)
                .addOptionalTag(ELECTRUM_TAG)
                .addOptionalTag(PLUTONIUM_TAG)
                .addOptionalTag(SILVER_TAG);

    }
}
