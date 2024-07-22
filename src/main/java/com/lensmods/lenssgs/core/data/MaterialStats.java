package com.lensmods.lenssgs.core.data;

import com.lensmods.lenssgs.core.datacomps.StatMod;
import com.lensmods.lenssgs.core.datacomps.TraitLevel;

import java.util.List;

import static com.lensmods.lenssgs.core.data.AllowedParts.*;

public final class MaterialStats {
    //STAT KEYS
    public static final String AMMO_MAX = "stat_ammo_max";
    public static final String PIERCE = "stat_pierce";
    public static final String FIRE_RATE="stat_fire_rate";
    public static final String PROJ_COUNT="stat_projectile_count";
    public static final String MAX_DMG="stat_max_damage";
    public static final String MIN_DMG="stat_min_damage";
    public static final String GRAVITY_MOD ="stat_gravity_mod";
    public static final String VEL_MULT = "stat_velocity_mul";
    public static final String INACCURACY_DEG = "stat_inaccuracy";

    //TRAIT KEYS
    public static final String BLAZING = "blazing";
    public static final String ECOLOGICAL = "eco_friendly";
    public static final String SHOCKING = "shocking";
    public static final String SHATTERING = "shattering";
    public static final String TRACING = "tracing";
    public static final String LACERATING = "lacerating";
    public static final String BLINDING = "blinding";
    public static final String EXPLOSIVE = "explosive";
    public static final String CONCUSSIVE = "concussive";
    public static final String VOID_TOUCHED = "voidtouched";
    public static final String LINGERING = "lingering";
    public static final String HALLOW_POINT = "hallowpoint";

    //STAT MOD MODE
    public static final String MUL_TOTAL = "total_multiply"; // Add all together, multiply final product by this number.
    public static final String AVG_MUL = "multiply"; // Add all together, multiply by that avg
    public static final String ADD = "add"; //adds to base stat.

    public static final List<StatMod> WOOD_STATS = List.of(
            new StatMod(MIN_DMG,-0.95F,MUL_TOTAL,ROUND),
            new StatMod(MAX_DMG,-0.95F,MUL_TOTAL,ROUND),
            new StatMod(INACCURACY_DEG, -0.5f,ADD,STOCK)
    );
    public static final List<TraitLevel> WOOD_TRAITS = List.of(
            new TraitLevel(ECOLOGICAL,10,ROUND)
    );

    public static final List<StatMod> STONE_STATS = List.of(
            new StatMod(MAX_DMG,-1f,ADD,CASING),
            new StatMod(MIN_DMG,-1f,ADD,CASING),
            new StatMod(MAX_DMG,-0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MAX_DMG,0.9f,AVG_MUL,AFFECTS_DMG_MOD),
            new StatMod(MIN_DMG,0.8f,AVG_MUL,AFFECTS_DMG_MOD),
            new StatMod(VEL_MULT,-0.0125f,ADD,AFFECTS_VEL),
            new StatMod(FIRE_RATE,2.5f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(PROJ_COUNT, -1f,ADD,ROUND),
            new StatMod(AMMO_MAX,0.5f, AVG_MUL,MAGAZINE),
            new StatMod(INACCURACY_DEG,2f,ADD,BARREL,ROUND,RECEIVER),
            new StatMod(GRAVITY_MOD, 0.0025f,ADD,ROUND),
            new StatMod(INACCURACY_DEG, -0.75f,ADD,STOCK)
    );

    public static final List<StatMod> COPPER_STATS = List.of(
            new StatMod(AMMO_MAX,0.75f, AVG_MUL,MAGAZINE),
            new StatMod(PIERCE,-1F,ADD,ROUND),
            new StatMod(MAX_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,-0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(FIRE_RATE,0.95f, AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,0.5f,ADD,AFFECTS_BASE_DMG),
            new StatMod(VEL_MULT,0.9f, AVG_MUL,ROUND),
            new StatMod(FIRE_RATE,-0.5f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(INACCURACY_DEG,2.5f,ADD,BARREL,RECEIVER),
            new StatMod(INACCURACY_DEG,-0.5f,ADD,STOCK,BARREL),
            new StatMod(MIN_DMG,-0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,-0.25f,ADD,BARREL)
    );
    public static final List<TraitLevel> COPPER_TRAITS= List.of(
            new TraitLevel(SHOCKING,1,CASING,ROUND,RECEIVER)
    );
    public static final List<StatMod> IRON_STATS = List.of(
            new StatMod(AMMO_MAX,0.9f, AVG_MUL, CASING),
            new StatMod(FIRE_RATE,1.05f, AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,0.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MAX_DMG,1.75F,ADD,AFFECTS_BASE_DMG),
            new StatMod(VEL_MULT,0.9f, AVG_MUL,ROUND),
            new StatMod(GRAVITY_MOD,1.1f, AVG_MUL,ROUND),
            new StatMod(FIRE_RATE,0.75f,ADD,MAGAZINE,RECEIVER),
            new StatMod(FIRE_RATE,-0.75F,ADD,BARREL,STOCK),
            new StatMod(MIN_DMG,0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,0.5f,ADD,BARREL)
    );
    public static final List<StatMod> GOLD_STATS = List.of(
            new StatMod(AMMO_MAX,0.6f, AVG_MUL,MAGAZINE),
            new StatMod(MAX_DMG,-0.5f,ADD,ROUND,CASING),
            new StatMod(MAX_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,-0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.75f,ADD,ROUND,RECEIVER),
            new StatMod(INACCURACY_DEG, 1.5f,ADD,BARREL,ACTION),
            new StatMod(FIRE_RATE, -2.5f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(PIERCE,-0.5f,ADD,ROUND),
            new StatMod(MIN_DMG,-0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,-0.25f,ADD,BARREL)
    );
    public static final List<StatMod> DIAMOND_STATS = List.of(
            new StatMod(AMMO_MAX,1.1f, AVG_MUL,MAGAZINE),
            new StatMod(PIERCE,1f,ADD,ROUND),
            new StatMod(MAX_DMG,1.25f,ADD,ROUND,ACTION),
            new StatMod(MIN_DMG,1.75f,ADD,ROUND,ACTION),
            new StatMod(MAX_DMG,0.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,1f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(VEL_MULT,0.75f,AVG_MUL,AFFECTS_VEL),
            new StatMod(FIRE_RATE,3f,ADD,ACTION,STOCK),
            new StatMod(PROJ_COUNT, -2f,ADD,ROUND),
            new StatMod(INACCURACY_DEG, -3f,ADD,STOCK,RECEIVER),
            new StatMod(INACCURACY_DEG,0.75f, AVG_MUL,ROUND),
            new StatMod(MIN_DMG,0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,0.5f,ADD,BARREL),
            new StatMod(INACCURACY_DEG,-0.25f,ADD,BARREL)
    );

    public static final List<TraitLevel>DIAMOND_TRAITS = List.of(
            new TraitLevel(BLINDING,1,ROUND),
            new TraitLevel(LACERATING,1,ROUND)
    );

    public static final List<StatMod> EMERALD_STATS = List.of(
            new StatMod(AMMO_MAX,1.1f, AVG_MUL,MAGAZINE),
            new StatMod(PIERCE,0.5f,ADD,ROUND),
            new StatMod(MAX_DMG,2.5f,ADD,ROUND,ACTION),
            new StatMod(MAX_DMG,1f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,-0.75f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(VEL_MULT,0.75f,AVG_MUL,AFFECTS_VEL),
            new StatMod(FIRE_RATE,2f,ADD,ACTION,STOCK),
            new StatMod(PROJ_COUNT, -2f,ADD,ROUND),
            new StatMod(INACCURACY_DEG, -2.5f,ADD,STOCK,RECEIVER),
            new StatMod(INACCURACY_DEG,1.25f, AVG_MUL,ROUND),
            new StatMod(MIN_DMG,-0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,0.75f,ADD,BARREL),
            new StatMod(INACCURACY_DEG,0.5f,ADD,BARREL)
    );
    public static final List<TraitLevel>EMERALD_TRAITS = List.of(
            new TraitLevel(LACERATING,2,ROUND)
    );

    public static final List<StatMod> NETHERITE_STATS = List.of(
            new StatMod(AMMO_MAX,1.5f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,0.8f, AVG_MUL, CASING),
            new StatMod(FIRE_RATE,1.25F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,1.05F,AVG_MUL,ACTION,RECEIVER,ROUND,CASING),
            new StatMod(MAX_DMG,1.05F,AVG_MUL,ACTION,RECEIVER,ROUND,CASING),
            new StatMod(INACCURACY_DEG,0.8F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(PIERCE,3f,ADD,ROUND),
            new StatMod(MAX_DMG,1.5f,ADD,ROUND),
            new StatMod(MIN_DMG,2.75f,ADD,ROUND),
            new StatMod(MAX_DMG,1f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.75f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, -2.5f,ADD,BARREL),
            new StatMod(PROJ_COUNT,-0.2f, MUL_TOTAL,ROUND),
            new StatMod(MIN_DMG,0.5f,ADD,BARREL),
            new StatMod(MAX_DMG,0.5f,ADD,BARREL)
    );

    public static final List<StatMod> VOID_STATS = List.of(
            new StatMod(AMMO_MAX,1.75f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,0.75f, AVG_MUL, CASING),
            new StatMod(FIRE_RATE,1.2F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,1.1F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(MAX_DMG,1.1F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(INACCURACY_DEG,0.9F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(PIERCE,-0.95f,MUL_TOTAL,ROUND,CASING),
            new StatMod(PROJ_COUNT,-0.95f,MUL_TOTAL,ROUND),
            new StatMod(MAX_DMG,3.5f,ADD,ROUND),
            new StatMod(MIN_DMG,2.5f,ADD,ROUND),
            new StatMod(FIRE_RATE,12f,ADD,ROUND),
            new StatMod(MAX_DMG,1.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.75f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, -3f,ADD,BARREL),
            new StatMod(MIN_DMG,0.75f,ADD,BARREL),
            new StatMod(MAX_DMG,0.5f,ADD,BARREL)
    );

    public static final List<TraitLevel> VOID_TRAITS = List.of(
            new TraitLevel(VOID_TOUCHED,2,ROUND)
    );

    public static final List<StatMod> WYRM_STATS = List.of(
            new StatMod(AMMO_MAX,1.5f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,0.5f, AVG_MUL, CASING),
            new StatMod(FIRE_RATE,1.3F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,1.2F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(MAX_DMG,1.2F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(INACCURACY_DEG,1.1F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(PIERCE,-0.95f,MUL_TOTAL,ROUND),
            new StatMod(MAX_DMG,2.5f,ADD,ROUND),
            new StatMod(MIN_DMG,2.75f,ADD,ROUND),
            new StatMod(MAX_DMG,1.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,1.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, 3f,ADD,BARREL),
            new StatMod(MIN_DMG,1.25f,ADD,BARREL),
            new StatMod(MAX_DMG,1.5f,ADD,BARREL)
    );

    public static final List<TraitLevel> WYRM_TRAITS = List.of(
            new TraitLevel(LINGERING,1,ROUND),
            new TraitLevel(SHATTERING,1,ROUND),
            new TraitLevel(LINGERING,1,ANY_BULLET_PART),
            new TraitLevel(SHATTERING,1,ANY_BULLET_PART),
            new TraitLevel(BLAZING,1,ANY_BULLET_PART),
            new TraitLevel(BLAZING,1)
    );

    public static final List<StatMod> BLITZ_STATS = List.of(
            new StatMod(AMMO_MAX,1.25f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,1.25f, AVG_MUL,CASING),
            new StatMod(FIRE_RATE, -2.5f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(FIRE_RATE,-0.2F,MUL_TOTAL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,0.95F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,0.95F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(INACCURACY_DEG,1.1F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(MAX_DMG,1.5f,ADD,ROUND),
            new StatMod(MIN_DMG,1f,ADD,ROUND),
            new StatMod(MAX_DMG,0.75f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, -1f,ADD,BARREL),
            new StatMod(MIN_DMG,0.5f,ADD,BARREL),
            new StatMod(MAX_DMG,0.75f,ADD,BARREL)
    );

    public static final List<TraitLevel> BLITZ_TRAITS = List.of(
            new TraitLevel(SHOCKING,1,ROUND),
            new TraitLevel(SHOCKING,1,ANY_BULLET_PART),
            new TraitLevel(SHOCKING,1)
    );

    public static final List<StatMod> GUNPOWDER_STATS = List.of(
            new StatMod(AMMO_MAX,1f, AVG_MUL,PROPELLANT) //This is here because I am too lazy to check that it wont throw a fit at an empty list.
    );
    public static final List<StatMod> TNT_STATS =List.of(
            new StatMod(AMMO_MAX,-0.5f,MUL_TOTAL,PROPELLANT),
            new StatMod(MIN_DMG,0.15f,MUL_TOTAL,PROPELLANT),
            new StatMod(MAX_DMG,0.15f,MUL_TOTAL,PROPELLANT),
            new StatMod(INACCURACY_DEG,0.75f,MUL_TOTAL,PROPELLANT),
            new StatMod(VEL_MULT,0.5f,MUL_TOTAL,PROPELLANT)
    );
    public static final List<TraitLevel> TNT_TRAITS = List.of(
            new TraitLevel(EXPLOSIVE,1)
    );
    public static final List<StatMod> PRISMARINE_STATS = List.of(
            new StatMod(PROJ_COUNT,2F,ADD,ROUND),
            new StatMod(MIN_DMG,0.75f,ADD,ROUND),
            new StatMod(MAX_DMG,1.25f,ADD,ROUND)
    );
    public static final List<TraitLevel> PRISMARINE_TRAITS = List.of(
            new TraitLevel(SHATTERING,2),
            new TraitLevel(LACERATING,1)
    );

    public static final List<StatMod> PRISMARINE_CRYSTAL_STATS = List.of(
            new StatMod(PROJ_COUNT,1F,ADD,ROUND),
            new StatMod(MAX_DMG,1.25f,ADD,ROUND)
    );

    public static final List<TraitLevel> PRISMARINE_CRYSTAL_TRAITS = List.of(
            new TraitLevel(SHATTERING,1),
            new TraitLevel(TRACING,1),
            new TraitLevel(BLINDING,1)
    );

    public static final List<StatMod> SLIME_STATS = List.of(
            new StatMod(MAX_DMG,0.25f,MUL_TOTAL,ROUND),
            new StatMod(MIN_DMG,0.25f,MUL_TOTAL,ROUND),
            new StatMod(INACCURACY_DEG,-1f,ADD,STOCK)
    );
    public static final List<TraitLevel> SLIME_TRAITS = List.of(
            new TraitLevel(CONCUSSIVE,1,ROUND)
    );

    public static final List<StatMod> MAGMA_STATS = List.of(
            new StatMod(MAX_DMG,0.5F,MUL_TOTAL,ROUND),
            new StatMod(MIN_DMG,0.5F,MUL_TOTAL,ROUND),
            new StatMod(INACCURACY_DEG,-0.5f,ADD,STOCK)
    );
    public static final List<TraitLevel> MAGMA_TRAITS = List.of(
            new TraitLevel(CONCUSSIVE,1,ROUND),
            new TraitLevel(BLAZING,1,ROUND,STOCK)
    );

    public static final List<StatMod> BLAZEROD_STATS =List.of(
            new StatMod(MIN_DMG,0.5f,ADD,BARREL),
            new StatMod(MIN_DMG, 0.5f,ADD,ROUND,PROPELLANT)
    );
    public static final List<TraitLevel> BLAZEROD_TRAITS = List.of(
            new TraitLevel(BLAZING,1)
    );

    public static final List<StatMod> QUARTZ_STATS = List.of(
            new StatMod(MIN_DMG,1f,ADD,ROUND),
            new StatMod(MAX_DMG,1f,ADD,ROUND),
            new StatMod(PROJ_COUNT,2f,ADD,ROUND)
    );
    public static final List<TraitLevel> QUARTZ_TRAITS = List.of(
            new TraitLevel(SHATTERING,1),
            new TraitLevel(LACERATING,1)
    );

    public static final List<StatMod> BREEZE_ROD_STATS = List.of(
            new StatMod(GRAVITY_MOD,-0.006f,ADD,BARREL,ROUND),
            new StatMod(VEL_MULT,0.02f,ADD,BARREL,ROUND,PROPELLANT),
            new StatMod(MIN_DMG,1.5f,ADD,BARREL,ROUND,PROPELLANT),
            new StatMod(MAX_DMG,1.75f,ADD,BARREL,ROUND,PROPELLANT)
    );
    public static final List<TraitLevel> BREEZE_ROD_TRAITS = List.of(
            new TraitLevel(CONCUSSIVE,3)
    );
    public static final List<StatMod> AMETHYST_STATS = List.of(
            new StatMod(PROJ_COUNT,3f,ADD,ROUND),
            new StatMod(PROJ_COUNT,0.25f,MUL_TOTAL,ROUND),
            new StatMod(MAX_DMG,-1f,ADD,ROUND),
            new StatMod(MIN_DMG,-0.5f,ADD,ROUND)
    );
    public static final List<TraitLevel> AMETHYST_TRAITS = List.of(
            new TraitLevel(SHATTERING,2),
            new TraitLevel(LACERATING,1)
    );
    public static final List<StatMod> DRAGONS_BREATH_STATS = List.of(
            new StatMod(MIN_DMG,1.5f,ADD,ROUND),
            new StatMod(MAX_DMG,1.75f,ADD,ROUND),
            new StatMod(AMMO_MAX,1.1F,AVG_MUL,ROUND),
            new StatMod(INACCURACY_DEG,0.5f,ADD,ROUND)
    );
    public static final List<TraitLevel> DRAGONS_BREATH_TRAITS = List.of(
            new TraitLevel(LINGERING,1),
            new TraitLevel(BLAZING,3)
    );

    public static final List<StatMod> END_ROD_STATS = List.of(
            new StatMod(MIN_DMG,3.5f,ADD,ROUND),
            new StatMod(MAX_DMG,4.25f,ADD,ROUND),
            new StatMod(INACCURACY_DEG,-4.5f,ADD,ROUND),
            new StatMod(PIERCE,3f,ADD,ROUND),
            new StatMod(VEL_MULT,0.012f,ADD,ROUND),
            new StatMod(FIRE_RATE,3.5f,ADD,ROUND),
            new StatMod(PROJ_COUNT,-0.95f,MUL_TOTAL,ROUND),
            new StatMod(GRAVITY_MOD,-0.9f,MUL_TOTAL,ROUND,BARREL),
            new StatMod(MIN_DMG,1.25f,ADD,BARREL),
            new StatMod(MAX_DMG,1.5f,ADD,BARREL),
            new StatMod(INACCURACY_DEG,-1.25f,ADD,BARREL)
    );

    public static final List<TraitLevel> END_ROD_TRAITS = List.of(
            new TraitLevel(TRACING,1),
            new TraitLevel(SHATTERING,3,ROUND)
    );

    public static final List<StatMod> COPPER_ROD_STATS = List.of(
            new StatMod(MIN_DMG,3.75f,ADD,ROUND),
            new StatMod(MAX_DMG,3.25f,ADD,ROUND),
            new StatMod(INACCURACY_DEG,-3f,ADD,ROUND),
            new StatMod(FIRE_RATE,4f,ADD,ROUND),
            new StatMod(PIERCE,2f,ADD,ROUND),
            new StatMod(VEL_MULT,0.008f,ADD,ROUND),
            new StatMod(PROJ_COUNT,-0.95f,MUL_TOTAL,ROUND)
    );

    public static final List<TraitLevel> COPPER_ROD_TRAITS = List.of(
            new TraitLevel(SHOCKING,4),
            new TraitLevel(SHATTERING,2,ROUND)
    );

    public static final List<StatMod> NETHER_STAR_STATS = List.of(
            new StatMod(MIN_DMG,0.75f,ADD,ROUND),
            new StatMod(MAX_DMG,0.75f,ADD,ROUND),
            new StatMod(INACCURACY_DEG,7.5F,ADD,ROUND,PROPELLANT),
            new StatMod(PROJ_COUNT,5f,ADD,ROUND,PROPELLANT),
            new StatMod(MIN_DMG,1.3f,AVG_MUL,PROPELLANT),
            new StatMod(MAX_DMG,1.3f,AVG_MUL,PROPELLANT)
    );
    public static final List<TraitLevel> NETHER_STAR_TRAITS = List.of(
            new TraitLevel(SHATTERING,1,ROUND),
            new TraitLevel(SHOCKING,1,ROUND),
            new TraitLevel(TRACING,1,ROUND),
            new TraitLevel(SHATTERING,1),
            new TraitLevel(SHOCKING,1),
            new TraitLevel(TRACING,1)
    );

    //Modded mats support

    public static final List<StatMod> TIN_STATS = List.of(
            new StatMod(AMMO_MAX,0.75f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,1.05f, AVG_MUL, CASING),
            new StatMod(FIRE_RATE,1.05F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,0.8F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,0.8F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,-0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,-0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(PIERCE,0.8f,AVG_MUL,ROUND,CASING),
            new StatMod(MAX_DMG,-0.75f,ADD,ROUND),
            new StatMod(MIN_DMG,-0.75f,ADD,ROUND),
            new StatMod(MIN_DMG,-0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,-0.25f,ADD,BARREL),
            new StatMod(INACCURACY_DEG, 1.5f,ADD,BARREL)
    );

    public static final List<StatMod> BRONZE_STATS = List.of(
            new StatMod(AMMO_MAX,1.1f, AVG_MUL,MAGAZINE),
            new StatMod(FIRE_RATE,1.2F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,-0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MAX_DMG,0.5f,ADD,ROUND),
            new StatMod(MIN_DMG,0.5f,ADD,ROUND),
            new StatMod(MIN_DMG,0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,0.25f,ADD,BARREL),
            new StatMod(INACCURACY_DEG, -1f,ADD,BARREL,STOCK,RECEIVER)
    );

    public static final List<StatMod> STEEL_STATS = List.of(
            new StatMod(AMMO_MAX,1.2f, AVG_MUL,MAGAZINE),
            new StatMod(FIRE_RATE,0.9F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,1.05F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(MAX_DMG,1.05F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(INACCURACY_DEG,0.9F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(MAX_DMG,1f,ADD,ROUND),
            new StatMod(MIN_DMG,1f,ADD,ROUND),
            new StatMod(MAX_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,-0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,0.25f,ADD,BARREL),
            new StatMod(INACCURACY_DEG, -1.5f,ADD,BARREL,RECEIVER,STOCK)
    );

    public static final List<StatMod> FISH_STATS = List.of(
            new StatMod(INACCURACY_DEG, 5f,ADD,ROUND),
            new StatMod(MIN_DMG,-2F,ADD,ROUND),
            new StatMod(MAX_DMG,-3f,ADD,ROUND)
    );

    public static final List<TraitLevel> FISH_TRAITS = List.of(
            new TraitLevel(CONCUSSIVE,5,ROUND)
    );

    public static final List<StatMod> FIRE_CHARGE_STATS = List.of(
            new StatMod(INACCURACY_DEG, 2f,ADD,ROUND),
            new StatMod(MIN_DMG,0.5F,ADD,ROUND),
            new StatMod(MAX_DMG,0.5f,ADD,ROUND)
    );

    public static final List<TraitLevel> FIRE_CHARGE_TRAITS = List.of(
            new TraitLevel(BLAZING,10,ROUND)
    );

    public static final List<StatMod> LEAD_STATS = List.of(
            new StatMod(AMMO_MAX,0.8f, AVG_MUL,MAGAZINE),
            new StatMod(INACCURACY_DEG,0.9F,AVG_MUL,ROUND),
            new StatMod(PIERCE,1.05f,AVG_MUL,ROUND,CASING),
            new StatMod(PROJ_COUNT,0.05f,MUL_TOTAL,ROUND),
            new StatMod(MAX_DMG,1f,ADD,ROUND),
            new StatMod(MIN_DMG,1f,ADD,ROUND),
            new StatMod(MAX_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(GRAVITY_MOD, 0.003f,ADD,ROUND),
            new StatMod(MIN_DMG,-0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,-0.5f,ADD,BARREL),
            new StatMod(INACCURACY_DEG,1.5F,ADD,BARREL)
    );

    public static final List<StatMod> REF_OBSIDIAN_STATS =List.of(
            new StatMod(AMMO_MAX,1.5f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,0.5f, AVG_MUL, CASING),
            new StatMod(FIRE_RATE,1.3F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,1.05F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(MAX_DMG,1.05F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(INACCURACY_DEG,0.95F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(PIERCE,-0.95f,MUL_TOTAL,ROUND,CASING),
            new StatMod(PROJ_COUNT,-0.95f,MUL_TOTAL,ROUND),
            new StatMod(MAX_DMG,2.25f,ADD,ROUND),
            new StatMod(MIN_DMG,1.25f,ADD,ROUND),
            new StatMod(FIRE_RATE,12.5f,ADD,ROUND),
            new StatMod(MAX_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,-0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, -2.5f,ADD,BARREL),
            new StatMod(MIN_DMG,0.55f,ADD,BARREL),
            new StatMod(MAX_DMG,0.75f,ADD,BARREL)
    );
    public static final List<TraitLevel> REF_OBSIDIAN_TRAITS =List.of(
            new TraitLevel(VOID_TOUCHED,1,ROUND),
            new TraitLevel(SHATTERING,2,ROUND)
    );

    public static final List<StatMod> REF_GLOWSTONE_STATS = List.of(
            new StatMod(AMMO_MAX,1.2f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,1.2f, AVG_MUL,CASING),
            new StatMod(FIRE_RATE, -2f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(FIRE_RATE,-0.15F,MUL_TOTAL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,0.9F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,0.9F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(INACCURACY_DEG,1.2F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(MAX_DMG,1.25f,ADD,ROUND),
            new StatMod(MIN_DMG,1.75f,ADD,ROUND),
            new StatMod(MAX_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,-0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, -0.5f,ADD,BARREL),
            new StatMod(MIN_DMG,1.25f,ADD,BARREL),
            new StatMod(MAX_DMG,1.5f,ADD,BARREL)
    );

    public static final List<TraitLevel> REF_GLOWSTONE_TRAITS = List.of(
        new TraitLevel(TRACING,2,ROUND),
        new TraitLevel(TRACING,1,BARREL,RECEIVER,ACTION)
    );

    public static final List<StatMod> OSMIUM_STATS = List.of(
            new StatMod(AMMO_MAX,1.15f, AVG_MUL,MAGAZINE),
            new StatMod(FIRE_RATE,0.9F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(INACCURACY_DEG,0.95F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(MAX_DMG,0.75f,ADD,ROUND),
            new StatMod(MIN_DMG,0.75f,ADD,ROUND),
            new StatMod(MAX_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,-0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, -1.25f,ADD,BARREL,RECEIVER,STOCK),
            new StatMod(MIN_DMG,0.5f,ADD,BARREL),
            new StatMod(MAX_DMG,0.5f,ADD,BARREL)
    );

    public static final List<StatMod> URANIUM_STATS = List.of(
            new StatMod(AMMO_MAX,0.9f, AVG_MUL,MAGAZINE),
            new StatMod(FIRE_RATE, 2f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,0.9F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(MAX_DMG,0.9F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(INACCURACY_DEG,1.25F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(MAX_DMG,1.5f,ADD,ROUND),
            new StatMod(MIN_DMG,1f,ADD,ROUND),
            new StatMod(MAX_DMG,0.75f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(PROJ_COUNT,1.75f,ADD,ROUND),
            new StatMod(INACCURACY_DEG, -0.5f,ADD,BARREL),
            new StatMod(MIN_DMG,0.5f,ADD,BARREL),
            new StatMod(MAX_DMG,0.75f,ADD,BARREL)
    );

    public static final List<TraitLevel> URANIUM_TRAITS = List.of(
            new TraitLevel(SHATTERING,6,ROUND),
            new TraitLevel(LACERATING,2,ROUND),
            new TraitLevel(LINGERING,2,ROUND)
    );

    public static final List<StatMod> ALUMINUM_STATS = List.of(
            new StatMod(AMMO_MAX,1.25f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,1.25f, AVG_MUL,CASING),
            new StatMod(FIRE_RATE, -1f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,0.8F,AVG_MUL,ROUND,CASING),
            new StatMod(MAX_DMG,0.8F,AVG_MUL,ROUND,CASING),
            new StatMod(INACCURACY_DEG,0.75F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(MAX_DMG,-0.25f,ADD,ROUND),
            new StatMod(MIN_DMG,0.25f,ADD,ROUND),
            new StatMod(MAX_DMG,1.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,1.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, -4.5f,ADD,BARREL,STOCK),
            new StatMod(MIN_DMG,-0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,-0.5f,ADD,BARREL)
    );

    public static final List<StatMod> ELECTRUM_STATS = List.of(
            new StatMod(AMMO_MAX,1.1f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,0.8f, AVG_MUL,CASING),
            new StatMod(FIRE_RATE, -0.75f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(FIRE_RATE,-0.05F,MUL_TOTAL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,0.25F,AVG_MUL,ACTION,ROUND,CASING),
            new StatMod(MAX_DMG,0.25F,AVG_MUL,ACTION,ROUND,CASING),
            new StatMod(MAX_DMG,0.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MAX_DMG,-0.25f,ADD,ROUND),
            new StatMod(MIN_DMG,0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,0.25f,ADD,BARREL)
    );

    public static final List<TraitLevel> ELECTRUM_TRAITS = List.of(
            new TraitLevel(SHOCKING,3,ROUND),
            new TraitLevel(SHOCKING,2,CASING),
            new TraitLevel(SHOCKING,1,STOCK,ACTION,RECEIVER)
    );

    public static final List<StatMod> SILVER_STATS = List.of(
            new StatMod(AMMO_MAX,0.75f, AVG_MUL,MAGAZINE),
            new StatMod(FIRE_RATE, -2f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,-0.75f,ADD,ROUND),
            new StatMod(MIN_DMG,-0.25f,ADD,ROUND),
            new StatMod(MAX_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,-0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, -1.5f,ADD,BARREL,STOCK),
            new StatMod(MIN_DMG,0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,0.25f,ADD,BARREL)
    );

    public static final List<TraitLevel> SILVER_TRAITS = List.of(
            new TraitLevel(HALLOW_POINT,2,ROUND,CASING),
            new TraitLevel(HALLOW_POINT,1)
    );

    public static final List<StatMod> PLUTONIUM_STATS = List.of(
            new StatMod(AMMO_MAX,0.8f, AVG_MUL,MAGAZINE),
            new StatMod(FIRE_RATE, 2.5f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,0.9F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(MAX_DMG,0.9F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(INACCURACY_DEG,1.75F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(MAX_DMG,2.25f,ADD,ROUND),
            new StatMod(MIN_DMG,1.75f,ADD,ROUND),
            new StatMod(MAX_DMG,0.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(PROJ_COUNT,2.5f,ADD,ROUND),
            new StatMod(INACCURACY_DEG, -1f,ADD,BARREL),
            new StatMod(MIN_DMG,0.75f,ADD,BARREL),
            new StatMod(MAX_DMG,0.75f,ADD,BARREL)
    );

    public static final List<TraitLevel> PLUTONIUM_TRAITS = List.of(
            new TraitLevel(SHATTERING,8,ROUND),
            new TraitLevel(LACERATING,3,ROUND),
            new TraitLevel(LINGERING,3,ROUND)
    );

    //Sgear Compat
    public static final List<StatMod> BLAZEGOLD_STATS = List.of(
            new StatMod(AMMO_MAX,1.25f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,1.25f, AVG_MUL,CASING),
            new StatMod(FIRE_RATE, -2.5f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(FIRE_RATE,-0.2F,MUL_TOTAL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,0.95F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,0.95F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(INACCURACY_DEG,1.1F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(MAX_DMG,1.25f,ADD,ROUND),
            new StatMod(MIN_DMG,1.75f,ADD,ROUND),
            new StatMod(MAX_DMG,0.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.75f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, -1f,ADD,BARREL),
            new StatMod(MIN_DMG,0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,0.25f,ADD,BARREL)
    );

    public static final List<TraitLevel> BLAZEGOLD_TRAITS = List.of(
            new TraitLevel(BLAZING,1,ROUND),
            new TraitLevel(BLAZING,1,ANY_BULLET_PART),
            new TraitLevel(BLAZING,1)
    );

    public static final List<StatMod> CRIMSON_IRON_STATS = List.of(
            new StatMod(AMMO_MAX,1.1f,AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,0.95f, AVG_MUL, CASING),
            new StatMod(MAX_DMG,1.25f,ADD,AFFECTS_BASE_DMG),
            new StatMod(MIN_DMG,1f,ADD,AFFECTS_BASE_DMG),
            new StatMod(MAX_DMG,.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,.25f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(VEL_MULT,0.9f, AVG_MUL,ROUND),
            new StatMod(GRAVITY_MOD,1.1f, AVG_MUL,ROUND),
            new StatMod(FIRE_RATE,0.75f,ADD,MAGAZINE,RECEIVER),
            new StatMod(FIRE_RATE,-0.75f,ADD,BARREL,STOCK),
            new StatMod(INACCURACY_DEG,-0.25f,ADD,BARREL,RECEIVER,STOCK),
            new StatMod(MIN_DMG,0.75f,ADD,BARREL),
            new StatMod(MAX_DMG,0.75f,ADD,BARREL)
    );
    public static final List<TraitLevel> CRIMSON_IRON_TRAITS = List.of(
            new TraitLevel(BLAZING,1,ROUND,CASING)
    );

    public static final List<StatMod> CRIMSON_STEEL_STATS = List.of(
            new StatMod(AMMO_MAX,1.3f, AVG_MUL,MAGAZINE),
            new StatMod(FIRE_RATE,0.85F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,1.1F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(MAX_DMG,1.1F,AVG_MUL,ACTION,CASING,ROUND),
            new StatMod(INACCURACY_DEG,0.85F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(MAX_DMG,1f,ADD,ROUND),
            new StatMod(MIN_DMG,1f,ADD,ROUND),
            new StatMod(MAX_DMG,0.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.5f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, -1.5f,ADD,BARREL,RECEIVER,STOCK),
            new StatMod(MIN_DMG,1f,ADD,BARREL),
            new StatMod(MAX_DMG,1f,ADD,BARREL)
    );
    public static final List<TraitLevel> CRIMSON_STEEL_TRAITS = List.of(
            new TraitLevel(BLAZING,2,ROUND,CASING)
    );

    public static final List<StatMod> AZURE_SILVER_STATS = List.of(
            new StatMod(AMMO_MAX,0.75f, AVG_MUL,MAGAZINE),
            new StatMod(FIRE_RATE, -3f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,-0.25f,ADD,ROUND),
            new StatMod(MIN_DMG,0.25f,ADD,ROUND),
            new StatMod(INACCURACY_DEG, -2f,ADD,BARREL,STOCK),
            new StatMod(MIN_DMG,0.25f,ADD,BARREL),
            new StatMod(MAX_DMG,0.25f,ADD,BARREL)
    );
    public static final List<TraitLevel> AZURE_SILVER_TRAITS = List.of(
            new TraitLevel(HALLOW_POINT,3,ROUND),
            new TraitLevel(HALLOW_POINT,2)
    );

    public static final List<StatMod> AZURE_ELECTRUM_STATS = List.of(
            new StatMod(AMMO_MAX,1.5f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,0.9f, AVG_MUL,CASING),
            new StatMod(FIRE_RATE, -2.55f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(FIRE_RATE,0.75F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,0.85F,AVG_MUL,ACTION,ROUND,CASING),
            new StatMod(MAX_DMG,0.85F,AVG_MUL,ACTION,ROUND,CASING),
            new StatMod(MAX_DMG,0.75f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,0.75f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MAX_DMG,0.25f,ADD,ROUND),
            new StatMod(MIN_DMG,-0.25f,ADD,ROUND),
            new StatMod(MIN_DMG,0.5f,ADD,BARREL),
            new StatMod(MAX_DMG,0.25f,ADD,BARREL)
    );

    public static final List<TraitLevel> AZURE_ELECTRUM_TRAITS = List.of(
            new TraitLevel(SHOCKING,4,ROUND),
            new TraitLevel(SHOCKING,4,CASING),
            new TraitLevel(SHOCKING,2,STOCK,ACTION,RECEIVER)
    );

    public static final List<StatMod> TYRAN_STEEL_STATS = List.of(
            new StatMod(AMMO_MAX,2f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,0.5f, AVG_MUL, CASING),
            new StatMod(FIRE_RATE,1.4F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,1.5F,AVG_MUL,CASING,ROUND),
            new StatMod(MAX_DMG,1.5F,AVG_MUL,CASING,ROUND),
            new StatMod(INACCURACY_DEG,0.8F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(PIERCE,-0.95f,MUL_TOTAL,ROUND),
            new StatMod(PROJ_COUNT,-0.95f,MUL_TOTAL,ROUND),
            new StatMod(MAX_DMG,2.5f,ADD,ROUND),
            new StatMod(MIN_DMG,3f,ADD,ROUND),
            new StatMod(MAX_DMG,1.75f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(MIN_DMG,1.75f,ADD,RECEIVER,ACTION,CASING),
            new StatMod(INACCURACY_DEG, -4.5f,ADD,BARREL,STOCK),
            new StatMod(MIN_DMG,1.5f,ADD,BARREL),
            new StatMod(MAX_DMG,1.25f,ADD,BARREL)
    );

    public static final List<TraitLevel> TYRAN_STEEL_TRAITS = List.of(
            new TraitLevel(VOID_TOUCHED,1,ROUND),
            new TraitLevel(LINGERING,1,ROUND),
            new TraitLevel(LACERATING,3,ROUND),
            new TraitLevel(LACERATING,2),
            new TraitLevel(SHATTERING,4,ROUND)
    );

}
