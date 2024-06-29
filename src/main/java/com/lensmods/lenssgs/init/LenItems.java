package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.items.GunBaseItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LenItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LensSGS.MODID);

    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = LenItems.ITEMS.registerSimpleBlockItem("example_block", LensSGS.EXAMPLE_BLOCK);
    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));
    public static final DeferredItem<GunBaseItem> GUN_BASE = ITEMS.register("base_gun_holder",()-> new GunBaseItem(new Item.Properties().component(LenDataComponents.AMMO_COUNTER.value(),Integer.valueOf(30))
            .stacksTo(1)
            .setNoRepair()));
}
