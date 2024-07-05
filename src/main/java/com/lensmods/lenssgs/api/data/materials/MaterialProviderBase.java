package com.lensmods.lenssgs.api.data.materials;

import com.google.common.collect.Sets;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;


public abstract class MaterialProviderBase implements DataProvider {
    protected final DataGenerator generator;
    protected final String modId;

    public MaterialProviderBase(DataGenerator gener, String modId) {
        this.generator = gener;
        this.modId = modId;
    }

    protected abstract Collection<MaterialBuilder> getMaterials();

    protected ResourceLocation modID(String path) {
        return ResourceLocation.fromNamespaceAndPath(this.modId,path);
    }

    protected static ResourceLocation neoforgeID(String path) {
        return ResourceLocation.fromNamespaceAndPath("neoforge",path);
    }
    @Override
    public @NotNull String getName() {
        return "LensSGS: " + modId;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Path output = this.generator.getPackOutput().getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        List<CompletableFuture<?>> list = new ArrayList<>();

        this.getMaterials().forEach(builder -> {
           if (!set.add(builder.getId())) {
               throw new IllegalStateException("Dupe in Materials: "+builder.getId());
           }
           Path path = output.resolve(String.format("data/%s/lenssgs_mats/%s.json",builder.getId().getNamespace(),builder.getId().getPath()));
           list.add(DataProvider.saveStable(cache,builder.serialize(),path));
        });

        return CompletableFuture.allOf(list.toArray(new CompletableFuture[0]));
    }
}
