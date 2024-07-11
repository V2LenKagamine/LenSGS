package com.lensmods.lenssgs.core.datacomps;

import com.lensmods.lenssgs.core.util.Color;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ModelColorPair(String model, Color color) {
    public static final Codec<ModelColorPair> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.STRING.fieldOf("model").forGetter(ModelColorPair::model),
        Color.CODEC.fieldOf("color").forGetter(ModelColorPair::color)
    ).apply(inst,ModelColorPair::new));
    public static final StreamCodec<ByteBuf,ModelColorPair> SCODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,ModelColorPair::model,
            Color.STREAM_CODEC,ModelColorPair::color,
            ModelColorPair::new
    );
}
