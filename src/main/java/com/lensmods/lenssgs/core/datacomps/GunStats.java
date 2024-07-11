package com.lensmods.lenssgs.core.datacomps;

import com.lensmods.lenssgs.LensSGS;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class GunStats implements MutableDataComponentHolder {

    private final PatchedDataComponentMap comps;
    private int ammo_max;
    private int peirce;
    private int firerate;
    private float inaccuracy;
    private float projectile_count;
    private float damage_max;
    private float damage_min;
    private float vel_multi;
    private double grav_mod;

    public GunStats() {
        this.comps = new PatchedDataComponentMap(DataComponentMap.EMPTY);
        LensSGS.L3NLOGGER.error("SOMEBODY MADE A NULL GUNSTATS. BAD.");
    }
    public GunStats(int ammo_max, int peirce, int firerate,float inaccuracy ,float projectile_count, float damage_max, float damage_min, float vel_multi, double grav_mod, DataComponentPatch patch) {
        this.comps = PatchedDataComponentMap.fromPatch(
                DataComponentMap.EMPTY,
                patch
        );
        this.ammo_max = ammo_max;
        this.peirce = peirce;
        this.firerate = firerate;
        this.inaccuracy = inaccuracy;
        this.projectile_count = projectile_count;
        this.damage_max = damage_max;
        this.damage_min = damage_min;
        this.vel_multi = vel_multi;
        this.grav_mod = grav_mod;
    }
    public GunStats(int ammoMax, int peirce, int firerate,float inaccuracy ,float projectileCount, float damageMax, float damageMin, float velMulti, double gravMod) {
        this.comps = new PatchedDataComponentMap(PatchedDataComponentMap.EMPTY);
        this.ammo_max = ammoMax;
        this.peirce = peirce;
        this.firerate = firerate;
        this.inaccuracy = inaccuracy;
        this.projectile_count = projectileCount;
        this.damage_max = damageMax;
        this.damage_min = damageMin;
        this.vel_multi = velMulti;
        this.grav_mod = gravMod;
    }
    public GunStats(SubStreamCodec subA, SubStreamCodec2 subB, DataComponentPatch patch) {
        this(subA.ammoMax,subA.peirce,subA.fireRate, subA.inacc, subB.projCount,subB.dmgMax,subB.dmgMin,subB.velMul,subB.grav,patch);
    }

    public static final Codec<GunStats> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.INT.fieldOf("ammo_max").forGetter(GunStats::getAmmo_max),
                    Codec.INT.fieldOf("peirce").forGetter(GunStats::getPierce),
                    Codec.INT.fieldOf("firerate").forGetter(GunStats::getFirerate),
                    Codec.FLOAT.fieldOf("inaccuracy").forGetter(GunStats::getInaccuracy),
                    Codec.FLOAT.fieldOf("projectile_count").forGetter(GunStats::getProjCount),
                    Codec.FLOAT.fieldOf("damage_max").forGetter(GunStats::getDamageMax),
                    Codec.FLOAT.fieldOf("damage_min").forGetter(GunStats::getDamageMin),
                    Codec.FLOAT.fieldOf("vel_multi").forGetter(GunStats::getVelocityMult),
                    Codec.DOUBLE.fieldOf("grav_mod").forGetter(GunStats::getGravMod),
                    DataComponentPatch.CODEC.optionalFieldOf("comps",DataComponentPatch.EMPTY).forGetter(hold -> hold.comps.asPatch())
            ).apply(inst,GunStats::new));

    public float getInaccuracy() {
        return this.inaccuracy;
    }

    //I hate this so much. Words can not describe how much I hate the inability to just make one long stream codec.
    public SubStreamCodec hate() {
        return new SubStreamCodec(this.ammo_max,this.peirce,this.firerate,this.inaccuracy);
    }
    public SubStreamCodec2 thisJank() {
        return new SubStreamCodec2(this.projectile_count,this.damage_max,this.damage_min,this.vel_multi,this.grav_mod);
    }

    public static final StreamCodec<RegistryFriendlyByteBuf,GunStats> SCODEC = StreamCodec.composite(
            SubStreamCodec.THIS,GunStats::hate,
            SubStreamCodec2.SUCKS,GunStats::thisJank,
            DataComponentPatch.STREAM_CODEC, somuch -> somuch.comps.asPatch(),
            GunStats::new);

    private record SubStreamCodec(int ammoMax, int peirce, int fireRate,float inacc) {
        private static final StreamCodec<ByteBuf,SubStreamCodec> THIS = StreamCodec.composite(
                ByteBufCodecs.INT,SubStreamCodec::ammoMax,
                ByteBufCodecs.INT,SubStreamCodec::peirce,
                ByteBufCodecs.INT,SubStreamCodec::fireRate,
                ByteBufCodecs.FLOAT,SubStreamCodec::inacc,
                SubStreamCodec::new
        );
    }
    private record SubStreamCodec2(float projCount,float dmgMax,float dmgMin, float velMul, double grav) {
        private static final StreamCodec<ByteBuf,SubStreamCodec2> SUCKS = StreamCodec.composite(
                ByteBufCodecs.FLOAT,SubStreamCodec2::projCount,
                ByteBufCodecs.FLOAT,SubStreamCodec2::dmgMax,
                ByteBufCodecs.FLOAT,SubStreamCodec2::dmgMin,
                ByteBufCodecs.FLOAT,SubStreamCodec2::velMul,
                ByteBufCodecs.DOUBLE,SubStreamCodec2::grav,
                SubStreamCodec2::new
        );
    }

    public Double getGravMod() {
        return this.grav_mod;
    }

    public Float getVelocityMult() {
        return this.vel_multi;
    }

    public Float getDamageMin() {
        return this.damage_min;
    }

    public Float getDamageMax() {
        return this.damage_max;
    }

    public Float getProjCount() {
        return this.projectile_count;
    }

    public Integer getFirerate() {
        return this.firerate;
    }

    public Integer getPierce() {
        return this.peirce;
    }

    public Integer getAmmo_max() {
        return this.ammo_max;
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
        return this.comps;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.comps,this.ammo_max,this.peirce,this.firerate,this.projectile_count,this.damage_max,this.damage_min,this.vel_multi,this.grav_mod);
    }
    @Override public boolean equals(Object obj) {
        if(obj == this) {return true;}
        else {
            return obj instanceof GunStats egg &&
                    egg.comps == this.comps &&
                    this.ammo_max == egg.ammo_max &&
                    this.peirce == egg.peirce &&
                    this.firerate == egg.firerate &&
                    this.projectile_count == egg.projectile_count &&
                    this.damage_max == egg.damage_max &&
                    this.damage_min == egg.damage_min &&
                    this.vel_multi == egg.vel_multi &&
                    this.grav_mod == egg.grav_mod;
        }
    }
}
