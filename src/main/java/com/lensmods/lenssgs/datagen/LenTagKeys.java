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

    public LenTagKeys(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags) {
        super(pOutput, pLookupProvider, pBlockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(GUNAMMO_MAT_TAG);
        this.tag(GUNAMMO_MAT_TAG)
                .addOptionalTag(Tags.Items.INGOTS_COPPER)
                .addOptionalTag(Tags.Items.INGOTS_IRON)
                .addOptionalTag(Tags.Items.INGOTS_GOLD);
    }
}