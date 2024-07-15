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
    public static final TagKey<Item> GUNAMMO_MAT_TAG = ItemTags.create(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"usable_for_guns"));
    public static final TagKey<Item> REFILLS_AMMO_TAG = ItemTags.create(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"refills_ammo"));

    public LenTagKeys(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags) {
        super(pOutput, pLookupProvider, pBlockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(REFILLS_AMMO_TAG);
        this.tag(REFILLS_AMMO_TAG)
                .addOptionalTag(Tags.Items.GUNPOWDERS);

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
                .addOptional(ResourceLocation.withDefaultNamespace("nether_star"))
                .addOptional(ResourceLocation.withDefaultNamespace("prismarine_shard"))
                .addOptional(ResourceLocation.withDefaultNamespace("tnt"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"void_metal"))
                .addOptional(ResourceLocation.withDefaultNamespace("dragon_breath"));
    }
}
