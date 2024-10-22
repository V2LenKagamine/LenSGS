package com.lensmods.lenssgs.core.datacomps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public record TraitLevel(String trait, int level, List<String> allowedParts) implements Comparable<TraitLevel>{
    public static final Codec<TraitLevel> TRAIT_LEVEL_CODEC = RecordCodecBuilder.create(inst ->
            inst.group(Codec.STRING.fieldOf("trait").forGetter(TraitLevel::trait),
                    Codec.INT.fieldOf("level").forGetter(TraitLevel::level),
                    Codec.STRING.listOf().fieldOf("allowedParts").forGetter(TraitLevel::allowedParts)
            ).apply(inst,TraitLevel::new));

    public static final StreamCodec<ByteBuf,TraitLevel> TRAIT_LEVEL_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,TraitLevel::trait,
            ByteBufCodecs.INT,TraitLevel::level,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.collection(HashSet::new)),TraitLevel::allowedParts,
            (trait1, level1, allowedParts1) -> new TraitLevel(trait1, level1, allowedParts1.stream().toList())
    );

    public TraitLevel(String key, int level) {
        this(key,level,List.of("all"));
    }
    public TraitLevel(String key, int level,String... types) {
        this(key,level, Arrays.stream(types).toList());
    }

    @Override
    public int compareTo(@NotNull TraitLevel o) {
        return trait.compareTo(o.trait);
    }
}
