package com.lensmods.lenssgs.core.items;

import com.lensmods.lenssgs.api.item.GunType;
import com.lensmods.lenssgs.api.material.IMaterialInstance;
import com.lensmods.lenssgs.api.part.PartType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CompPartItem extends Item {

    private final PartType partType;
    public CompPartItem(Properties pProperties,PartType partType) {
        super(pProperties);
        this.partType = partType;
    }
    public PartType getPartType(){
        return partType;
    }
    public GunType getGunType() {
        return GunType.PART;
    }
    public ItemStack create (IMaterialInstance mat) {
        return create(mat,-1);
    }
    public ItemStack create(IMaterialInstance mat,int craftedAmt) {
        ItemStack result = new ItemStack(this, craftedAmt > 0 ? craftedAmt : 1);
        //Todo: use set to set materials datacomponents to part.
        return result;
    }
}
