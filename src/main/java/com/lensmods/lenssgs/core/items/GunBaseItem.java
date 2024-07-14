package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.api.IModdable;
import com.lensmods.lenssgs.client.render.CustomGunRenderer;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.datacomps.GunPartHolder;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.core.weaponsystems.WeaponAmmoStats;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class GunBaseItem extends Item implements IModdable,IClientItemExtensions {

    public GunBaseItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
    @Override
    public Component getName(ItemStack pStack) {
        var maybe = pStack.getOrDefault(LenDataComponents.GUN_COMP,null);
        if (maybe != null) {
            Component barrel = Component.empty();
            Component receiver = Component.empty();
            Component action = Component.empty();
            for(GunPartHolder part : maybe.getPartList()) {
                switch (part.getName()) {
                    case AllowedParts.BARREL -> barrel = part.getGunPartName();
                    case AllowedParts.ACTION -> action = part.getGunPartName();
                    case AllowedParts.RECEIVER -> receiver = part.getTotalName();
                }
            }
            return barrel.copy().append(LenUtil.spaceAppend(action)).append(LenUtil.spaceAppend(receiver));
        }
        return Component.literal("LOUD INCORRECT BUZZER AKA ERROR");
    }
/*
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        LenUtil.showGunData(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
*/
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return CustomGunRenderer.get();
            }
        });
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if(pRemainingUseDuration <= 1) {
            WeaponAmmoStats.attemptReload(pLivingEntity,pStack,pLevel);
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
        ItemStack main = pEntity.getMainHandItem();
        if(main.getOrDefault(LenDataComponents.AMMO_COUNTER,null) != null && main.getOrDefault(LenDataComponents.GUN_COMP,null) != null
        && main.getOrDefault(LenDataComponents.GUN_STAT_TRAITS,null)!=null) {
            boolean mag = false;
            for (GunPartHolder part : main.get(LenDataComponents.GUN_COMP).getPartList()) {
                if (part.getName().contains(AllowedParts.MAGAZINE)) {
                    mag =true;
                    break;
                }
            }
            if(mag) {
                return main.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max() * 2;
            }else return main.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max() * 12;
        }
        return super.getUseDuration(pStack, pEntity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack main = pPlayer.getItemInHand(pUsedHand);
        if(main.getOrDefault(LenDataComponents.AMMO_COUNTER,null) != null && main.getOrDefault(LenDataComponents.GUN_STAT_TRAITS,null) != null) {
            if(main.get(LenDataComponents.AMMO_COUNTER) != main.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max()) {
                pPlayer.startUsingItem(pUsedHand);
            }
        }
        return InteractionResultHolder.pass(main);
    }
}
