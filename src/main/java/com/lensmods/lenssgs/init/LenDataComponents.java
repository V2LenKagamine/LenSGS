package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LenDataComponents {

    public static final DeferredRegister.DataComponents DATA_REG = DeferredRegister.createDataComponents(LensSGS.MODID);

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Integer>> AMMO_COUNTER =
            DATA_REG.registerComponentType("ammo_counter", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
}
