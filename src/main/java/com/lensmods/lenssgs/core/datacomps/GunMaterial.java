package com.lensmods.lenssgs.core.datacomps;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.util.Color;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GunMaterial{
    private final String matName;
    private final List<StatMod> statModList;
    @Nullable private final List<TraitLevel> traitLevelList;
    private final Color color;
    private final Ingredient ingredient;
    private final List<String> blacklistedParts;

    public static final Codec<GunMaterial> GUN_MAT_CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.STRING.fieldOf("partType").forGetter(GunMaterial::getMatName),
                    StatMod.STAT_MOD_CODEC.listOf().fieldOf("statModList").forGetter(GunMaterial::getStatModList),
                    TraitLevel.TRAIT_LEVEL_CODEC.listOf().fieldOf("traitLevelList").forGetter(GunMaterial::getTraitLevelList),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter((GunMaterial::getIngredient)),
                    Color.CODEC.fieldOf("color").forGetter(GunMaterial::getColor),
                    Codec.STRING.listOf().fieldOf("allowedOn").forGetter(GunMaterial::getAllowedParts)
            ).apply(inst, GunMaterial::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, GunMaterial> GUN_MAT_SCODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, GunMaterial::getMatName,
            StatMod.STAT_MOD_STREAM_CODEC.apply(ByteBufCodecs.list()), GunMaterial::getStatModList,
            TraitLevel.TRAIT_LEVEL_STREAM_CODEC.apply(ByteBufCodecs.list()), GunMaterial::getTraitLevelList,
            Ingredient.CONTENTS_STREAM_CODEC,GunMaterial::getIngredient,
            Color.STREAM_CODEC,GunMaterial::getColor,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),GunMaterial::getAllowedParts,
            GunMaterial::new
    );

    public GunMaterial(String matName, List<StatMod> statMods, @Nullable List<TraitLevel> traitLevels, Ingredient ing, Color color, List<String> blacklistedParts) {
        this.matName = matName;
        this.statModList = statMods;
        this.traitLevelList = traitLevels != null ? traitLevels : new ArrayList<>();
        this.color = color;
        this.ingredient = ing;
        this.blacklistedParts = blacklistedParts;
    }
    public GunMaterial(String matName, List<StatMod> statMods, @Nullable List<TraitLevel> traitLevels, TagKey<Item> ing, Color color, List<String> blacklistedParts) {
        this(matName,statMods,traitLevels,Ingredient.of(ing),color,blacklistedParts);
    }
    public GunMaterial(String matName, List<StatMod> statMods, @Nullable List<TraitLevel> traitLevels, TagKey<Item> ing, Color color, String... blacklistedParts) {
        this(matName,statMods,traitLevels,Ingredient.of(ing),color, Arrays.stream(blacklistedParts).toList());
    }
    public MutableComponent getHumanName() {
        return Component.translatable(LensSGS.MODID + "." +this.matName);
    }
    public String getMatName() {return this.matName;}
    public List<StatMod> getStatModList() {return this.statModList;}
    public List<TraitLevel> getTraitLevelList() {return this.traitLevelList;}
    public Ingredient getIngredient() {
        return ingredient;
    }
    public Color getColor() {
        return color;
    }
    public List<String> getAllowedParts(){return blacklistedParts.isEmpty()? List.of("all"): blacklistedParts;}
    @Override
    public int hashCode() {
        return Objects.hash(this.matName,this.traitLevelList,this.traitLevelList);
    }
    @Override public boolean equals(Object obj) {
        if(obj == this) {return true;}
        else {
            return obj instanceof GunMaterial egg && matName == egg.matName && statModList.equals(egg.statModList) && traitLevelList.equals(traitLevelList);
        }
    }
}
