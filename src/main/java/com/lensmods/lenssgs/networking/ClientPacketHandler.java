package com.lensmods.lenssgs.networking;

import com.lensmods.lenssgs.core.items.AmmoBaseItem;
import com.lensmods.lenssgs.core.items.GunBaseItem;
import com.lensmods.lenssgs.core.weaponsystems.PICooldown;
import com.lensmods.lenssgs.networking.messages.STCFireSync;
import com.lensmods.lenssgs.networking.messages.STCUpdateGunComps;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPacketHandler {
    public static void handleSTCGunComps(STCUpdateGunComps stcUpdateGunComps, IPayloadContext context) {
        Item holding = context.player().getMainHandItem().getItem();
        if(holding instanceof GunBaseItem || holding instanceof AmmoBaseItem) {
            context.enqueueWork(() -> {
                context.player().getMainHandItem().applyComponents(stcUpdateGunComps.patch());
            });
        }
    }
    public static void HandleFireSync(STCFireSync mess,IPayloadContext context) {
        if (context.player().getMainHandItem().getItem() instanceof GunBaseItem) {
            context.enqueueWork(() -> {
                PICooldown.getCDTracker(context.player()).putCooldown(context.player().getMainHandItem(), mess.time());
            });
        }
    }
}
