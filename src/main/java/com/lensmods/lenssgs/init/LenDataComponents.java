package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LenDataComponents {

    public static final DeferredRegister.DataComponents DATA_REG = DeferredRegister.createDataComponents(LensSGS.MODID);

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Integer>> AMMO_MAX =
            DATA_REG.registerComponentType("ammo_max", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Integer>> AMMO_COUNTER =
            DATA_REG.registerComponentType("ammo_counter", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Float>> FIRE_RATE =
            DATA_REG.registerComponentType("fire_rate", builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Float>> PROJ_COUNT =
            DATA_REG.registerComponentType("proj_count",builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Float>> DMG_MAX =
            DATA_REG.registerComponentType("dmg_max",builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Float>> DMG_MIN =
            DATA_REG.registerComponentType("dmg_min",builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Double>> GRAV_MOD =
            DATA_REG.registerComponentType("grav_mod",builder -> builder.persistent(Codec.DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Float>> VEL_MULT =
            DATA_REG.registerComponentType("vel_mult",builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));

}
