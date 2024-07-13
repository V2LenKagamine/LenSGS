package com.lensmods.lenssgs.client.render;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;

import java.util.function.Function;

public class CustomRenderGeo implements IUnbakedGeometry<CustomRenderGeo> {
    //private final BlockModel base;
    public CustomRenderGeo() {
        //this.base = base;
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides) {
        //BakedModel baked = new ElementsModel(base.getElements()).bake(context,baker,spriteGetter,modelState,overrides);
        return new GunModel(overrides);
    }


    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
    }
}
