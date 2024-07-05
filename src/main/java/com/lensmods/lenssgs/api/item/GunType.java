package com.lensmods.lenssgs.api.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public final class GunType {
    private static final Pattern VALID_NAME = Pattern.compile("[^a-z_]");
    private static final Map<String, GunType> VALUES = new HashMap<>();
    private static final Map<GunType, ICoreItem> ITEMS = new HashMap<>();


    public static final GunType NONE = getOrCreate("none");
    public static final GunType ALL = getOrCreate("all");
    public static final GunType PART = getOrCreate("part"); //I can and will steal code, no matter how bad.

    public static GunType get(String name) {
        return VALUES.getOrDefault(name,NONE);
    }

    public static GunType getOrCreate(String name) {
        return getOrCreate(name,null, b -> {});
    }

    public static GunType getOrCreate(String name, @Nullable GunType parent) {
        return getOrCreate(name,parent,b -> {});
    }
    public static GunType getOrCreate(String name, @Nullable GunType parent, Consumer<Builder> propertiesBuilder) {
        if (VALID_NAME.matcher(name).find()) {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
        return VALUES.computeIfAbsent(name,s->{
            Builder bob =  Builder.of(name,parent);
            propertiesBuilder.accept(bob);
            return bob.build();
        });
    }
    public static GunType fromJson(JsonObject json,String key) {
        String boi = GsonHelper.getAsString(json,key);
        GunType type = get(boi);
        if(type.isInvalid()) {
            throw new JsonSyntaxException("Unknown Gun Type: " + boi);
        }
        return type;
    }

    private final String name;
    @Nullable private final GunType parent;

    private GunType (String name, @Nullable GunType parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    @Nullable GunType getParent() {
        return parent;
    }

    public boolean matches(String type) {
        return matches(type, true);
    }
    public boolean matches(GunType type) {
        return matches(type.name, true);
    }
    public boolean matches(String type, boolean includeAll) {
        if (type.contains("/")) {
            return matches(type.split("/")[1], includeAll);
        }
        return (includeAll && "all".equals(type)) || name.equals(type) || (parent != null && parent.matches(type, includeAll));
    }
    public boolean matches(GunType type, boolean includeAll) {
        return matches(type.name, includeAll);
    }
    public boolean isGun() {
        return matches(ALL, false);
    }

    public boolean isInvalid() {
        return this == NONE;
    }

    public MutableComponent getDisplayName() {
        return Component.translatable("gunType.lenssgs."+this.name);
    }

    public static class Builder {
        private final String name;
        @Nullable private final GunType parent;

        private Builder(String name, @Nullable GunType parent) {
            this.name = name;
            this.parent = parent;
        }

        public static Builder of(String name) {
            return of(name, null);
        }

        public static Builder of(String name, @Nullable GunType parent) {
            return new Builder(name, parent);
        }

        public GunType build() {
            return new GunType(name, parent);
        }
    }
}
