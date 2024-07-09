package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.api.IModdable;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.datacomps.GunPartHolder;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class GunBaseItem extends Item implements IModdable {

    public GunBaseItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
    @Override
    public Component getName(ItemStack pStack) {
        var maybe = pStack.getOrDefault(LenDataComponents.GUN_COMP,null);
        if (maybe != null) {
            String barrel = "";
            String stock = "";
            String action = "";
            for(GunPartHolder part : maybe.getPartList()) {
                switch (part.getName()) {
                    case AllowedParts.BARREL -> barrel = part.getGunPartName().getString();
                    case AllowedParts.ACTION -> action = part.getTotalName().getString();
                    case AllowedParts.STOCK -> stock = part.getGunPartName().getString();
                }
            }
            return Component.translatable(barrel).copy().append(LenUtil.spaceAppend(action)).append(LenUtil.spaceAppend(stock));
        }
        return Component.literal("LOUD INCORRECT BUZZER AKA ERROR");
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        LenUtil.showGunData(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
