package com.lensmods.lenssgs.api.part;

import com.lensmods.lenssgs.api.item.GunType;
import com.lensmods.lenssgs.api.material.MaterialList;
import com.lensmods.lenssgs.core.guns.part.PartData;
import com.lensmods.lenssgs.core.util.IGunComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;

import javax.annotation.Nullable;

public interface IGunPart extends IGunComponent<IPartData> {
    ResourceLocation getId();
    PartType getType();
    default GunType getGunType() {
        return GunType.ALL;
    }

    IPartSerializer<?> getSerializer();
    default String getPackName() {
        return "RUH ROH RAGGY, RUNKNOWN!";
    }
    @Override
    default MaterialList getMaterials(IPartData part) {
        return MaterialList.empty();
    }
    default boolean craftAllowed(IPartData part, GunType gunType) {
        if(gunType.isGun() && this.getType() == PartType.MAIN) {
            return false; //Todo: Fix this, no idea how.
        }
        return true;
    }
    default boolean craftAllowed(IPartData part, PartType partType, GunType gunType, @Nullable Container inv) {
        return craftAllowed(part,gunType);
    }
    default boolean replacesExistingParts(PartData part) {
        return true;
    }
}
