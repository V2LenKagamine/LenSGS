package com.lensmods.lenssgs.networking.messages;

import com.lensmods.lenssgs.LensSGS;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record STCFireSync(int time) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<STCFireSync> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"stcfiresync"));

    public static final StreamCodec<ByteBuf,STCFireSync> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,STCFireSync::time,
            STCFireSync::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

