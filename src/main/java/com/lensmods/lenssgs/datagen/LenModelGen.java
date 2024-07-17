package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.init.LenItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class LenModelGen extends ItemModelProvider {
    public LenModelGen(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(LenItems.PART_CRAFTER.get());
        basicItem(LenItems.GUNPRINTER_PAPER.get());
        basicItem(LenItems.VOIDMETAL.get());
    }
}
