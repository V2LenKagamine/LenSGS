package com.lensmods.lenssgs.core.entity.render;

import com.lensmods.lenssgs.core.entity.GenericProjectile;
import com.lensmods.lenssgs.core.util.Color;
import com.lensmods.lenssgs.core.util.RenderUtil;
import com.lensmods.lenssgs.init.LenRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GenericProjRender extends EntityRenderer<GenericProjectile> {
    public GenericProjRender(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(GenericProjectile pEntity) {
        return null;
    }

    @Override
    public void render(GenericProjectile entity, float entityYaw, float pTick, PoseStack poseStack, MultiBufferSource pBuffer, int light)
    {
        if(entity.getVisualItem() == ItemStack.EMPTY || entity.tickCount <= 1)
        {
            return;
        }
        poseStack.pushPose();
            if(entity.getVisualItem().is(Items.ENDER_EYE) && entity.getSecondLife()) {
                poseStack.translate(-0.5f,-0.5f,-0.5f);
                RenderUtil.renderSphere(poseStack,pBuffer, LenRenderTypes.END_TRIANGLES,light,
                        entity.getPercentLifeLeft() < 0.965f ?  1.5f*(Mth.square(entity.getPercentLifeLeft())) :  1.5f*(-10f * Mth.square(entity.getPercentLifeLeft())+10f),18, Color.BLACK);
            }
            else {
                poseStack.mulPose(Axis.YP.rotationDegrees(180F));
                poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));
                poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
                Minecraft.getInstance().getItemRenderer().renderStatic(entity.getVisualItem(), ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, poseStack, pBuffer, entity.level(), 0);
            }
        poseStack.popPose();
    }
}
