package com.lensmods.lenssgs.core.data.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.HashSet;

public class GunRecipeSerializer implements RecipeSerializer<CraftingRecipe>{

    public static final MapCodec<GunRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
            inst.group(
                    Ingredient.CODEC.listOf().fieldOf("inputs").forGetter(GunRecipe::getInputs),
                    ItemStack.CODEC.fieldOf("output").forGetter(GunRecipe::getOutput),
                    Codec.BOOL.fieldOf("swapping").forGetter(GunRecipe::getSwapping)
            ).apply(inst,GunRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf,GunRecipe> SCODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.collection(HashSet::new)),GunRecipe::getInputs,
            ItemStack.STREAM_CODEC,GunRecipe::getOutput,
            ByteBufCodecs.BOOL,GunRecipe::getSwapping,
            GunRecipe::new
    );
    @Override
    public MapCodec codec() {
        return CODEC;
    }

    @Override
    public StreamCodec streamCodec() {
        return SCODEC;
    }
}
