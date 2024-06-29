package com.lensmods.lenssgs.networking.messages;

import com.lensmods.lenssgs.LensSGS;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CTSFire(float xRot, float yRot) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<CTSFire> CTS_FIRE_TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"c2sfire"));

    public static final StreamCodec<ByteBuf,CTSFire> CTS_FIRE_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            CTSFire::xRot,
            ByteBufCodecs.FLOAT,
            CTSFire::yRot,
            CTSFire::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return CTS_FIRE_TYPE;
    }
}
