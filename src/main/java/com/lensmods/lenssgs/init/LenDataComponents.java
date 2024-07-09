package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.datacomps.*;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LenDataComponents {

    public static final DeferredRegister.DataComponents DATA_REG = DeferredRegister.createDataComponents(LensSGS.MODID);

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Integer>> AMMO_COUNTER =
            DATA_REG.registerComponentType("ammo_counter",build -> build.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<GunStats>> GUN_STATS =
            DATA_REG.registerComponentType("gun_stats",build -> build.persistent(GunStats.CODEC).networkSynchronized(GunStats.SCODEC));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<GunComp>> GUN_COMP =
            DATA_REG.registerComponentType("gun_comp",builder -> builder.persistent(GunComp.GUN_COMP_CODEC).networkSynchronized(GunComp.GUN_COMP_SCODEC));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<GunPartHolder>> GUN_PART_HOLDER =
            DATA_REG.registerComponentType("gun_part_holder",builder -> builder.persistent(GunPartHolder.GUN_PART_CODEC).networkSynchronized(GunPartHolder.GUN_PART_SCODEC));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<GunMaterial>> GUM_MAT_HOLDER =
            DATA_REG.registerComponentType("gun_material_holder",builder -> builder.persistent(GunMaterial.GUN_MAT_CODEC).networkSynchronized(GunMaterial.GUN_MAT_SCODEC));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<StatMod>> STAT_MOD_HOLDER =
            DATA_REG.registerComponentType("stat_mod_holder",builder -> builder.persistent(StatMod.STAT_MOD_CODEC).networkSynchronized(StatMod.STAT_MOD_STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<TraitLevel>> TRAIT_LVL_HOLDER =
            DATA_REG.registerComponentType("trait_lvl_holder",builder -> builder.persistent(TraitLevel.TRAIT_LEVEL_CODEC).networkSynchronized(TraitLevel.TRAIT_LEVEL_STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<String>> PART_TYPE =
            DATA_REG.registerComponentType("part_type",builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<String>> PART_SUB_TYPE =
            DATA_REG.registerComponentType("part_sub_type",builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<ItemStack>> OVERRIDE_MODEL =
            DATA_REG.registerComponentType("model_item",builder -> builder.persistent(ItemStack.CODEC).networkSynchronized((ItemStack.STREAM_CODEC)));
}
