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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class GunComp implements MutableDataComponentHolder{
    private List<GunPartHolder> parts;
    private final PatchedDataComponentMap comps;

    public static final Codec<GunComp> GUN_COMP_CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    GunPartHolder.GUN_PART_CODEC.listOf().fieldOf("parts").forGetter(GunComp::getPartList),
                    DataComponentPatch.CODEC.optionalFieldOf("comps",DataComponentPatch.EMPTY).forGetter(holder ->holder.comps.asPatch())
            ).apply(inst,GunComp::new));

    public static final StreamCodec<RegistryFriendlyByteBuf,GunComp> GUN_COMP_SCODEC = StreamCodec.composite(
            GunPartHolder.GUN_PART_SCODEC.apply(ByteBufCodecs.collection(HashSet::new)),GunComp::getPartList,
            DataComponentPatch.STREAM_CODEC,holder -> holder.comps.asPatch(),
            GunComp::new
    );

    public GunComp(Collection<GunPartHolder> gunPartHolders, DataComponentPatch patch) {
        this.parts = gunPartHolders.stream().toList();
        this.comps = PatchedDataComponentMap.fromPatch(
                DataComponentMap.EMPTY,
                patch
        );
    }

    public GunComp(List<GunPartHolder> parts) {
        this.parts = parts;
        this.comps = new PatchedDataComponentMap(DataComponentMap.EMPTY);
    }

    public List<GunPartHolder> getPartList() {
        return this.parts;
    }

    public GunComp(List<GunPartHolder> parts,DataComponentPatch patch) {
        this.parts = parts;
        this.comps = PatchedDataComponentMap.fromPatch(
                DataComponentMap.EMPTY,
                patch
        );
    }

    @Override
    public <T> @Nullable T set(DataComponentType<? super T> dataComponentType, @Nullable T t) {
        return this.comps.set(dataComponentType,t);
    }

    @Override
    public <T> @Nullable T remove(DataComponentType<? extends T> dataComponentType) {
        return this.comps.remove(dataComponentType);
    }

    @Override
    public void applyComponents(DataComponentPatch dataComponentPatch) {
        this.comps.applyPatch(dataComponentPatch);
    }

    @Override
    public void applyComponents(DataComponentMap dataComponentMap) {
        this.comps.setAll(dataComponentMap);
    }

    @Override
    public DataComponentMap getComponents() {
        return comps;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.comps,this.parts);
    }
    @Override public boolean equals(Object obj) {
        if(obj == this) {return true;}
        else {
            return obj instanceof GunComp egg &&
                    egg.comps == this.comps &&
                    egg.parts == this.parts;
        }
    }
}
