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

public class RefillRecipeSerializer implements RecipeSerializer<CraftingRecipe> {

    public static final MapCodec<RefillRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
            inst.group(
                    Ingredient.CODEC.listOf().fieldOf("inputs").forGetter(RefillRecipe::getInputs),
                    ItemStack.CODEC.fieldOf("output").forGetter(RefillRecipe::getOutput),
                    Codec.INT.fieldOf("toRestore").forGetter(RefillRecipe::getToRestore)
            ).apply(inst,RefillRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf,RefillRecipe> SCODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.collection(HashSet::new)),RefillRecipe::getInputs,
            ItemStack.STREAM_CODEC,RefillRecipe::getOutput,
            ByteBufCodecs.INT,RefillRecipe::getToRestore,
            RefillRecipe::new
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