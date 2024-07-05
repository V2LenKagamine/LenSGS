package com.lensmods.lenssgs.core.guns.material;

import com.lensmods.lenssgs.api.item.GunType;
import com.lensmods.lenssgs.api.material.IMaterial;
import com.lensmods.lenssgs.api.material.IMaterialInstance;
import com.lensmods.lenssgs.api.material.IMaterialSerializer;
import com.lensmods.lenssgs.api.part.PartType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class AbstractMaterial implements IMaterial {
    protected final ResourceLocation matId;
    @Nullable ResourceLocation parent;
    protected final String pName;
    protected boolean visible = true;
    protected Ingredient ingredient = Ingredient.EMPTY;
    protected final Map<PartType,Ingredient> partSubs = new HashMap<>();

    protected final List<String> blacklistedGuns = new ArrayList();

    @Override
    public String getPackName() {
        return pName;
    }

    @Override
    public ResourceLocation getId() {
        return matId;
    }

    @Override
    public IMaterialSerializer<?> getSerializer() {
        return null;
    }

    @Nullable
    @Override
    public IMaterial getParent() {
        return null;
    }

    @Override
    public Set<PartType> getPartTypes(IMaterialInstance mat) {
        return Set.of();
    }

    @Override
    public boolean allowedInPart(IMaterialInstance mat, PartType partType) {
        return false;
    }

    @Override
    public Ingredient getIngredient() {
        return null;
    }

    @Override
    public boolean craftAllowed(IMaterialInstance instance, PartType partType, GunType gunType, @Nullable Container inv) {
        return false;
    }

    @Override
    public Component getDisplayName(@Nullable IMaterialInstance instance, PartType type, ItemStack gear) {
        return null;
    }
}
