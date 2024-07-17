package com.lensmods.lenssgs.networking;

import com.lensmods.lenssgs.core.weaponsystems.GunFireLogic;
import com.lensmods.lenssgs.networking.messages.CTSFire;
import com.lensmods.lenssgs.networking.messages.CTSReload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPacketHandler {
    public static void HandleCTSFire(final CTSFire message, final IPayloadContext context) {
        context.enqueueWork(()-> {
            ServerPlayer player = (ServerPlayer) context.player();
            if(player!=null) {
                GunFireLogic.handleFireMsg(message,player);
            }
        });
    }
    public static void HandleReload(final CTSReload mess, final IPayloadContext context) {
        context.enqueueWork(()->{
            ServerPlayer player = (ServerPlayer) context.player();
            if(player!=null) {
                GunFireLogic.handleReloadMsg(mess,player);
            }
        });
    }
}
