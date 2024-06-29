package com.lensmods.lenssgs.client;

import com.lensmods.lenssgs.core.items.GunBaseItem;
import com.lensmods.lenssgs.core.weaponsystems.GunLogic;
import com.lensmods.lenssgs.networking.messages.CTSFire;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class ClientFireHandler {
    private static ClientFireHandler instance;

    public static ClientFireHandler get() {
        if (instance == null) {
            instance = new ClientFireHandler();
        }
        return instance;
    }

    private boolean firing;

    private boolean tabbedin() {
        Minecraft MC = Minecraft.getInstance();
        if(MC.getOverlay() != null) {
            return false;
        }
        if (MC.screen !=null) {
            return false;
        }
        if (!MC.mouseHandler.isMouseGrabbed()) {
            return false;
        }
        return MC.isWindowActive();
    }
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onMouseClick(InputEvent.InteractionKeyMappingTriggered event) {
        if(event.isCanceled())
            return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if(player == null)
            return;
        if(event.isAttack())
        {
            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem() instanceof GunBaseItem gun)
            {
                event.setSwingHand(false);
                event.setCanceled(true);
                this.shoot(player, heldItem);
            }
        }
    }
    public void shoot(Player player, ItemStack heldItem)
    {
        if(!(heldItem.getItem() instanceof GunBaseItem))
            return;

        if(!GunLogic.doesHaveAmmo(heldItem) && !player.isCreative())
            return;

        if(player.isSpectator())
            return;

        if(player.getUseItem().getItem() == Items.SHIELD)
            return;

        ItemCooldowns tracker = player.getCooldowns();
        if(!tracker.isOnCooldown(heldItem.getItem()))
        {
            GunBaseItem gunItem = (GunBaseItem) heldItem.getItem();
            PacketDistributor.sendToServer(new CTSFire(player.getYRot(),player.getXRot()));
        }
    }

}
