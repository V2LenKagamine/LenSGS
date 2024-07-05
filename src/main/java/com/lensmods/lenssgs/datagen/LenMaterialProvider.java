package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.api.data.materials.MaterialBuilder;
import com.lensmods.lenssgs.api.data.materials.MaterialProviderBase;
import net.minecraft.data.DataGenerator;

import java.util.ArrayList;
import java.util.Collection;

public class LenMaterialProvider extends MaterialProviderBase {
    public LenMaterialProvider(DataGenerator gener, String modId) {
        super(gener, modId);
    }

    @Override
    protected Collection<MaterialBuilder> getMaterials() {
        Collection<MaterialBuilder> thebois = new ArrayList<>();
        return thebois;
    }
}
