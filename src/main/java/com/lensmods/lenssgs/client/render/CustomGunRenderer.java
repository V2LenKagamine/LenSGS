package com.lensmods.lenssgs.client.render;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.client.ADSHandler;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.datacomps.ModelColorPair;
import com.lensmods.lenssgs.core.items.AmmoBaseItem;
import com.lensmods.lenssgs.core.items.GunBaseItem;
import com.lensmods.lenssgs.core.util.RenderUtil;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LightLayer;

import javax.annotation.Nullable;
import java.util.Objects;

public class CustomGunRenderer extends BlockEntityWithoutLevelRenderer {
    private static CustomGunRenderer instance;

    public CustomGunRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    public static CustomGunRenderer get() {
        if (instance == null) {
            instance = new CustomGunRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
        }
        return instance;
    }
    private int sprintTrans;
    private int lastSprintTrans;

    public float recoilTimer = 0;

    private float offHandTrans;
    private float lastOffHandTrans;

    private float fallSway;
    private float lastFallSway;

    @Override
    public void renderByItem(ItemStack heldItem, ItemDisplayContext pDisplayContext, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int overlay) {
        if (heldItem.getItem() instanceof GunBaseItem) {
            float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
            InteractionHand handy = Minecraft.getInstance().player.getMainHandItem() == heldItem ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            HumanoidArm army = handy == InteractionHand.MAIN_HAND ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
            poseStack.pushPose();
            if(handy == InteractionHand.OFF_HAND&& pDisplayContext!=ItemDisplayContext.GUI)
            {
                float offhand = 1.0F - Mth.lerp(1, this.lastOffHandTrans, this.offHandTrans);
                poseStack.translate(0, offhand * -0.6F, 0);
            }

            LocalPlayer player = Objects.requireNonNull(Minecraft.getInstance().player);


            /* Applies custom bobbing animations */

            // Values are based on fuck around and find out.
            //X is L-R, Y is U-D, Z is F-B Rel to Origin
            float side = handy == InteractionHand.MAIN_HAND ? 1:-1;
            poseStack.translate(0.52, 1.55, (0.052f * -side) - 0.052f);
            /* Applies basic stuff transforms */
            int blockLight = player.isOnFire() ? 15 : player.level().getBrightness(LightLayer.BLOCK, BlockPos.containing(player.getEyePosition(partialTicks)));
            blockLight = Math.min(blockLight, 15);
            int packedLight = LightTexture.pack(pDisplayContext!= ItemDisplayContext.GUI? blockLight : 15,player.level().getBrightness(LightLayer.SKY, BlockPos.containing(player.getEyePosition(partialTicks))));
            if(pDisplayContext.firstPerson() && pDisplayContext != ItemDisplayContext.GUI && ADSHandler.get().adsProgress()<=0f) {
                this.applyBobbingTransforms(poseStack, partialTicks);
                this.applySwayTransforms(poseStack, player, 0, 0, 0, partialTicks);
                this.applySprintingTransforms(army, poseStack, partialTicks);
                this.applyShieldTransforms(poseStack, player, partialTicks);
            }
            if(!pDisplayContext.firstPerson() && pDisplayContext != ItemDisplayContext.GUI) {
                poseStack.translate(-0.12 * side ,-0.12f, 0.2f);
            }
            if(pDisplayContext == ItemDisplayContext.GUI) {
                poseStack.translate(0,-0.6f,0);
                poseStack.scale(0.75f,0.75f,0.75f);
                poseStack.mulPose(Axis.YP.rotationDegrees(30f));
            }
            this.renderWeapon(Minecraft.getInstance().player, heldItem,army, pDisplayContext, poseStack, pBuffer, pDisplayContext!= ItemDisplayContext.GUI?packedLight:LightTexture.FULL_BRIGHT, partialTicks);
            poseStack.popPose();
        }
        if (heldItem.getItem() instanceof AmmoBaseItem) {
            float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
            InteractionHand handy = Minecraft.getInstance().player.getMainHandItem() == heldItem ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            HumanoidArm army = handy == InteractionHand.MAIN_HAND ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
            poseStack.pushPose();
            if(handy == InteractionHand.OFF_HAND && pDisplayContext!=ItemDisplayContext.GUI)
            {
                float offhand = 1.0F - Mth.lerp(1, this.lastOffHandTrans, this.offHandTrans);
                poseStack.translate(0, offhand * -0.6F, 0);
            }
            LocalPlayer player = Objects.requireNonNull(Minecraft.getInstance().player);
            /* Applies custom bobbing animations */
            // Values are based on fuck around and find out.
            //X is L-R, Y is U-D, Z is F-B Rel to Origin
            float side = handy == InteractionHand.MAIN_HAND ? 1:-1;
            poseStack.translate(0.52 ,1.55, (0.052f*-side) - 0.052f);
            /* Applies basic stuff transforms */
            if(pDisplayContext.firstPerson() && pDisplayContext!= ItemDisplayContext.GUI) {
                this.applyBobbingTransforms(poseStack, partialTicks);
                this.applySwayTransforms(poseStack, player, 0, 0, 0, partialTicks);
                this.applySprintingTransforms(army, poseStack, partialTicks);
                this.applyShieldTransforms(poseStack, player, partialTicks);
            }
            if(!pDisplayContext.firstPerson()&& pDisplayContext != ItemDisplayContext.GUI) {
                poseStack.translate(side * -0.1f ,-0.1, (0.012f*-side) - 0.012f);
            }
            if(pDisplayContext == ItemDisplayContext.GUI) {
                poseStack.translate(-0.55f,0.15f,-0.3);
                poseStack.mulPose(Axis.YP.rotationDegrees(45f));
                poseStack.scale(2f,2f,2f);
            }
            this.renderAmmo(Minecraft.getInstance().player,pDisplayContext,army, heldItem, poseStack, pBuffer, LightTexture.FULL_BRIGHT, partialTicks);
            poseStack.popPose();
        }
    }
    private void applyShieldTransforms(PoseStack poseStack, LocalPlayer player, float partialTick)
    {
        if(player.isUsingItem() && player.getOffhandItem().getItem() == Items.SHIELD)
        {
            double time = Mth.clamp((player.getTicksUsingItem() + partialTick), 0.0, 4.0) / 4.0;
            poseStack.translate(0, 0.35 * time, 0);
            poseStack.mulPose(Axis.XP.rotationDegrees(45F * (float) time));
        }
    }

    private void applyBobbingTransforms(PoseStack poseStack, float partialTicks)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.options.bobView().get() && mc.getCameraEntity() instanceof Player player)
        {
            float deltaDistanceWalked = player.walkDist - player.walkDistO;
            float distanceWalked = -(player.walkDist + deltaDistanceWalked * partialTicks);
            float bobbing = Mth.lerp(partialTicks, player.oBob, player.bob);

            /* Reverses the original bobbing rotations and translations so it can be controlled */
            poseStack.mulPose(Axis.XP.rotationDegrees(-(Math.abs(Mth.cos(distanceWalked * (float) Math.PI - 0.2F) * bobbing) * 5.0F)));
            poseStack.mulPose(Axis.ZP.rotationDegrees(-(Mth.sin(distanceWalked * (float) Math.PI) * bobbing * 3.0F)));
            poseStack.translate(-(Mth.sin(distanceWalked * (float) Math.PI) * bobbing * 0.5F), -(-Math.abs(Mth.cos(distanceWalked * (float) Math.PI) * bobbing)), 0.0D);

            /* Slows down the bob by half */
            bobbing *= player.isSprinting() ? 8f : 4f;
            /* The new controlled bobbing */
            poseStack.mulPose(Axis.ZP.rotationDegrees((Mth.sin(distanceWalked * (float) Math.PI) * bobbing * 3.0F)));
            poseStack.mulPose(Axis.XP.rotationDegrees((Math.abs(Mth.cos(distanceWalked * (float) Math.PI - 0.2F) * bobbing) * 5.0F)));
        }
    }
    private void applySwayTransforms(PoseStack poseStack, LocalPlayer player, float x, float y, float z, float partialTicks)
    {
        if(player != null)
        {
            poseStack.translate(x, y, z);

            double zOffset = 0;//Todo?
            poseStack.translate(0, -0.25, zOffset);
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTicks, this.lastFallSway, this.fallSway)));
            poseStack.translate(0, 0.25, -zOffset);

            float bobPitch = Mth.rotLerp(partialTicks, player.xBobO, player.xBob);
            float headPitch = Mth.rotLerp(partialTicks, player.xRotO, player.getXRot());
            float swayPitch = headPitch - bobPitch;
            poseStack.mulPose(Axis.YP.rotationDegrees(swayPitch * 0.3f));

            float bobYaw = Mth.rotLerp(partialTicks, player.yBobO, player.yBob);
            float headYaw = Mth.rotLerp(partialTicks, player.yHeadRotO, player.yHeadRot);
            float swayYaw = headYaw - bobYaw;
            poseStack.mulPose(Axis.XP.rotationDegrees(swayYaw * 0.3f));

            poseStack.translate(-x, -y, -z);
        }
    }

    private void renderAmmo(@Nullable LivingEntity entity, ItemDisplayContext display,HumanoidArm handy,ItemStack stack, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light, float partialTicks)
    {
        poseStack.pushPose();
        if(stack.getOrDefault(LenDataComponents.PART_COLOR_LIST,null) != null) {

            float side = entity.getMainHandItem() == stack ? 1 : -1;
            poseStack.scale(1.3f,1.3f,1.3f);
            if(display!=ItemDisplayContext.GUI) {
                poseStack.translate((0.025f * side) - 0.025f, -0.22f * side, (0.05f * side) - 0.05f);
                poseStack.mulPose(Axis.YP.rotationDegrees(7.5f * side));
            }
            poseStack.mulPose(Axis.XP.rotationDegrees(3.9f));
            for (ModelColorPair pair : stack.get(LenDataComponents.PART_COLOR_LIST)) {
                if(!pair.model().contains(AllowedParts.CASING)) {continue;}
                BakedModel bakedModel;
                bakedModel = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID, "ammo/box")));
                if (bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()) {
                    RenderUtil.applyTransformType(poseStack, display, entity,bakedModel);
                    RenderUtil.forceItemWithColorBlend(bakedModel,ItemDisplayContext.NONE,null,stack,poseStack,renderTypeBuffer,light, OverlayTexture.NO_OVERLAY,pair.color(),1f);
                }
            }
        }
        poseStack.popPose();
    }
    public void renderWeapon(@Nullable LivingEntity entity, ItemStack stack,HumanoidArm handy ,ItemDisplayContext display, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light, float partialTicks)
    {
        if(stack.getItem() instanceof GunBaseItem)
        {
            if(recoilTimer > 0f && display.firstPerson() && display != ItemDisplayContext.GUI) {
                poseStack.translate(0,0,recoilTimer * 0.025f);
                recoilTimer-=partialTicks;
            }
            float side = entity.getMainHandItem() == stack ? 1 : -1;
            poseStack.scale(1.3f,1.3f,1.3f);
            if (display != ItemDisplayContext.GUI) {
                poseStack.translate((0.025f * side) - 0.025f, -0.22f * side, (0.05f * side) - 0.05f);
                poseStack.mulPose(Axis.YP.rotationDegrees(7.5f * side));
            }
            poseStack.mulPose(Axis.XP.rotationDegrees(3.9f));
            if(display.firstPerson() && display != ItemDisplayContext.GUI) {
                if(ADSHandler.get().adsProgress() != 0) {
                    float prog = ADSHandler.get().adsProgress();
                    poseStack.translate(-0.5f * prog, 0.051f * prog,0.4f*prog);
                    poseStack.mulPose(Axis.YP.rotationDegrees(-7.75f*prog));
                    poseStack.mulPose(Axis.XP.rotationDegrees(-6f*prog));
                }
            }
            this.renderGun(entity, display,handy,stack, poseStack, renderTypeBuffer, light, partialTicks);
        }
    }

    private void renderGun(@Nullable LivingEntity entity, ItemDisplayContext display,HumanoidArm handy,ItemStack stack, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light, float partialTicks)
    {
        if(stack.getOrDefault(LenDataComponents.PART_COLOR_LIST,null) != null) {
            boolean bullpup=false;
            for (ModelColorPair pair : stack.get(LenDataComponents.PART_COLOR_LIST)) {
                if(pair.model().contains("bullpup")) {
                    bullpup=true;
                    break;
                }
            }
            for (ModelColorPair pair : stack.get(LenDataComponents.PART_COLOR_LIST)) {
                poseStack.pushPose();
                BakedModel bakedModel;
                if(bullpup) { //Bullpup, load bullpup models
                    bakedModel = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID, "gunparts/" + pair.model()+"_bullpup")));
                    if (bakedModel == Minecraft.getInstance().getModelManager().getMissingModel()) { //if we fail, load normal.
                        bakedModel = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID, "gunparts/" + pair.model())));
                    }
                }
                else {
                    bakedModel = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID, "gunparts/" + pair.model())));
                }
                if (bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()) {
                    RenderUtil.applyTransformType(poseStack, display, entity,bakedModel);
                    RenderUtil.forceItemWithColorBlend(bakedModel,ItemDisplayContext.NONE,null,stack,poseStack,renderTypeBuffer,light, OverlayTexture.NO_OVERLAY,pair.color(),0.25f);
                }
                poseStack.popPose();
            }
            RenderUtil.renderFirstPersonArms(Minecraft.getInstance().player,display ,handy, stack, poseStack, renderTypeBuffer, light, partialTicks,bullpup);
        }
    }

    private void applySprintingTransforms(HumanoidArm hand, PoseStack poseStack, float partialTicks) {

        float leftHanded = hand == HumanoidArm.LEFT ? -1 : 1;
        float transition = (this.lastSprintTrans + (this.sprintTrans - this.lastSprintTrans) * partialTicks) / 5F;
        transition = (float) Math.sin((transition * Math.PI) / 2);
        poseStack.translate(-0.25 * leftHanded * transition, -0.1 * transition, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(45F * leftHanded * transition));
        poseStack.mulPose(Axis.XP.rotationDegrees(-25F * transition));
    }
}
