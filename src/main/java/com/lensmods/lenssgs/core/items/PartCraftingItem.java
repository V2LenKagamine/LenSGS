package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PartCraftingItem extends Item {
    public PartCraftingItem(Properties pProperties) {
        super(pProperties);
    }
    //This is ass and I CBA to do anything better.
    public static float getOverlayFloat(ItemStack stack) {
        switch (stack.getOrDefault(LenDataComponents.PART_SUB_TYPE,"ERROR")) {
            case AllowedParts.RECEIVER_PISTOL: return 0f;
            case AllowedParts.RECEIVER_STANDARD: return 0.01f;
            case AllowedParts.RECEIVER_BULLPUP: return 0.02f;

            case AllowedParts.ACTION_MANUAL: return 0.10f;
            case AllowedParts.ACTION_SINGLE: return 0.11f;
            case AllowedParts.ACTION_AUTOMATIC: return 0.12f;

            case AllowedParts.BARREL_STUB: return 0.20f;
            case AllowedParts.BARREL_SHORT: return 0.21f;
            case AllowedParts.BARREL_FAIR: return 0.22f;
            case AllowedParts.BARREL_LONG: return 0.23f;
            case AllowedParts.BARREL_EXTENDED: return 0.24f;

            case AllowedParts.STOCK_SHORT: return 0.30f;
            case AllowedParts.STOCK_FULL: return 0.31f;

            case AllowedParts.MAGAZINE_SHORT: return 0.40f;
            case AllowedParts.MAGAZINE_NORMAL: return 0.41f;
            case AllowedParts.MAGAZINE_EXTENDED: return 0.42f;
            case AllowedParts.MAGAZINE_BELT: return 0.43f;

            case AllowedParts.SCOPE_IRONS: return 0.50f;
            case AllowedParts.SCOPE_SHORT: return 0.51f;
            case AllowedParts.SCOPE_MEDIUM: return 0.52f;
            case AllowedParts.SCOPE_LONG: return 0.53f;
            //Bullets
            case AllowedParts.CASING_SMALL: return 0.60f;
            case AllowedParts.CASING_NORMAL: return 0.61f;
            case AllowedParts.CASING_LARGE: return 0.62f;
            case AllowedParts.CASING_SHELL: return 0.63f;

            case AllowedParts.ROUND_STANDARD: return  0.70f;
            case AllowedParts.ROUND_BUCKSHOT: return  0.71f;
            case AllowedParts.ROUND_BIRDSHOT: return  0.72f;

            case AllowedParts.PROPELLANT_LIGHT: return  0.80f;
            case AllowedParts.PROPELLANT_NORMAL: return  0.81f;
            case AllowedParts.PROPELLANT_HEAVY: return  0.82f;

            default: return 1f;
        }
    }

    @Override
    public Component getName(ItemStack pStack) {
        return LenUtil.translatableOf(pStack.getOrDefault(LenDataComponents.PART_SUB_TYPE,"ERROR")).copy().append(LenUtil.spaceAppend(LenUtil.translatableOf("part_crafter")));
    }


}
