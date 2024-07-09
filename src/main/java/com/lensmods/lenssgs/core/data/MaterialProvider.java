package com.lensmods.lenssgs.core.data;

import com.lensmods.lenssgs.LensSGS;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MaterialProvider extends DatapackBuiltinEntriesProvider {
    public MaterialProvider(PackOutput output, RegistrySetBuilder datapackEntriesBuilder, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, datapackEntriesBuilder, Set.of(LensSGS.MODID));
    }
}
