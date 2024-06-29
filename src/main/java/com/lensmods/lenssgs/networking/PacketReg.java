package com.lensmods.lenssgs.networking;

import com.lensmods.lenssgs.networking.messages.CTSFire;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PacketReg {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                CTSFire.CTS_FIRE_TYPE,
                CTSFire.CTS_FIRE_STREAM_CODEC,
                ServerPacketHandler::HandleCTSFire);
    }
}
