package com.lensmods.lenssgs.client.render;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.datacomps.GunComp;
import com.lensmods.lenssgs.core.datacomps.ModelColorPair;
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
import net.minecraft.client.renderer.block.model.ItemTransforms;
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
import java.util.List;
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
    private int sprintCD;
    private int sprintInt;

    private float offHandTrans;
    private float lastOffHandTrans;

    private float fallSway;
    private float lastFallSway;

    @Override
    public void renderByItem(ItemStack heldItem, ItemDisplayContext pDisplayContext, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int overlay) {
        if (heldItem.getItem() instanceof GunBaseItem) {
            float partialTicks = Minecraft.getInstance().gameRenderer.getMainCamera().getPartialTickTime();
            InteractionHand handy = Minecraft.getInstance().player.getUsedItemHand();
            HumanoidArm army = handy == InteractionHand.MAIN_HAND ? HumanoidArm.RIGHT : HumanoidArm.LEFT;

            if(handy == InteractionHand.OFF_HAND)
            {
                float offhand = 1.0F - Mth.lerp(1, this.lastOffHandTrans, this.offHandTrans);
                poseStack.translate(0, offhand * -0.6F, 0);
            }

            LocalPlayer player = Objects.requireNonNull(Minecraft.getInstance().player);
            float translateX = ItemTransforms.NO_TRANSFORMS.firstPersonRightHand.translation.x();
            float translateY = ItemTransforms.NO_TRANSFORMS.firstPersonRightHand.translation.y();
            float translateZ = ItemTransforms.NO_TRANSFORMS.firstPersonRightHand.translation.z();



            GunComp gunComp = heldItem.getOrDefault(LenDataComponents.GUN_COMP,new GunComp(List.of()));

            /* Applies custom bobbing animations */
            this.applyBobbingTransforms(poseStack, partialTicks);

            // Values are based on fuck around and find out.
            //X is R-L, Y is U-D, Z is F-B Rel to Origin
            int offset = handy == InteractionHand.MAIN_HAND ? 1 : -1;
            poseStack.translate(0.52 * offset, 01.1, 0.2);

            /* Applies basic stuff transforms */
            this.applySwayTransforms(poseStack, player, translateX, translateY, translateZ, partialTicks);
            this.applySprintingTransforms(army, poseStack, partialTicks);
            this.applyShieldTransforms(poseStack, player, partialTicks);


            int blockLight = player.isOnFire() ? 15 : player.level().getBrightness(LightLayer.BLOCK, BlockPos.containing(player.getEyePosition(partialTicks)));
            blockLight = Math.min(blockLight, 15);
            int packedLight = LightTexture.pack(blockLight, player.level().getBrightness(LightLayer.SKY, BlockPos.containing(player.getEyePosition(partialTicks))));

            /* Renders the first persons arms from the grip type of the weapon */
            poseStack.pushPose();
            ItemDisplayContext display = handy == InteractionHand.MAIN_HAND ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
            this.renderWeapon(Minecraft.getInstance().player, heldItem, display, poseStack, pBuffer, packedLight, partialTicks);
            RenderUtil.renderFirstPersonArms(Minecraft.getInstance().player, army, heldItem, poseStack, pBuffer, packedLight, partialTicks);
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

            double zOffset = 0;//Todo
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


    public void renderWeapon(@Nullable LivingEntity entity, ItemStack stack, ItemDisplayContext display, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light, float partialTicks)
    {
        if(stack.getItem() instanceof GunBaseItem)
        {
            poseStack.pushPose();

            RenderUtil.applyTransformType(stack, poseStack, display, entity);

            this.renderGun(entity, display, stack, poseStack, renderTypeBuffer, light, partialTicks);
            poseStack.popPose();
        }
    }

    private void renderGun(@Nullable LivingEntity entity, ItemDisplayContext display, ItemStack stack, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light, float partialTicks)
    {
        if(stack.getOrDefault(LenDataComponents.PART_COLOR_LIST,null) != null) {
            for (ModelColorPair pair : stack.get(LenDataComponents.PART_COLOR_LIST)) {
                poseStack.pushPose();
                BakedModel bakedModel = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID, "gunparts/"+pair.model())));
                if (bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()) {
                    RenderUtil.forceItemWithColor(bakedModel,ItemDisplayContext.NONE,null,stack,stack,poseStack,renderTypeBuffer,light,OverlayTexture.NO_OVERLAY,pair.color());
                }
                poseStack.popPose();
            }
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
