package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.api.IModdable;
import com.lensmods.lenssgs.client.render.CustomGunRenderer;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.datacomps.GunPartHolder;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.core.weaponsystems.ReloadTracker;
import com.lensmods.lenssgs.core.weaponsystems.WeaponAmmoStats;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
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
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(!(entity instanceof Player player))
        {return;}
        ReloadTracker tracker = ReloadTracker.getReloadTracker(player);
        if(!isSelected && tracker.hasReloadTimer(stack)) {
            tracker.removeTimer(stack);
        } else if (isSelected && tracker.hasReloadTimer(stack) &&
                tracker.getRemaining(stack) <= 0) {
            tracker.removeTimer(stack);
            WeaponAmmoStats.attemptReload(player,stack,level);
        }
    }
    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return WeaponAmmoStats.getAmmoMax(pStack) * WeaponAmmoStats.AMMO_POINTS_MUL > WeaponAmmoStats.ammoAmountLeft(pStack);
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round((float)WeaponAmmoStats.ammoAmountLeft(pStack)/ (float) (WeaponAmmoStats.getAmmoMax(pStack)));
    }
}
