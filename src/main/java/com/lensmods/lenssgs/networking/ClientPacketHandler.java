package com.lensmods.lenssgs.networking;

import com.lensmods.lenssgs.core.items.AmmoBaseItem;
import com.lensmods.lenssgs.core.items.GunBaseItem;
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
}
