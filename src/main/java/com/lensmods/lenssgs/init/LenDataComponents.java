package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.datacomps.*;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class LenDataComponents {

    public static final DeferredRegister.DataComponents DATA_REG = DeferredRegister.createDataComponents(LensSGS.MODID);

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Integer>> AMMO_COUNTER =
            DATA_REG.registerComponentType("ammo_counter",build -> build.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<GunComp>> GUN_COMP =
            DATA_REG.registerComponentType("gun_comp",builder -> builder.persistent(GunComp.GUN_COMP_CODEC).networkSynchronized(GunComp.GUN_COMP_SCODEC));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<GunPartHolder>> GUN_PART_HOLDER =
            DATA_REG.registerComponentType("gun_part_holder",builder -> builder.persistent(GunPartHolder.GUN_PART_CODEC).networkSynchronized(GunPartHolder.GUN_PART_SCODEC));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<String>> PART_TYPE =
            DATA_REG.registerComponentType("part_type",builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<String>> PART_SUB_TYPE =
            DATA_REG.registerComponentType("part_sub_type",builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<List<ModelColorPair>>> PART_COLOR_LIST =
            DATA_REG.registerComponentType("part_color_list",builder -> builder.persistent(ModelColorPair.CODEC.listOf()).networkSynchronized(ModelColorPair.SCODEC.apply(ByteBufCodecs.list())));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<GunStatTraitPair>> LAST_AMMO =
            DATA_REG.registerComponentType("last_ammo",builder -> builder.persistent(GunStatTraitPair.CODEC).networkSynchronized(GunStatTraitPair.SCODEC));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<GunStatTraitPair>> GUN_STAT_TRAITS =
            DATA_REG.registerComponentType("gun_stat_traits",builder -> builder.persistent(GunStatTraitPair.CODEC).networkSynchronized(GunStatTraitPair.SCODEC));
}
