package com.lensmods.lenssgs.api.material;

import com.lensmods.lenssgs.api.part.PartType;
import com.lensmods.lenssgs.core.util.IGunComponent;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Set;

public interface IMaterial extends IGunComponent<IMaterialInstance> {
    String getPackName();
    ResourceLocation getId();
    IMaterialSerializer<?> getSerializer();
    @Nullable IMaterial getParent();
    Set<PartType> getPartTypes(IMaterialInstance mat);
    boolean allowedInPart(IMaterialInstance mat,PartType partType);
    default void retainData(@Nullable IMaterial oldMat){}
    default boolean isVisible(PartType partType) {
        return true;
    }

    @Override
    default MaterialList getMaterials(IMaterialInstance instance) {
        return MaterialList.empty();
    }
}
