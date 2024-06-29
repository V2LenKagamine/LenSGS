package com.lensmods.lenssgs.core.entity.render;

import com.lensmods.lenssgs.core.entity.GenericProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class GenericProjRender extends EntityRenderer<GenericProjectile> {
    public GenericProjRender(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(GenericProjectile pEntity) {
        return null;
    }

    @Override
    public void render(GenericProjectile entity, float entityYaw, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int light)
    {
        if(entity.getVisualItem() == ItemStack.EMPTY || entity.tickCount <= 1)
        {
            return;
        }

        poseStack.pushPose();

            poseStack.mulPose(Axis.YP.rotationDegrees(180F));
            poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
            Minecraft.getInstance().getItemRenderer().renderStatic(entity.getVisualItem(), ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, poseStack, pBuffer, entity.level(), 0);

        poseStack.popPose();
    }
}
