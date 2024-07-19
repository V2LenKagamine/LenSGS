package com.lensmods.lenssgs.init;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.recipes.GunRecipeSerializer;
import com.lensmods.lenssgs.core.data.recipes.RefillRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LenRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, LensSGS.MODID);
    public static final DeferredHolder<RecipeSerializer<?>, GunRecipeSerializer> GUN_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("gun_recipe",()->new GunRecipeSerializer());

    public static final DeferredHolder<RecipeSerializer<?>, RefillRecipeSerializer> REFILL_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("refill_recipe",()->new RefillRecipeSerializer());
}
