package com.lensmods.lenssgs.datagen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.data.recipes.GunRecipe;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.lensmods.lenssgs.init.LenItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class LenRecipeGen extends RecipeProvider {
    private HolderLookup.Provider prov;
    public LenRecipeGen(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
        try {
            prov = pRegistries.get();
        } catch (Exception e) {
            LensSGS.L3NLOGGER.error("RUH ROH RAGGY.DATAGEN BADNESS");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void buildRecipes(RecipeOutput out) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,LenItems.GUNPRINTER_PAPER,4)
                .pattern("apg")
                .pattern("pip")
                .pattern("gpa")
                .define('a',Items.ARROW)
                .define('i',Tags.Items.DYES_WHITE)
                .define('p',Items.PAPER)
                .define('g',Items.GUNPOWDER)
                .unlockedBy("paper",InventoryChangeTrigger.TriggerInstance.hasItems(Items.PAPER))
                .save(out);
        GunFromItems("make_gun",List.of(LenItems.PART_BASE.get(),LenItems.PART_BASE.get(),LenItems.PART_BASE.get()),LenItems.GUN_BASE.get(),out,false); //The gun
        GunFromItems("make_ammo",List.of(LenItems.PART_BASE.get(),LenItems.PART_BASE.get(),LenItems.PART_BASE.get()),LenItems.AMMO_BASE.get(),out,false); //The Ammo
        GunFromItems("swap_part_gun",List.of(LenItems.PART_BASE.get(),LenItems.GUN_BASE.get()),LenItems.GUN_BASE.get(),out,true);//Swap GunParts
        GunFromItems("swap_part_ammo",List.of(LenItems.PART_BASE.get(),LenItems.AMMO_BASE.get()),LenItems.AMMO_BASE.get(),out,true); //Swap Ammo parts
        GunFromIng("make_part", List.of(Ingredient.of(LenItems.PART_CRAFTER.get()),Ingredient.of(LenTagKeys.GUNAMMO_MAT_TAG)), LenItems.PART_BASE.get(), out,false); //Parts
        //Stocks
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"stock_crafter_short",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.STOCK),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.STOCK_SHORT))
                .pattern("ps")
                .pattern(" s")
                .define('p',LenItems.GUNPRINTER_PAPER)
                .define('s',Tags.Items.RODS_WOODEN)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"stock_crafter_full",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.STOCK),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.STOCK_FULL))
                .pattern("   ")
                .pattern("pps")
                .pattern("  s")
                .define('p',LenItems.GUNPRINTER_PAPER)
                .define('s',Tags.Items.RODS_WOODEN)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        //Barrels
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"barrel_crafter_stub",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.BARREL),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.BARREL_STUB))
                .pattern("   ")
                .pattern("ssp")
                .pattern("   ")
                .define('s',Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"barrel_crafter_short",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.BARREL),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.BARREL_SHORT))
                .pattern("   ")
                .pattern("spp")
                .pattern("   ")
                .define('s',Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"barrel_crafter_normal",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.BARREL),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.BARREL_STUB))
                .pattern("  s")
                .pattern("ppp")
                .pattern("  s")
                .define('s',Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"barrel_crafter_long",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.BARREL),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.BARREL_LONG))
                .pattern("   ")
                .pattern("ppp")
                .pattern(" sp")
                .define('s',Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"barrel_crafter_extend",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.BARREL),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.BARREL_EXTENDED))
                .pattern(" sp")
                .pattern("ppp")
                .pattern("ssp")
                .define('s',Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        //actions
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"single_action_crafter",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.ACTION),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.ACTION_SINGLE))
                .pattern("sp")
                .pattern("ps")
                .define('p', LenItems.GUNPRINTER_PAPER)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"manual_action_crafter",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.ACTION),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.ACTION_MANUAL))
                .pattern("sps")
                .pattern("p p")
                .pattern("psp")
                .define('p', LenItems.GUNPRINTER_PAPER)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"automatic_action_crafter",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.ACTION),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.ACTION_AUTOMATIC))
                .pattern(" ps")
                .pattern("ppp")
                .pattern("sss")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        //Receivers
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"receiver_crafter_pistol",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.RECEIVER),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.RECIEVER_PISTOL))
                .pattern("s  ")
                .pattern("ppp")
                .pattern("s p")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"receiver_crafter_standard",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.RECEIVER),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.RECIEVER_STANDARD))
                .pattern("ss ")
                .pattern("ppp")
                .pattern("psp")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"receiver_crafter_bull",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.RECEIVER),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.RECIEVER_BULLPUP))
                .pattern(" s ")
                .pattern("ppp")
                .pattern("p s")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        //Mags
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"mag_crafter_short",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.MAGAZINE),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.MAGAZINE_SHORT))
                .pattern(" p ")
                .pattern(" s ")
                .pattern(" s ")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"mag_crafter_normal",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.MAGAZINE),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.MAGAZINE_NORMAL))
                .pattern(" p ")
                .pattern(" p ")
                .pattern(" s ")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"mag_crafter_extended",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.MAGAZINE),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.MAGAZINE_EXTENDED))
                .pattern("sps")
                .pattern(" p ")
                .pattern(" p ")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"mag_crafter_belt",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.MAGAZINE),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.MAGAZINE_BELT))
                .pattern("ps ")
                .pattern("pss")
                .pattern(" sp")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        //Casings
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"casing_crafter_small",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.CASING),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.CASING_SMALL))
                .pattern("   ")
                .pattern("psp")
                .pattern("sps")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"casing_crafter_normal",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.CASING),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.CASING_NORMAL))
                .pattern("s s")
                .pattern("psp")
                .pattern("ppp")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"casing_crafter_large",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.CASING),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.CASING_LARGE))
                .pattern("psp")
                .pattern("psp")
                .pattern("ppp")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"casing_crafter_shell",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.CASING),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.CASING_SHELL))
                .pattern("psp")
                .pattern("psp")
                .pattern("sps")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        //Rounds
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"round_crafter_standard",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.ROUND),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.ROUND_STANDARD))
                .pattern(" s ")
                .pattern("sps")
                .pattern(" s ")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"round_crafter_buck",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.ROUND),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.ROUND_BUCKSHOT))
                .pattern("psp")
                .pattern("sps")
                .pattern("   ")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"round_crafter_bird",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.ROUND),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.ROUND_BIRDSHOT))
                .pattern(" sp")
                .pattern("sps")
                .pattern("ps ")
                .define('s', Tags.Items.RODS_WOODEN)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        //Propellant types
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"prop_crafter_light",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.PROPELLANT),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.PROPELLANT_LIGHT))
                .pattern(" s")
                .pattern(" p")
                .define('s', Tags.Items.GUNPOWDERS)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"prop_crafter_normal",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.PROPELLANT),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.PROPELLANT_NORMAL))
                .pattern("   ")
                .pattern("   ")
                .pattern("sps")
                .define('s', Tags.Items.GUNPOWDERS)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);
        DataShapedBuilder.betterShaped(RecipeCategory.MISC, new ItemStack(LenItems.PART_CRAFTER.get()),"prop_crafter_heavy",
                        Pair.of(LenDataComponents.PART_TYPE.get(),AllowedParts.PROPELLANT),
                        Pair.of(LenDataComponents.PART_SUB_TYPE.get(),AllowedParts.PROPELLANT_HEAVY))
                .pattern("   ")
                .pattern(" s ")
                .pattern("sps")
                .define('s', Tags.Items.GUNPOWDERS)
                .define('p', LenItems.GUNPRINTER_PAPER)
                .unlockedBy("gunpaper", InventoryChangeTrigger.TriggerInstance.hasItems(LenItems.GUNPRINTER_PAPER))
                .save(out);

    }

    protected void GunFromItems(String name, List<Item> ing, Item output, RecipeOutput out,boolean swap) {
        List<Ingredient> egg = new ArrayList<>();
        for (Item ingey : ing) {
            egg.add(Ingredient.of(ingey));
        }
        GunFromIng(name,egg,output,out,swap);
    }
    protected void GunFromIng(String name, List<Ingredient> ing, Item output, RecipeOutput out,boolean swap) {
        GunRecipe boi = GunRecipe.fromIngs(ing,output,swap);
        SpecialRecipeBuilder.special(category->boi).save(out,name);
    }
    public static class DataShapedBuilder extends ShapedRecipeBuilder {
        private final RecipeCategory category = RecipeCategory.COMBAT;
        private static ItemStack resultStack = null;
        private final String name;
        private final List<String> rows;
        private final Map<Character, Ingredient> key;
        private final Map<String, Criterion<?>> criteria;
        private final boolean showNotification;
        private String group;

        public DataShapedBuilder(RecipeCategory pCategory, ItemStack pResult, int pCount, String name) {
            super(pCategory, pResult.getItem(), pCount);
            this.name = name;
            this.rows = Lists.newArrayList();
            this.key = Maps.newLinkedHashMap();
            this.criteria = new LinkedHashMap();
            this.showNotification = true;
            this.resultStack = pResult;
        }
        public static DataShapedBuilder betterShaped(RecipeCategory categ, ItemStack stacc, String name, Pair<DataComponentType,String>... supp) {
            for(int i = 0;i<supp.length;i++) {
                stacc.set(supp[i].getFirst(),supp[i].getSecond());
            }
            return new DataShapedBuilder(categ,stacc,1,name);
        }
        @Override
        public DataShapedBuilder unlockedBy(String pName, Criterion<?> pCriterion) {
            this.criteria.put(pName, pCriterion);
            return this;
        }
        @Override
        public void save(RecipeOutput pRecipeOutput, ResourceLocation no) {
            ResourceLocation pId = ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,this.name);
            ShapedRecipePattern shapedrecipepattern = this.ensureValid(pId);
            Advancement.Builder advancement$builder = pRecipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId)).rewards(AdvancementRewards.Builder.recipe(pId)).requirements(AdvancementRequirements.Strategy.OR);
            Objects.requireNonNull(advancement$builder);
            this.criteria.forEach(advancement$builder::addCriterion);
            ShapedRecipe shapedrecipe = new ShapedRecipe((String)Objects.requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), shapedrecipepattern, this.resultStack, this.showNotification);
            pRecipeOutput.accept(pId, shapedrecipe, advancement$builder.build(pId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
        }
        private ShapedRecipePattern ensureValid(ResourceLocation pLocation) {
            if (this.criteria.isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(pLocation));
            } else {
                return ShapedRecipePattern.of(this.key, this.rows);
            }
        }

        public DataShapedBuilder define(Character pSymbol, TagKey<Item> pTag) {
            return this.define(pSymbol, Ingredient.of(pTag));
        }

        public DataShapedBuilder define(Character pSymbol, ItemLike pItem) {
            return this.define(pSymbol, Ingredient.of(new ItemLike[]{pItem}));
        }

        public DataShapedBuilder define(Character pSymbol, Ingredient pIngredient) {
            if (this.key.containsKey(pSymbol)) {
                throw new IllegalArgumentException("Symbol '" + pSymbol + "' is already defined!");
            } else if (pSymbol == ' ') {
                throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
            } else {
                this.key.put(pSymbol, pIngredient);
                return this;
            }
        }

        public DataShapedBuilder pattern(String pPattern) {
            if (!this.rows.isEmpty() && pPattern.length() != ((String)this.rows.get(0)).length()) {
                throw new IllegalArgumentException("Pattern must be the same width on every line!");
            } else {
                this.rows.add(pPattern);
                return this;
            }
        }
    }

}
