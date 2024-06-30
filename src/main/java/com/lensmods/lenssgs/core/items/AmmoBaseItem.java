package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.init.LenDataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class AmmoBaseItem extends Item {

    private Random randy;

    public AmmoBaseItem(Properties pProperties) {
        super(pProperties);
    }
    public int getProjAmt(ItemStack stacc) {
        float baseProjCount = stacc.get(LenDataComponents.PROJ_COUNT);
        return randy.nextFloat(0,1) < baseProjCount ? Mth.floor(baseProjCount) : Mth.ceil(baseProjCount);
    }
}
