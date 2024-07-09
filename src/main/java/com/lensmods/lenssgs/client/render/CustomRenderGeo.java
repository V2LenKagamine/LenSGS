package com.lensmods.lenssgs.client.render;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.ElementsModel;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;

import java.util.function.Function;

public class CustomRenderGeo implements IUnbakedGeometry<CustomRenderGeo> {
    private final ElementsModel base;
    private final boolean fake;

    public CustomRenderGeo(ElementsModel base,boolean fake) {
        this.base = base;
        this.fake = fake;
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides) {
        BakedModel bakedBase = base.bake(context, baker, spriteGetter, modelState, overrides);
        return new GunModel(bakedBase, context.useAmbientOcclusion(), context.isGui3d(), context.useBlockLight(),
                spriteGetter.apply(context.getMaterial("particle")), overrides, fake);
    }


    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
        if (!fake) {
            base.resolveParents(modelGetter, context);
        }
    }
}
