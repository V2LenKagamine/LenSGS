package com.lensmods.lenssgs.core.guns.part;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.lensmods.lenssgs.api.part.IGunPart;
import com.lensmods.lenssgs.api.part.IPartData;
import com.lensmods.lenssgs.api.part.IPartSerializer;
import com.lensmods.lenssgs.api.part.PartType;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractGunPart implements IGunPart {
    private final ResourceLocation name;
    String pName = "Unknown";
    Ingredient ingredient = Ingredient.EMPTY;
    boolean visible = true;
    List<String> gunBlacklist = new ArrayList<>();

    Component displayName;

    public AbstractGunPart(ResourceLocation location) {
        this.name = location;
    }

    @Override
    public ResourceLocation getId() {
        return name;
    }

    @Override
    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public String getPackName() {
        return pName;
    }

    public void updateCraftingItems(Ingredient ing) {
        this.ingredient = ing;
    }

    @Override
    public Component getDisplayName(@Nullable IPartData instance, PartType type, ItemStack gear) {
        return null;
    }

    public static class Serializer<T extends AbstractGunPart> implements IPartSerializer<T> {
        private final ResourceLocation serializerId;
        private final Function<ResourceLocation,T> func;

        public Serializer(ResourceLocation serializerId, Function<ResourceLocation, T> function) {
            this.serializerId = serializerId;
            this.func = function;
        }

        @Override
        public T read(ResourceLocation id, JsonObject object) {
            return read(id,object,true);
        }

        protected T read(ResourceLocation id,JsonObject obj,boolean failOnMissing) {
            T part = func.apply(id);
            JsonElement stats = obj.get("stats");
            if (stats !=null) {
                //TODO: Put data components from json -> stack
            }
            JsonElement traits = obj.get("traits");
            if(traits!=null && traits.isJsonArray()) {
                //TODO: Put data from Json -> stack
            }
            JsonElement crafting = obj.get("craft_item");
            if (crafting != null) {
                part.ingredient = Ingredient.CODEC.decode(JsonOps.INSTANCE,crafting).getOrThrow().getFirst();
            }
            else if ( failOnMissing) {
                throw new JsonSyntaxException("Missing 'craft_item");
            }
            JsonElement name = obj.get("name");
            if (name != null && name.isJsonObject()) {
                part.displayName = ComponentSerialization.CODEC.decode(JsonOps.INSTANCE,name).getOrThrow().getFirst();
            }
            JsonElement blacklist = obj.get("availability");
            if(blacklist != null && blacklist.isJsonObject())
            {
                JsonObject blackerlist = blacklist.getAsJsonObject();
                part.visible = GsonHelper.getAsBoolean(blackerlist,"visible",part.visible);
                JsonArray real = getGunBlacklist(blackerlist);
                if(real != null) {
                    part.gunBlacklist.clear();
                    real.forEach(e-> part.gunBlacklist.add(e.getAsString()));
                }
            }
            return part;
        }
        @Nullable
        private static JsonArray getGunBlacklist(JsonObject json) {
            if (json.has("gun_blacklist"))
                return GsonHelper.getAsJsonArray(json, "gun_blacklist");
            return null;
        }
        @Override
        public T read(ResourceLocation id, FriendlyByteBuf buffer) {
            T part = func.apply(id);
            part.pName = buffer.readUtf();
            part.displayName = buffer.readJsonWithCodec(ComponentSerialization.CODEC);
            part.ingredient = buffer.readJsonWithCodec(Ingredient.CODEC);
            part.visible = buffer.readBoolean();
            part.gunBlacklist.clear();
            int size = buffer.readByte();
            for(int i = 0; i< size; i++) {
                part.gunBlacklist.add(buffer.readUtf());
            }
            return part;
        }

        @Override
        public void write(FriendlyByteBuf buffer, T part) {
            buffer.writeUtf(part.getPackName());
            buffer.writeJsonWithCodec(ComponentSerialization.CODEC,part.displayName);
            buffer.writeJsonWithCodec(Ingredient.CODEC,part.ingredient);
            buffer.writeBoolean(part.visible);
            buffer.writeByte(part.gunBlacklist.size());
            part.gunBlacklist.forEach(buffer::writeUtf);
        }

        @Override
        public ResourceLocation getName() {
            return null;
        }
    }
}
