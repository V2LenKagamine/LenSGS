package com.lensmods.lenssgs.client;

import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.datacomps.GunPartHolder;
import com.lensmods.lenssgs.core.items.GunBaseItem;
import com.lensmods.lenssgs.core.weaponsystems.WeaponAmmoStats;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class ADSHandler {

    private static ADSHandler instance;

    public static ADSHandler get() {
        if(instance==null){
            instance = new ADSHandler();
        }
        return instance;
    }
    private double aimCurr;
    String scope = "none";
    public enum MAXADS{
        scope_long(1f,0.65f),
        scope_medium(0.76f,0.5f),
        scope_short(0.61f,0.4f),
        scope_irons(0.46f,0.3f),
        none(0,0);

        public final float val;
        public final float adjst;

        MAXADS(float type,float val) {
            this.val = val;
            this.adjst = type;
        }
    }
    private boolean ADS;
    public float adsProgress(String scope) {return (float) (aimCurr/MAXADS.valueOf(scope).val);}

    private ADSHandler() {}

    //Hacky way of not rendering the crosshair while ADSing
    @SubscribeEvent(receiveCanceled = true)
    public void onRenderOverlay(RenderGuiLayerEvent.Pre event)
    {
        if(event.getName().equals(ResourceLocation.withDefaultNamespace("crosshair")) && aimCurr >= 0.3f) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public void playerTick(PlayerTickEvent.Pre e) {
        Player plyr = e.getEntity();
        if(!plyr.isLocalPlayer()) {return;}
        if(ADS && plyr.getMainHandItem().getItem() instanceof GunBaseItem) {
            if(WeaponAmmoStats.safeGunComp(plyr.getMainHandItem()) && scope.equals("none")) {
                for(GunPartHolder part : plyr.getMainHandItem().get(LenDataComponents.GUN_COMP).getPartList()) {
                    if(part.getName().equals(AllowedParts.SCOPE)) {
                        scope = part.getSubType();
                        break;
                    }
                }
            }
            if (aimCurr < MAXADS.valueOf(scope).val) {
                aimCurr += 0.05f;
                if (aimCurr > MAXADS.valueOf(scope).val) {
                    aimCurr = MAXADS.valueOf(scope).val;
                    scope = "none";
                }
            }
        }else{
            if(aimCurr>0){
                aimCurr-=0.05f;
                scope = "none";
                if(aimCurr<0){
                    aimCurr =0;
                }
            }
        }
    }

    @SubscribeEvent
    public void cTick(ClientTickEvent.Pre e) {
        Player plyr =  Minecraft.getInstance().player;
        if(plyr == null) {return;}
        if(tryingToAim()) {
            if(!ADS) {
                ADS = true;
            }
        }
        else if(ADS) {ADS = false;}
    }

    public boolean tryingToAim()
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null)
            return false;

        if(mc.player.isSpectator())
            return false;

        if(mc.screen != null)
            return false;

        ItemStack heldItem = mc.player.getMainHandItem();
        if(!(heldItem.getItem() instanceof GunBaseItem))
            return false;

        if(!WeaponAmmoStats.safeGunComp(heldItem)) {return false;}

        if(heldItem.get(LenDataComponents.GUN_COMP).getPartList().stream().noneMatch(part -> part.getName().contains(AllowedParts.SCOPE)))
            return false;

        if(mc.player.getOffhandItem().getItem() == Items.SHIELD && heldItem.get(LenDataComponents.GUN_COMP).getPartList().stream().noneMatch(part -> part.getSubType().equals(AllowedParts.RECEIVER_PISTOL)))
            return false;

        return mc.options.keyUse.isDown();
    }


    @SubscribeEvent
    public void fovUpdate(ViewportEvent.ComputeFov e) {
        if(!e.usedConfiguredFov()){return;}
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || mc.player.getMainHandItem().isEmpty() || mc.options.getCameraType() != CameraType.FIRST_PERSON)
            return;

        ItemStack heldItem = mc.player.getMainHandItem();
        if(!(heldItem.getItem() instanceof GunBaseItem gun))
            return;

        if(this.aimCurr == 0)
            return;
        if(!WeaponAmmoStats.safeGunComp(heldItem)) {return;}

        if(heldItem.get(LenDataComponents.GUN_COMP).getPartList().stream().noneMatch(part -> part.getName().contains(AllowedParts.SCOPE))){return;}

        e.setFOV(e.getFOV() - e.getFOV() * this.aimCurr);
    }
}
