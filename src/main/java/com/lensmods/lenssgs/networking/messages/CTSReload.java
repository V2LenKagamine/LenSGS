package com.lensmods.lenssgs.networking.messages;

import com.lensmods.lenssgs.LensSGS;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CTSReload(boolean useless) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CTSReload> CTS_RELOAD_TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"c2sreload"));

    public static final StreamCodec<ByteBuf,CTSReload> CTS_FIRE_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            CTSReload::useless,
            CTSReload::new
    );
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return CTS_RELOAD_TYPE;
    }
}
