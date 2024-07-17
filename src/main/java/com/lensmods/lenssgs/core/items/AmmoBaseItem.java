package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.api.IModdable;
import com.lensmods.lenssgs.client.render.CustomGunRenderer;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.datacomps.GunPartHolder;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.core.weaponsystems.WeaponAmmoStats;
import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class AmmoBaseItem extends Item implements IModdable {
    public AmmoBaseItem(Properties pProperties) {
        super(pProperties);
    }
    /*
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        LenUtil.showGunData(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
    */
    @Override
    public Component getName(ItemStack pStack) {
        var maybe = pStack.getOrDefault(LenDataComponents.GUN_COMP,null);
        if (maybe != null) {
            String round = "";
            String casing = "";
            String prop = "";
            for(GunPartHolder part : maybe.getPartList()) {
                switch (part.getName()) {
                    case AllowedParts.ROUND -> round = part.getTotalName().getString();
                    case AllowedParts.CASING -> casing = part.getGunPartName().getString();
                    case AllowedParts.PROPELLANT -> prop = part.getGunPartName().getString();
                }
            }
            return Component.translatable(prop).copy().append(LenUtil.spaceAppend(casing)).append(LenUtil.spaceAppend(round));
        }
        return Component.literal("LOUD INCORRECT BUZZER AKA ERROR");
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return WeaponAmmoStats.getAmmoMax(pStack)*WeaponAmmoStats.AMMO_POINTS_MUL > WeaponAmmoStats.ammoAmountLeft(pStack);
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round((float)WeaponAmmoStats.ammoAmountLeft(pStack)/(float) (WeaponAmmoStats.getAmmoMax(pStack)));
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return CustomGunRenderer.get();
            }
        });
    }
}
