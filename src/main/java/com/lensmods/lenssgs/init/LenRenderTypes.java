package com.lensmods.lenssgs.init;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;

public class LenRenderTypes {
    /*
    public static final RenderType TEXTURED_COLORED_TRIANGLES = RenderType.create(
            "tex_color_triangls",
            DefaultVertexFormat.POSITION_TEX_COLOR,
            VertexFormat.Mode.TRIANGLES,
            1536,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderType.POSITION_TEX_SHADER)
                    .setTextureState(
                            new RenderStateShard.TextureStateShard(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID, "textures/entity/test_texture.png"), false, false)
                    )
                    .setCullState(RenderStateShard.NO_CULL)
                    .createCompositeState(false)
    );
     */
    public static final RenderType END_TRIANGLES = RenderType.create(
            "tex_color_triangles",
            DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
            VertexFormat.Mode.TRIANGLES,
            1536,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderType.RENDERTYPE_END_PORTAL_SHADER)
                    .setTextureState(
                            RenderStateShard.MultiTextureStateShard.builder()
                                    .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
                                    .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
                                    .build()
                    )
                    .setCullState(RenderStateShard.CullStateShard.NO_CULL)
                    .createCompositeState(false)
    );
}
