package com.lensmods.lenssgs.api.part;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.api.item.GunType;
import com.lensmods.lenssgs.core.guns.part.CompPart;
import com.lensmods.lenssgs.core.items.CompPartItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public final class PartType {

    private static final Map<ResourceLocation,PartType> VALUES = new LinkedHashMap<>();

    public static final PartType NONE = create(Builder.builder(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"none")));

    public static final PartType MAIN = create(Builder.builder(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"main"))
            .compPartItem(PartType::getFrame));


    public static PartType create(Builder bob) {
        if (VALUES.containsKey(bob.name)) {
            throw new IllegalArgumentException(String.format("Already have PartType \"%s\"",bob.name));
        }
        PartType type = new PartType(bob);
        VALUES.put(bob.name,type);
        if (!type.alias.isEmpty()) {
            VALUES.put(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,type.alias),type);
        }
        return type;
    }
    @Nullable
    public static PartType get(ResourceLocation name) {
        return VALUES.get(name);
    }

    public static PartType nonNullGet(ResourceLocation name) {
        PartType p = get(name);
        return p != null ? p : NONE;
    }

    public static Collection<PartType> getValues() {
        return VALUES.values();
    }

    public static PartType fromJson(JsonObject json,String key) {
        String boi = GsonHelper.getAsString(json,key);
        PartType type = get(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,boi));
        if(type == null) {
            throw new JsonSyntaxException("Unknown Part Type: " + boi);
        }
        return type;
    }

    private final ResourceLocation name;
    private final boolean removable;
    private final boolean isUpgrade;
    private final Function<GunType,Integer> maxPerGun;
    private final String alias;

    private PartType(Builder builder) {
        this.name = builder.name;
        this.removable = builder.removable;
        this.isUpgrade = builder.isUpgrade;
        this.maxPerGun = builder.maxPerGun;
        //this.defaultTex = builder.defaultTex;
        this.alias = builder.alias;
    }

    public boolean isInvalid() {
        return this == NONE;
    }

    public ResourceLocation getName() {
        return name;
    }

    public boolean isRemovable() {
        return removable;
    }

    public boolean isUpgrade() {
        return isUpgrade;
    }
    public int getMaxPerGun(GunType type) {
        return maxPerGun.apply(type);
    }

    public MutableComponent getDisplayName() {
        return Component.translatable("part."+name.getNamespace() + ".type"+name.getPath());
    }


    public static final class Builder {
        private final ResourceLocation name;
        private boolean removable = false;
        private boolean isUpgrade = false;
        private Function<GunType,Integer> maxPerGun = gt -> 1;
        private String alias = "";

        private Builder(ResourceLocation name) {
            this.name = name;
        }

        public static Builder builder(ResourceLocation name) {
            return new Builder(name);
        }

        public Builder isRemovable(boolean value) {
            this.removable = value;
            return this;
        }

        public Builder isUpgrade(boolean value) {
            this.isUpgrade = value;
            return this;
        }

        public Builder maxPerGun(int value) {
            return maxPerGun(gt -> value);
        }

        public Builder maxPerGun(Function<GunType, Integer> function) {
            this.maxPerGun = function;
            return this;
        }

        public Builder alias(String name) {
            this.alias = name;
            return this;
        }
    }
}
