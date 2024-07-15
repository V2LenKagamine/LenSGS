package com.lensmods.lenssgs.core.datacomps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public record StatMod(String stat, Float val,String modType, List<String> allowedParts) implements Comparable<StatMod>{

    public StatMod(String stat, Float val, String modType, String... allowedParts) {
        this(stat,val,modType, Arrays.stream(allowedParts).toList());
    }

    public static final Codec<StatMod> STAT_MOD_CODEC = RecordCodecBuilder.create(inst ->
        inst.group(Codec.STRING.fieldOf("stat").forGetter(StatMod::stat),
                Codec.FLOAT.fieldOf("val").forGetter(StatMod::val),
                Codec.STRING.fieldOf("modType").forGetter(StatMod::modType),
                Codec.STRING.listOf().fieldOf("allowedParts").forGetter(StatMod::allowedParts)
        ).apply(inst,StatMod::new));

    public static final StreamCodec<ByteBuf,StatMod> STAT_MOD_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,StatMod::stat,
            ByteBufCodecs.FLOAT,StatMod::val,
            ByteBufCodecs.STRING_UTF8,StatMod::modType,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),StatMod::allowedParts,
            StatMod::new
    );

    @Override
    public int compareTo(@NotNull StatMod o) {
        return stat.compareTo(o.stat);
    }
}

