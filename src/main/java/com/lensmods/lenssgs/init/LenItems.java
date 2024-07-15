package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.datacomps.GunComp;
import com.lensmods.lenssgs.core.items.AmmoBaseItem;
import com.lensmods.lenssgs.core.items.GunBaseItem;
import com.lensmods.lenssgs.core.items.GunPartBaseItem;
import com.lensmods.lenssgs.core.items.PartCraftingItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class LenItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LensSGS.MODID);

    public static final DeferredItem<Item> GUNPRINTER_PAPER = ITEMS.registerSimpleItem("gunprinter_paper", new Item.Properties());
    public static final DeferredItem<Item> VOIDMETAL = ITEMS.registerSimpleItem("void_metal",new Item.Properties());
    public static final DeferredItem<GunBaseItem> GUN_BASE = ITEMS.register("base_gun_holder",()-> new GunBaseItem(
            new Item.Properties()
                    .stacksTo(1)
                    .component(LenDataComponents.AMMO_COUNTER,0)
                    .component(LenDataComponents.GUN_COMP,new GunComp(List.of()))
                    .setNoRepair()));
    public static final DeferredItem<AmmoBaseItem> AMMO_BASE = ITEMS.register("base_ammo_holder",()-> new AmmoBaseItem(
       new Item.Properties()
               .stacksTo(1)
               .component(LenDataComponents.AMMO_COUNTER,0)
               .component(LenDataComponents.GUN_COMP,new GunComp(List.of()))
               .setNoRepair()));
    public static final DeferredItem<GunPartBaseItem> PART_BASE = ITEMS.register("gun_part_base",()-> new GunPartBaseItem(
            new Item.Properties().setNoRepair().stacksTo(1)
    ));
    public static final DeferredItem<PartCraftingItem> PART_CRAFTER =ITEMS.register("part_crafter",()-> new PartCraftingItem(
            new Item.Properties()
    ));
}
