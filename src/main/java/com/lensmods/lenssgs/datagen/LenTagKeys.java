package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.init.LenItems;
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
    //Sgear
    public static final TagKey<Item> BLAZEGOLD_TAG = commonTag("ingots/blaze_gold");
    public static final TagKey<Item> CRIMSON_IRON_TAG = commonTag("ingots/crimson_iron");
    public static final TagKey<Item> CRIMSON_STEEL_TAG = commonTag("ingots/crimson_steel");
    public static final TagKey<Item> AZURE_SILVER_TAG = commonTag("ingots/azure_silver");
    public static final TagKey<Item> AZURE_ELECTRUM_TAG = commonTag("ingots/azure_electrum");
    public static final TagKey<Item> TYRAN_STEEL_TAG = commonTag("ingots/tyran_steel");
    //Modern Indust
    public static final TagKey<Item> STAINLESS_STEEL_TAG = commonTag("ingots/stainless_steel");
    public static final TagKey<Item> TUNGSTEN_TAG = commonTag("ingots/tungsten");
    public static final TagKey<Item> TITANIUM_TAG = commonTag("ingots/titanium");
    //Mekanism
    public static final TagKey<Item> INFUSED_ALLOY_TAG = ItemTags.create(ResourceLocation.fromNamespaceAndPath("mekanism","alloys/infused"));
    public static final TagKey<Item> REINFORCED_ALLOY_TAG = ItemTags.create(ResourceLocation.fromNamespaceAndPath("mekanism","alloys/reinforced"));
    public static final TagKey<Item> ATOMIC_ALLOY_TAG = ItemTags.create(ResourceLocation.fromNamespaceAndPath("mekanism","alloys/atomic"));



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
        this.tag(ItemTags.WEAPON_ENCHANTABLE)
                .add(LenItems.GUN_BASE.get());
        this.tag(ItemTags.SWORD_ENCHANTABLE)
                .add(LenItems.GUN_BASE.get());

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
                .addOptionalTag(Tags.Items.FOODS_RAW_FISH)
                .addOptional(ResourceLocation.withDefaultNamespace("end_rod"))
                .addOptional(ResourceLocation.withDefaultNamespace("fire_charge"))
                .addOptional(ResourceLocation.withDefaultNamespace("magma_cream"))
                .addOptional(ResourceLocation.withDefaultNamespace("lightning_rod"))
                .addOptional(ResourceLocation.withDefaultNamespace("nether_star"))
                .addOptional(ResourceLocation.withDefaultNamespace("prismarine_shard"))
                .addOptional(ResourceLocation.withDefaultNamespace("tnt"))
                .addOptional(ResourceLocation.withDefaultNamespace("dragon_breath"))
                .addOptional(ResourceLocation.withDefaultNamespace("echo_shard"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"void_metal"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"blitz_gold"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"wyrm_steel"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"hocufe_ingot"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"nurukukan_ingot"))
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
                .addOptionalTag(SILVER_TAG)
                .addOptionalTag(BLAZEGOLD_TAG)
                .addOptionalTag(CRIMSON_IRON_TAG)
                .addOptionalTag(CRIMSON_STEEL_TAG)
                .addOptionalTag(AZURE_SILVER_TAG)
                .addOptionalTag(AZURE_ELECTRUM_TAG)
                .addOptionalTag(TYRAN_STEEL_TAG)
                .addOptionalTag(STAINLESS_STEEL_TAG)
                .addOptionalTag(TITANIUM_TAG)
                .addOptionalTag(TUNGSTEN_TAG)
                .addOptionalTag(REINFORCED_ALLOY_TAG)
                .addOptionalTag(INFUSED_ALLOY_TAG)
                .addOptionalTag(ATOMIC_ALLOY_TAG)
        ;

    }
}
