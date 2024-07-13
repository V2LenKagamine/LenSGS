package com.lensmods.lenssgs.core.datacomps;

import com.lensmods.lenssgs.core.util.LenUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class GunPartHolder implements MutableDataComponentHolder {
    private final String name;
    private final String subType;
    private final String material;
    private final PatchedDataComponentMap map;
    public GunPartHolder(String name,@Nullable String subtype,String material,PatchedDataComponentMap mappy) {
        this.name=name;
        this.subType = subtype != null ? subtype : "none";
        this.material=material;
        this.map = mappy;
    }
    public GunPartHolder(String name,String subType, String gun, DataComponentPatch patch) {
        this.name=name;
        this.material = gun;
        this.subType = subType;
        this.map = PatchedDataComponentMap.fromPatch(
                DataComponentMap.EMPTY,
                patch
        );
    }
    public GunPartHolder(String name,String subtype,String gun) {
        this.name = name;
        this.material=gun;
        this.subType = subtype;
        this.map = new PatchedDataComponentMap(DataComponentMap.EMPTY);
    }

    public String getName() {
        return name;
    }

    public String getMaterial() {
        return material;
    }
    public String getSubType() {
        return this.subType != null ? this.subType : "none";
    }

    public static final Codec<GunPartHolder> GUN_PART_CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
               Codec.STRING.fieldOf("name").forGetter(GunPartHolder::getName),
               Codec.STRING.fieldOf("subType").forGetter(GunPartHolder::getSubType),
               Codec.STRING.fieldOf("material").forGetter(GunPartHolder::getMaterial),
                    DataComponentPatch.CODEC.optionalFieldOf("map",DataComponentPatch.EMPTY).forGetter(hold -> hold.map.asPatch())
            ).apply(inst,GunPartHolder::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, GunPartHolder> GUN_PART_SCODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, GunPartHolder::getName,
            ByteBufCodecs.STRING_UTF8, GunPartHolder::getSubType,
            ByteBufCodecs.STRING_UTF8,GunPartHolder::getMaterial,
            DataComponentPatch.STREAM_CODEC,holder -> holder.map.asPatch(),
            GunPartHolder::new
    );

    @Override
    public <T> @Nullable T set(DataComponentType<? super T> dataComponentType, @Nullable T t) {
        return this.map.set(dataComponentType,t);
    }

    @Override
    public <T> @Nullable T remove(DataComponentType<? extends T> dataComponentType) {
        return this.map.remove(dataComponentType);
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
        return Objects.hash(name,material,map);
    }
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }else {
            return o instanceof GunPartHolder ex &&
                    this.material == ex.getMaterial() &&
                    this.name == ex.getName() &&
                    this.map == ex.getComponents();
        }
    }

    public Component getTotalName() {
        return LenUtil.translatableOf(material).copy().append(LenUtil.spaceAppend(LenUtil.translatableOf(subType)));
    }
    public Component getGunPartName() {
        return LenUtil.translatableOf(subType);
    }
}
