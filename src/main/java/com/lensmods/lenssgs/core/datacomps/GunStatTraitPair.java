package com.lensmods.lenssgs.core.datacomps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GunStatTraitPair implements MutableDataComponentHolder {
    private final GunStats stats;
    private final List<TraitLevel> traits;
    private final PatchedDataComponentMap map;

    public static final Codec<GunStatTraitPair> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(GunStats.CODEC.fieldOf("stats").forGetter(GunStatTraitPair::getStats),
                    TraitLevel.TRAIT_LEVEL_CODEC.listOf().fieldOf("traits").forGetter(GunStatTraitPair::getTraits),
                    DataComponentPatch.CODEC.optionalFieldOf("map",DataComponentPatch.EMPTY).forGetter(hold -> hold.map.asPatch())).apply(inst,GunStatTraitPair::new));

    public static final StreamCodec<RegistryFriendlyByteBuf,GunStatTraitPair> SCODEC = StreamCodec.composite(
            GunStats.SCODEC,GunStatTraitPair::getStats,
            TraitLevel.TRAIT_LEVEL_STREAM_CODEC.apply(ByteBufCodecs.list()),GunStatTraitPair::getTraits,
            DataComponentPatch.STREAM_CODEC,holder -> holder.map.asPatch(),
            GunStatTraitPair::new
    );

    public GunStatTraitPair() {
        this.stats = new GunStats();
        this.traits = new ArrayList<>();
        this.map = new PatchedDataComponentMap(PatchedDataComponentMap.EMPTY);
    }

    public GunStatTraitPair(GunStats stats, List<TraitLevel> traits) {
        this.stats = stats;
        this.traits = traits;
        this.map = new PatchedDataComponentMap(PatchedDataComponentMap.EMPTY);
    }
    public GunStatTraitPair(GunStats stats, List<TraitLevel> traits,DataComponentPatch patch) {
        this.stats = stats;
        this.traits = traits;
        this.map = PatchedDataComponentMap.fromPatch(
                PatchedDataComponentMap.EMPTY,
                patch
        );
    }

    public List<TraitLevel> getTraits() {
        return this.traits;
    }
    public GunStats getStats() {
        return this.stats;
    }
    public PatchedDataComponentMap getMap() {
        return map;
    }
    @Override
    public <T> @Nullable T set(DataComponentType<? super T> dataComponentType, @Nullable T t) {
        return map.set(dataComponentType,t);
    }

    @Override
    public <T> @Nullable T remove(DataComponentType<? extends T> dataComponentType) {
        return map.remove(dataComponentType);
    }

    @Override
    public void applyComponents(DataComponentPatch dataComponentPatch) {
        this.map.applyPatch(dataComponentPatch);
    }

    @Override
    public void applyComponents(DataComponentMap dataComponentMap) {
        this.map.setAll(dataComponentMap);
    }

    @Override
    public DataComponentMap getComponents() {
        return map;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.stats,this.traits);
    }
    @Override public boolean equals(Object obj) {
        if(obj == this) {return true;}
        else {
            return obj instanceof GunStatTraitPair egg &&
                    egg.stats == this.stats &&
                    egg.traits == this.traits;
        }
    }

}
