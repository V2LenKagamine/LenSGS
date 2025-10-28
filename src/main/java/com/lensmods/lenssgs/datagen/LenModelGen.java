package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.init.LenItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class LenModelGen extends ItemModelProvider {
    public LenModelGen(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        //Blueprint fuckery below
        ItemModelBuilder r_pistol = withExistingParent(AllowedParts.RECEIVER_PISTOL, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/r_pistol");
        ItemModelBuilder r_standard = withExistingParent(AllowedParts.RECEIVER_STANDARD, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/r_standard");
        ItemModelBuilder r_bullpup = withExistingParent(AllowedParts.RECEIVER_BULLPUP, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/r_bullpup");

        ItemModelBuilder a_single = withExistingParent(AllowedParts.ACTION_SINGLE, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/a_single");
        ItemModelBuilder a_manual = withExistingParent(AllowedParts.ACTION_MANUAL, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/a_manual");
        ItemModelBuilder a_auto = withExistingParent(AllowedParts.ACTION_AUTOMATIC, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/a_auto");

        ItemModelBuilder b_stub = withExistingParent(AllowedParts.BARREL_STUB, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/b_stub");
        ItemModelBuilder b_short = withExistingParent(AllowedParts.BARREL_SHORT, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/b_short");
        ItemModelBuilder b_fair = withExistingParent(AllowedParts.BARREL_FAIR, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/b_fair");
        ItemModelBuilder b_long = withExistingParent(AllowedParts.BARREL_LONG, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/b_long");
        ItemModelBuilder b_ext = withExistingParent(AllowedParts.BARREL_EXTENDED, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/b_ext");

        ItemModelBuilder stock_short = withExistingParent(AllowedParts.STOCK_SHORT, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/stock_short");
        ItemModelBuilder stock_full = withExistingParent(AllowedParts.STOCK_FULL, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/stock_full");

        ItemModelBuilder m_short = withExistingParent(AllowedParts.MAGAZINE_SHORT, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/m_short");
        ItemModelBuilder m_norm = withExistingParent(AllowedParts.MAGAZINE_NORMAL, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/m_norm");
        ItemModelBuilder m_ext = withExistingParent(AllowedParts.MAGAZINE_EXTENDED, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/m_ext");
        ItemModelBuilder m_belt = withExistingParent(AllowedParts.MAGAZINE_BELT, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/m_belt");

        ItemModelBuilder s_iron = withExistingParent(AllowedParts.SCOPE_IRONS, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/s_iron");
        ItemModelBuilder s_short = withExistingParent(AllowedParts.SCOPE_SHORT, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/s_short");
        ItemModelBuilder s_med = withExistingParent(AllowedParts.SCOPE_MEDIUM, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/s_med");
        ItemModelBuilder s_long = withExistingParent(AllowedParts.SCOPE_LONG, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/s_long");
        //Bullets
        ItemModelBuilder c_small = withExistingParent(AllowedParts.CASING_SMALL, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/c_small");
        ItemModelBuilder c_norm = withExistingParent(AllowedParts.CASING_NORMAL, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/c_norm");
        ItemModelBuilder c_large = withExistingParent(AllowedParts.CASING_LARGE, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/c_large");
        ItemModelBuilder c_shell = withExistingParent(AllowedParts.CASING_SHELL, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/c_shell");

        ItemModelBuilder r_norm = withExistingParent(AllowedParts.ROUND_STANDARD, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/r_norm");
        ItemModelBuilder r_buck = withExistingParent(AllowedParts.ROUND_BUCKSHOT, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/r_buck");
        ItemModelBuilder r_bird = withExistingParent(AllowedParts.ROUND_BIRDSHOT, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/r_bird");

        ItemModelBuilder p_light = withExistingParent(AllowedParts.PROPELLANT_LIGHT, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/p_light");
        ItemModelBuilder p_norm = withExistingParent(AllowedParts.PROPELLANT_NORMAL, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/p_norm");
        ItemModelBuilder p_heavy = withExistingParent(AllowedParts.PROPELLANT_HEAVY, mcLoc("item/generated"))
                .texture("layer0", "item/crafters/base")
                .texture("layer1", "item/crafters/p_heavy");

        withExistingParent(LenItems.PART_CRAFTER.getId().toString(),mcLoc("item/generated")).texture("layer0","item/crafters/base")
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.00F)
                .model(r_pistol).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.01F)
                .model(r_standard).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.02F)
                .model(r_bullpup).end()

                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.10F)
                .model(a_manual).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.11F)
                .model(a_single).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.12F)
                .model(a_auto).end()

                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.20F)
                .model(b_stub).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.21F)
                .model(b_short).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.22F)
                .model(b_fair).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.23F)
                .model(b_long).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.24F)
                .model(b_ext).end()

                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.30F)
                .model(stock_short).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.31F)
                .model(stock_full).end()

                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.40F)
                .model(m_short).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.41F)
                .model(m_norm).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.42F)
                .model(m_ext).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.43F)
                .model(m_belt).end()

                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.50F)
                .model(s_iron).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.51F)
                .model(s_short).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.52F)
                .model(s_med).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.53F)
                .model(s_long).end()
                //Bullets
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.60F)
                .model(c_small).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.61F)
                .model(c_norm).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.62F)
                .model(c_large).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.63F)
                .model(c_shell).end()

                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.70F)
                .model(r_norm).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.71F)
                .model(r_buck).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.72F)
                .model(r_bird).end()

                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.80F)
                .model(p_light).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.81F)
                .model(p_norm).end()
                .override()
                .predicate(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"crafter_type"),0.82F)
                .model(p_heavy).end()
        ;



        //The basics
        basicItem(LenItems.GUNPRINTER_PAPER.get());
        basicItem(LenItems.BLITZGOLD.get());
        basicItem(LenItems.VOIDMETAL.get());
        basicItem(LenItems.WYRMSTEEL.get());
        basicItem(LenItems.NURUKUKAN.get());
        basicItem(LenItems.HOCOFE.get());

    }
}
