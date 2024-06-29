package com.lensmods.lenssgs.core.weaponsystems;

import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.world.item.ItemStack;

public class GunLogic {

    public static boolean doesHaveAmmo(ItemStack gun) {
        return gun.get(LenDataComponents.AMMO_COUNTER) > 0;
    }

}
