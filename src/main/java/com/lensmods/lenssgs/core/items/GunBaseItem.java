package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.core.weaponsystems.GunLogic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GunBaseItem extends Item{

    private GunLogic logic = new GunLogic();

    public GunLogic revealLogic() {
        return this.logic;
    }

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
}
