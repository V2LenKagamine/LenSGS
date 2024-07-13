package com.lensmods.lenssgs.client.render;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GunModel implements IDynamicBakedModel {
    private static final Material MISSING_TEXTURE =
            new Material(TextureAtlas.LOCATION_BLOCKS, MissingTextureAtlasSprite.getLocation());

    private final ItemOverrides overrides;
    //private final BakedModel model;

    public GunModel(ItemOverrides overrides) {
        //this.model = baked;
        this.overrides = overrides;

    }

    // Use our attributes. Refer to the article on baked models for more information on the method's effects.
    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return MISSING_TEXTURE.sprite();
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }



    // Override this to true if you want to use a custom block entity renderer instead of the default renderer.
    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    // This is where the magic happens. Return a list of the quads to render here. Parameters are:
    // - The blockstate being rendered. May be null if rendering an item.
    // - The side being culled against. May be null, which means quads that cannot be occluded should be returned.
    // - A client-bound random source you can use for randomizing stuff.
    // - The extra data to use. Originates from a block entity (if present), or from BakedModel#getModelData().
    // - The render type for which quads are being requested.
    // NOTE: This may be called many times in quick succession, up to several times per block.
    // This should be as fast as possible and use caching wherever applicable.

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource, ModelData modelData, @Nullable RenderType renderType) {
        List<BakedQuad> list = new ArrayList<>();
        return list;
    }
}
