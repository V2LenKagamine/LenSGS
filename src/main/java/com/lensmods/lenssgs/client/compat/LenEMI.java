package com.lensmods.lenssgs.client.compat;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.datagen.LenLang;
import com.lensmods.lenssgs.init.LenItems;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@EmiEntrypoint
public class LenEMI implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        registry.addRecipe(new EmiInfoRecipe(List.of(EmiStack.of(LenItems.GUNPRINTER_PAPER)), List.of(LenLang.GUN_INSTRUCTIONS)
                ,ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"gun_instructions")));
    }
}
