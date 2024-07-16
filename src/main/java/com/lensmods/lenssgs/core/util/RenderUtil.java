package com.lensmods.lenssgs.core.util;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.datacomps.ModelColorPair;
import com.lensmods.lenssgs.core.items.GunBaseItem;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;

import static net.neoforged.neoforge.client.ClientHooks.handleCameraTransforms;

public class RenderUtil
{
    private static final ModelResourceLocation TRIDENT_MODEL = ModelResourceLocation.vanilla("trident", "inventory");
    private static final ModelResourceLocation SPYGLASS_MODEL = ModelResourceLocation.vanilla("spyglass", "inventory");

    public static void scissor(int x, int y, int width, int height)
    {
        Minecraft mc = Minecraft.getInstance();
        int scale = (int) mc.getWindow().getGuiScale();
        GL11.glScissor(x * scale, mc.getWindow().getScreenHeight() - y * scale - height * scale, Math.max(0, width * scale), Math.max(0, height * scale));
    }

    public static BakedModel getModel(Item item)
    {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(new ItemStack(item));
    }

    public static BakedModel getModel(ItemStack item)
    {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(item);
    }

    public static void rotateZ(PoseStack poseStack, float xOffset, float yOffset, float rotation)
    {
        poseStack.translate(xOffset, yOffset, 0);
        poseStack.mulPose(Axis.ZN.rotationDegrees(rotation));
        poseStack.translate(-xOffset, -yOffset, 0);
    }

    public static void renderGun(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay, @Nullable LivingEntity entity)
    {
        renderModel(stack, ItemDisplayContext.NONE, poseStack, buffer, light, overlay, entity);
    }

    public static void renderModel(ItemStack child, ItemStack parent, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay)
    {
        BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(child);
        renderModel(model, ItemDisplayContext.NONE, null, child, parent, poseStack, buffer, light, overlay);
    }

    public static void renderModel(ItemStack stack, ItemDisplayContext display, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay, @Nullable LivingEntity entity)
    {
        BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(stack);
        if(entity != null)
        {
            model = Minecraft.getInstance().getItemRenderer().getModel(stack, entity.level(), entity, 0);
        }
        renderModel(model, display, stack, poseStack, buffer, light, overlay);
    }

    public static void renderModel(ItemStack stack, ItemDisplayContext display, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay, @Nullable Level world, @Nullable LivingEntity entity)
    {
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(stack, world, entity, 0);
        renderModel(model, display, stack, poseStack, buffer, light, overlay);
    }

    public static void renderModel(BakedModel model, ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay)
    {
        renderModel(model, ItemDisplayContext.NONE, stack, poseStack, buffer, light, overlay);
    }

    public static void renderModel(BakedModel model, ItemDisplayContext display, ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay)
    {
        renderModel(model, display, null, stack, ItemStack.EMPTY, poseStack, buffer, light, overlay);
    }

    public static void renderModel(BakedModel model, ItemDisplayContext display, @Nullable Runnable transform, ItemStack stack, ItemStack parent, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay)
    {
        if(!stack.isEmpty())
        {
            poseStack.pushPose();
            boolean flag = display == ItemDisplayContext.GUI || display == ItemDisplayContext.GROUND || display == ItemDisplayContext.FIXED;
            if(flag)
            {
                if(stack.is(Items.TRIDENT))
                {
                    model = Minecraft.getInstance().getModelManager().getModel(TRIDENT_MODEL);
                }
                else if(stack.is(Items.SPYGLASS))
                {
                    model = Minecraft.getInstance().getModelManager().getModel(SPYGLASS_MODEL);
                }
            }

            model = model.applyTransform(display, poseStack, false);
            poseStack.translate(-0.5D, -0.5D, -0.5D);
            if(!model.isCustomRenderer() && (stack.getItem() != Items.TRIDENT || flag))
            {
                boolean entity = true;
                if(display != ItemDisplayContext.GUI && !display.firstPerson() && stack.getItem() instanceof BlockItem)
                {
                    Block block = ((BlockItem) stack.getItem()).getBlock();
                    entity = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                }

                RenderType renderType = getRenderType(stack, entity);
                VertexConsumer builder;
                if (entity)
                {
                    builder = ItemRenderer.getFoilBufferDirect(buffer, renderType, true, stack.hasFoil() || parent.hasFoil());
                }
                else
                {
                    builder = ItemRenderer.getFoilBuffer(buffer, renderType, true, stack.hasFoil() || parent.hasFoil());
                }

                renderModel(model, stack, parent, transform, poseStack, builder, light, overlay);
            }
            else
            {
                IClientItemExtensions.of(stack).getCustomRenderer().renderByItem(stack, display, poseStack, buffer, light, overlay);
            }

            poseStack.popPose();
        }
    }
    public static void forceItemWithColorBlend(BakedModel model, ItemDisplayContext display, @Nullable Runnable transform, ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay, Color color, float alpha)
    {
        if(!stack.isEmpty())
        {
            poseStack.pushPose();
            model = model.applyTransform(display, poseStack, false);
            poseStack.translate(-0.5D, -0.5D, -0.5D);
            if(stack.getItem() != Items.TRIDENT)
            {
                RenderType renderType = getRenderType(stack, true);
                VertexConsumer builder;
                builder = ItemRenderer.getFoilBufferDirect(buffer, renderType, true, stack.hasFoil());
                forceRenderModelColorBlend(model, transform, poseStack, builder, light, overlay,color,alpha);
            }
            poseStack.popPose();
        }
    }

    public static void forceRenderModelColorBlend(BakedModel model, @Nullable Runnable transform, PoseStack poseStack, VertexConsumer buffer, int light, int overlay, Color color, float alpha)
    {
        if(transform != null)
        {
            transform.run();
        }
        RandomSource random = LensSGS.STORYTELLER;
        for(Direction direction : Direction.values())
        {
            random.setSeed(42L);
            forceRenderQuadsColorBlend(poseStack, buffer, model.getQuads(null, direction, random), light, overlay,color,alpha);
        }
        random.setSeed(42L);
        forceRenderQuadsColorBlend(poseStack, buffer, model.getQuads(null, null, random) ,light, overlay,color,alpha);
    }
    private static void forceRenderQuadsColorBlend(PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads, int light, int overlay, Color color, float alpha)
    {
        PoseStack.Pose entry = poseStack.last();

        for(BakedQuad quad : quads)
        {
            buffer.putBulkData(entry, quad, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), light, overlay,true);
        }
    }

    public static void forceItemWithColor(BakedModel model, ItemDisplayContext display, @Nullable Runnable transform, ItemStack stack, ItemStack parent, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay,Color color)
    {
        if(!stack.isEmpty())
        {
            poseStack.pushPose();
            model = model.applyTransform(display, poseStack, false);
            poseStack.translate(-0.5D, -0.5D, -0.5D);
            if(stack.getItem() != Items.TRIDENT)
            {
                RenderType renderType = getRenderType(stack, true);
                VertexConsumer builder;
                builder = ItemRenderer.getFoilBufferDirect(buffer, renderType, true, stack.hasFoil());
                forceRenderModel(model, stack, parent, transform, poseStack, builder, light, overlay,color);
            }
            poseStack.popPose();
        }
    }
    public static void forceRenderModel(BakedModel model, ItemStack stack, ItemStack parent, @Nullable Runnable transform, PoseStack poseStack, VertexConsumer buffer, int light, int overlay,Color color)
    {
        if(transform != null)
        {
            transform.run();
        }
        RandomSource random = RandomSource.create();
        for(Direction direction : Direction.values())
        {
            random.setSeed(42L);
            forceRenderQuads(poseStack, buffer, model.getQuads(null, direction, random), stack, parent, light, overlay,color);
        }
        random.setSeed(42L);
        forceRenderQuads(poseStack, buffer, model.getQuads(null, null, random), stack, parent, light, overlay,color);
    }
    private static void forceRenderQuads(PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads, ItemStack stack, ItemStack parent, int light, int overlay,Color color)
    {
        PoseStack.Pose entry = poseStack.last();
        for(BakedQuad quad : quads)
        {
            buffer.putBulkData(entry, quad, color.getRed(), color.getGreen(), color.getBlue(),color.getAlpha(),light, overlay);
        }
    }

    public static void renderModelWithTransforms(ItemStack child, ItemStack parent, ItemDisplayContext display, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay)
    {
        poseStack.pushPose();
        BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(child);
        model = handleCameraTransforms(poseStack, model, display, false);
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        renderItemWithoutTransforms(model, child, parent, poseStack, buffer, light, overlay);
        poseStack.popPose();
    }

    public static void renderItemWithoutTransforms(BakedModel model, ItemStack stack, ItemStack parent, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay)
    {
        RenderType renderType = getRenderType(stack, false);
        VertexConsumer builder = ItemRenderer.getFoilBuffer(buffer, renderType, true, stack.hasFoil() || parent.hasFoil());
        renderModel(model, stack, parent, null, poseStack, builder, light, overlay);
    }

    public static void renderItemWithoutTransforms(BakedModel model, ItemStack stack, ItemStack parent, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay, @Nullable Runnable transform)
    {
        RenderType renderType = getRenderType(stack, false);
        VertexConsumer builder = ItemRenderer.getFoilBuffer(buffer, renderType, true, stack.hasFoil() || parent.hasFoil());
        renderModel(model, stack, parent, transform, poseStack, builder, light, overlay);
    }
    public static void renderModel(BakedModel model, ItemStack stack, ItemStack parent, @Nullable Runnable transform, PoseStack poseStack, VertexConsumer buffer, int light, int overlay)
    {
        if(transform != null)
        {
            transform.run();
        }
        RandomSource random = RandomSource.create();
        for(Direction direction : Direction.values())
        {
            random.setSeed(42L);
            renderQuads(poseStack, buffer, model.getQuads(null, direction, random), stack, parent, light, overlay);
        }
        random.setSeed(42L);
        renderQuads(poseStack, buffer, model.getQuads(null, null, random), stack, parent, light, overlay);
    }

    private static void renderQuads(PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads, ItemStack stack, ItemStack parent, int light, int overlay)
    {
        PoseStack.Pose entry = poseStack.last();
        for(BakedQuad quad : quads)
        {
            int color = -1;
            float red = (float) ((int)color >> 16 & 255) / 255.0F;
            float green = (float) ((int)color >> 8 & 255) / 255.0F;
            float blue = (float) ((int)color & 255) / 255.0F;
            buffer.putBulkData(entry, quad, red, green, blue,255,light, overlay);
        }
    }

    public static int getItemStackColor(ItemStack stack, ItemStack parent, int tintIndex)
    {
        int color = Minecraft.getInstance().getItemColors().getColor(stack, tintIndex);
        if(color == -1)
        {
            if(!parent.isEmpty())
            {
                return getItemStackColor(parent, ItemStack.EMPTY, tintIndex);
            }
        }
        return color;
    }

    public static void renderSphere(PoseStack poseStack, MultiBufferSource bufferSource,RenderType renderT, int packedLight,float radius, int sectors,Color color) {
        poseStack.pushPose();
        Matrix4f last = poseStack.last().pose();
        VertexConsumer consume = bufferSource.getBuffer(renderT);
        float startU = 0;
        float startV = 0;
        float endU = Mth.PI * 2;
        float endV = Mth.PI;
        float stepU = (endU - startU) / sectors;
        float stepV = (endV - startV) / sectors;
        poseStack.translate(0.5f, 0.5f, 0.5f);
        for (int i = 0; i <=sectors; ++i) {
            for (int j = 0; j <= sectors; ++j) {
                // V-points
                float u1 = i * stepU + startU;
                float v1 = j * stepV + startV;
                float u2 = (i + 1 == sectors) ? endU : (i + 1) * stepU + startU;
                float v2 = (j + 1 == sectors) ? endV : (j + 1) * stepV + startV;
                Vector3f p0 = parametricSphere(u1, v1, radius);
                Vector3f p1 = parametricSphere(u1, v2, radius);
                Vector3f p2 = parametricSphere(u2, v1, radius);
                Vector3f p3 = parametricSphere(u2, v2, radius);

                float textureU1 = u1 / endU * radius;
                float textureV1 = v1 / endV * radius;
                float textureU2 = u2 / endU * radius;
                float textureV2 = v2 / endV * radius;
                vertexPosColorUVLight(consume, last, p0.x(), p0.y(), p0.z(), 0, 0, 0, 1, textureU1, textureV1, packedLight);
                vertexPosColorUVLight(consume, last, p2.x(), p2.y(), p2.z(), 0, 0, 0, 1,  textureU2, textureV1, packedLight);
                vertexPosColorUVLight(consume, last, p1.x(), p1.y(), p1.z(), 0, 0, 0, 1,  textureU1, textureV2, packedLight);

                vertexPosColorUVLight(consume, last, p3.x(), p3.y(), p3.z(), 0, 0, 0, 1,  textureU2, textureV2, packedLight);
                vertexPosColorUVLight(consume, last, p1.x(), p1.y(), p1.z(), 0, 0, 0, 1,  textureU1, textureV2, packedLight);
                vertexPosColorUVLight(consume, last, p2.x(), p2.y(), p2.z(), 0, 0, 0, 1,  textureU2, textureV1, packedLight);
            }
        }
        poseStack.popPose();
    }
    public static Vector3f parametricSphere(float theta, float phi, float radius) {
        return new Vector3f(Mth.cos(theta) * Mth.sin(phi) * radius, Mth.cos(phi) * radius, Mth.sin(theta) * Mth.sin(phi) * radius);
    }
    public static void vertexPosColorUVLight(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z, float r, float g, float b, float a, float u, float v, int light) {
        vertexConsumer.addVertex(last, x, y, z)
                .setColor(r,g,b,a)
                .setUv(u, v)
                .setLight(light);
    }

    public static void applyTransformType(PoseStack poseStack, ItemDisplayContext display, @Nullable LivingEntity entity,BakedModel model)
    {

        boolean leftHanded = display == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || display == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;

        //TODO test
        model.applyTransform(display, poseStack, leftHanded);
        /* Flips the model and normals if left handed. */
        if(leftHanded)
        {
            Matrix4f scale = new Matrix4f().scale(-1, 1, 1);
            Matrix3f normal = new Matrix3f(scale);
            poseStack.last().pose().mul(scale);
            poseStack.last().normal().mul(normal);
        }
    }
    public static void renderFirstPersonArms(LocalPlayer player,ItemDisplayContext context ,HumanoidArm hand, ItemStack heldItem, PoseStack poseStack, MultiBufferSource buffer, int light, float partialTick,boolean isBullpup) {
        {
            if(!(heldItem.getItem() instanceof GunBaseItem)) { return;}
            if(!context.firstPerson()){return;}
            if(heldItem.getOrDefault(LenDataComponents.PART_COLOR_LIST,null) == null) {return;}
            boolean isShortHanded = false;
            for(ModelColorPair part : heldItem.get(LenDataComponents.PART_COLOR_LIST)) {
                if (part.model().equals(AllowedParts.RECEIVER_PISTOL)) {
                    isShortHanded = true;
                    break;
                }
            }
            poseStack.mulPose(Axis.YP.rotationDegrees(180F));
            int side = hand.getOpposite() == HumanoidArm.RIGHT ? 1 : -1;
            poseStack.translate(0.075f*side, 0.125f,  -0.1f);
            boolean slim = player.getSkin().model() == PlayerSkin.Model.SLIM;
            float armWidth = slim ? 3.0F : 4.0F;
            poseStack.pushPose();
            if(isShortHanded)
            {
                poseStack.translate(0.1f*side, 0.45, -0.7);
                poseStack.scale(0.75F, 0.75F, 0.75F);
                poseStack.translate(-4.0 * 0.0625 * side, 0, 0);
                poseStack.translate(-(armWidth / 2.0) * 0.0625 * side, -1.6, 0);
                poseStack.mulPose(Axis.XP.rotationDegrees(80F));
                RenderUtil.renderFirstPersonArm(player, hand, poseStack, buffer, light);
            } else {
                // Front arm holding the barrel
                poseStack.translate(-0.0125f* side, 0.18, 0.25);
                poseStack.scale(0.75F, 0.75F, 0.75F);
                poseStack.translate(4.0 * 0.0625 * side, 0, 0);
                poseStack.translate((armWidth / 2.0) * 0.0625 * side, 0, 0);
                poseStack.translate(-0.3125 * side, -1.58, -0.575);

                poseStack.mulPose(Axis.XP.rotationDegrees(80F));
                poseStack.mulPose(Axis.YP.rotationDegrees(12.5F * -side));
                poseStack.mulPose(Axis.ZP.rotationDegrees(20F * -side));
                poseStack.mulPose(Axis.XP.rotationDegrees(-35F));

                RenderUtil.renderFirstPersonArm(player, hand.getOpposite(), poseStack, buffer, light);
                poseStack.popPose();
                // Back arm holding the handle,slightly rotated
                poseStack.pushPose();
                if (isBullpup){
                    poseStack.translate(0,0,.2f);
                }
                poseStack.translate(-0.095f*side, 0.425, -0.75);
                poseStack.scale(0.75F, 0.75F, 0.75F);
                poseStack.translate(-4.0 * 0.0625 * side, 0, 0);
                poseStack.translate(-(armWidth / 2.0) * 0.0625 * side, -1.6, 0);

                poseStack.mulPose(Axis.XP.rotationDegrees(72F));
                RenderUtil.renderFirstPersonArm(player, hand, poseStack, buffer, light);
            }
            poseStack.popPose();
        }
    }

    public static boolean isMouseWithin(int mouseX, int mouseY, int x, int y, int width, int height)
    {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public static void renderFirstPersonArm(LocalPlayer player, HumanoidArm hand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight)
    {
        Minecraft mc = Minecraft.getInstance();
        EntityRenderDispatcher renderManager = mc.getEntityRenderDispatcher();
        PlayerRenderer renderer = (PlayerRenderer) renderManager.getRenderer(player);
        RenderSystem.setShaderTexture(0, player.getSkin().texture());
        poseStack.pushPose();
        if(hand == HumanoidArm.RIGHT)
        {
            renderer.renderRightHand(poseStack, buffer, combinedLight, player);
        }
        else
        {
            renderer.renderLeftHand(poseStack, buffer, combinedLight, player);
        }
        poseStack.popPose();
    }

    public static RenderType getRenderType(ItemStack stack, boolean entity)
    {
        Item item = stack.getItem();
        if(item instanceof BlockItem)
        {
            Block block = ((BlockItem) item).getBlock();
            return ItemBlockRenderTypes.getRenderType(block.defaultBlockState(), !entity);
        }
        return RenderType.entityTranslucent(InventoryMenu.BLOCK_ATLAS);
    }
}