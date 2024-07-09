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
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GunMaterial {
    private final String matName;
    private final List<StatMod> statModList;
    private final List<TraitLevel> traitLevelList;
    private final Color color;
    private final Ingredient ingredient;

    public static final Codec<GunMaterial> GUN_MAT_CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.STRING.fieldOf("partType").forGetter(GunMaterial::getMatName),
                    StatMod.STAT_MOD_CODEC.listOf().fieldOf("statModList").forGetter(GunMaterial::getStatModList),
                    TraitLevel.TRAIT_LEVEL_CODEC.listOf().fieldOf("traitLevelList").forGetter(GunMaterial::getTraitLevelList),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter((GunMaterial::getIngredient)),
                    Color.CODEC.fieldOf("color").forGetter(GunMaterial::getColor)
            ).apply(inst, GunMaterial::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, GunMaterial> GUN_MAT_SCODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, GunMaterial::getMatName,
            StatMod.STAT_MOD_STREAM_CODEC.apply(ByteBufCodecs.collection(HashSet::new)), GunMaterial::getStatModList,
            TraitLevel.TRAIT_LEVEL_STREAM_CODEC.apply(ByteBufCodecs.collection(HashSet::new)), GunMaterial::getTraitLevelList,
            Ingredient.CONTENTS_STREAM_CODEC,GunMaterial::getIngredient,
            Color.STREAM_CODEC,GunMaterial::getColor,
            GunMaterial::new
    );

    public GunMaterial(String matName, List<StatMod> statMods, List<TraitLevel> traitLevels, Ingredient ing, Color color) {
        this.matName = matName;
        this.statModList = statMods;
        this.traitLevelList = traitLevels != null ? traitLevels : new ArrayList<>();
        this.color = color;
        this.ingredient = ing;
    }

    public GunMaterial(String matName, List<StatMod> statMods, Ingredient ing, @Nullable List<TraitLevel> traits) {
        this(matName,statMods,traits,ing, Color.HOTPINK);
    }
    public GunMaterial(String matName, List<StatMod> statMods, ItemLike ing, @Nullable List<TraitLevel> traits) {
        this(matName,statMods,new ArrayList<>(),Ingredient.of(ing),Color.HOTPINK);
    }
    public GunMaterial(String matName, List<StatMod> statMods, TagKey<Item> ing, @Nullable List<TraitLevel> traits, Color color) {
        this(matName,statMods,new ArrayList<>(),Ingredient.of(ing), color);
    }

    public GunMaterial(String s, Collection<StatMod> statMods, Collection<TraitLevel> traitLevels, Ingredient ingredient,Color color) {
        this(s,statMods.stream().toList(),traitLevels.stream().toList(),ingredient, color);
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
