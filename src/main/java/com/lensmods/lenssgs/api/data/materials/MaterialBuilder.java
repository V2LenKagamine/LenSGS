package com.lensmods.lenssgs.api.data.materials;

import com.google.gson.JsonObject;
import com.lensmods.lenssgs.api.item.GunType;
import com.lensmods.lenssgs.api.material.IMaterialSerializer;
import com.lensmods.lenssgs.api.part.PartType;
import com.lensmods.lenssgs.core.guns.material.MaterialSerializer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

public class MaterialBuilder {
    private final ResourceLocation id;
    private final Ingredient ingredient;
    private boolean visible = true;
    private Collection<String> gunBlacklist = new ArrayList<>();
    private final Collection<ICondition> loadCons =new ArrayList<>();
    @Nullable private ResourceLocation parent;
    private MutableComponent name;
    private IMaterialSerializer<?> serializer = MaterialSerializer.STANDARD;

    private final Map<PartType, List<IGunTrait>> traits = new LinkedHashMap<>();


    public MaterialBuilder(ResourceLocation id, ResourceLocation ingTagName) {
        this(id,Ingredient.of(ItemTags.create(ingTagName)));
    }

    public MaterialBuilder(ResourceLocation id, TagKey<Item> ing) {
        this(id,Ingredient.of(ing));
    }

    public MaterialBuilder(ResourceLocation id, ItemLike... ings) {
        this(id,Ingredient.of(ings));
    }

    public MaterialBuilder(ResourceLocation id, Ingredient ing) {
        this.id = id;
        this.ingredient = ing;
        this.name = net.minecraft.network.chat.Component.translatable(String.format("material.%s.%s",
                this.id.getNamespace(),
                this.id.getPath().replace("/",".")));
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public MaterialBuilder type(IMaterialSerializer<?> serial) {
        this.serializer = serial;
        return this;
    }

    public MaterialBuilder loadConditionTagExists(ResourceLocation tagID) {
        return loadCond(new NotCondition(new TagEmptyCondition(tagID)));
    }
    public MaterialBuilder loadCond(ICondition cond) {
        this.loadCons.add(cond);
        return this;
    }
    public MaterialBuilder parent(ResourceLocation parent) {
        this.parent = parent;
        return this;
    }
    public MaterialBuilder visible(boolean visible) {
        this.visible = visible;
        return this;
    }
    public MaterialBuilder blacklistGunType(GunType type) {
        return blacklistGunType(type.getName());
    }
    public MaterialBuilder blacklistGunType(String type) {
        this.gunBlacklist.add(type);
        return this;
    }
    public MaterialBuilder name(MutableComponent text) {
        this.name = text;
        return this;
    }
    private void validate() {
        //todo:Models or smthn idk
    }

    public JsonObject serialize() {
        validate();
        JsonObject json = new JsonObject();
        json.addProperty("type",this.serializer.getName().toString());
        if (this.parent != null) {
            json.addProperty("parent",this.parent.toString());
        }
        JsonObject availability = new JsonObject();
        //TODO: This bs
        return json;
    }
}
