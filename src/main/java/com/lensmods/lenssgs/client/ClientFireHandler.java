package com.lensmods.lenssgs.client;

import com.lensmods.lenssgs.client.render.CustomGunRenderer;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.items.GunBaseItem;
import com.lensmods.lenssgs.core.weaponsystems.PICooldown;
import com.lensmods.lenssgs.core.weaponsystems.WeaponAmmoStats;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.lensmods.lenssgs.init.LenSounds;
import com.lensmods.lenssgs.networking.messages.CTSFire;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
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

    private int clickcooldown =0;

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
            if(heldItem.getItem() instanceof GunBaseItem)
            {
                event.setSwingHand(false);
                event.setCanceled(true);
                this.shoot(player, heldItem);
                if(WeaponAmmoStats.safeGunComp(heldItem)) {
                    if(heldItem.get(LenDataComponents.GUN_COMP).getPartList().stream().noneMatch(part -> part.getSubType().equals(AllowedParts.ACTION_AUTOMATIC))){
                        mc.options.keyAttack.setDown(false);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void postCTick(ClientTickEvent.Post e) {
        if(!tabbedin())
        {return;}
        if (clickcooldown >0) {
            clickcooldown--;
        }
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if(player!=null && mc.options.keyAttack.isDown()) {
            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem() instanceof GunBaseItem)
            {
                if(WeaponAmmoStats.safeGunComp(heldItem)) {
                    if(heldItem.get(LenDataComponents.GUN_COMP).getPartList().stream().anyMatch(part -> part.getSubType().equals(AllowedParts.ACTION_AUTOMATIC))){
                        this.shoot(player, heldItem);
                    }
                }
            }
        }
    }
    public void shoot(Player player, ItemStack heldItem)
    {
        if(!(heldItem.getItem() instanceof GunBaseItem))
        {return;}

        if(!WeaponAmmoStats.doesHaveAmmo(heldItem) && !player.isCreative()) {
            if(clickcooldown <= 0 ){
                clickcooldown = 15;
                player.level().playSound(player,player.getX(),player.getY(),player.getZ(), LenSounds.GUN_CLICK.get(), SoundSource.PLAYERS);
            }
            return;
        }
        if(player.isSpectator())
        {return;}

        if(player.getUseItem().getItem() == Items.SHIELD)
        {
            if(WeaponAmmoStats.safeGunComp(heldItem)) {
                if(heldItem.get(LenDataComponents.GUN_COMP).getPartList().stream().noneMatch(part -> part.getSubType().equals(AllowedParts.RECEIVER_PISTOL))){
                    return;
                }
            }
        }

        PICooldown tracker = PICooldown.getCDTracker(player);
        if(!tracker.hasCooldown(heldItem))
        {
            if(WeaponAmmoStats.safeGunComp(heldItem)) {
            CustomGunRenderer.get().recoilTimer = heldItem.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getFirerate();
            }
            PacketDistributor.sendToServer(new CTSFire(player.getXRot(),player.getYRot()));
        }
    }

}
