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

    //STAT MOD MODE
    public static final String MUL_TOTAL = "total_multiply"; // Add all together, multiply final product by this number.
    public static final String AVG_MUL = "multiply"; // Add all together, multiply by that avg
    public static final String ADD = "add"; //adds to base stat.

    public static final List<StatMod> WOOD_STATS = List.of(
            new StatMod(MIN_DMG,0.05F,MUL_TOTAL,ROUND),
            new StatMod(MAX_DMG,0.05F,MUL_TOTAL,ROUND),
            new StatMod(INACCURACY_DEG, -0.5f,ADD,STOCK)
    );
    public static final List<TraitLevel> WOOD_TRAITS = List.of(
            new TraitLevel(ECOLOGICAL,1,ROUND)
    );

    public static final List<StatMod> STONE_STATS = List.of(
            new StatMod(MAX_DMG,-1f,ADD,CASING),
            new StatMod(MIN_DMG,-1f,ADD,CASING),
            new StatMod(MAX_DMG,0.9f,AVG_MUL,AFFECTS_DMG_MOD),
            new StatMod(MIN_DMG,0.8f,AVG_MUL,AFFECTS_DMG_MOD),
            new StatMod(VEL_MULT,-0.25f,ADD,AFFECTS_VEL),
            new StatMod(FIRE_RATE,10f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(PROJ_COUNT, -2f,ADD,ROUND),
            new StatMod(AMMO_MAX,0.5f, AVG_MUL,MAGAZINE),
            new StatMod(INACCURACY_DEG,2f,ADD,BARREL,ROUND,RECEIVER),
            new StatMod(GRAVITY_MOD, 0.005f,ADD,ROUND),
            new StatMod(INACCURACY_DEG, -0.75f,ADD,STOCK)
    );

    public static final List<StatMod> COPPER_STATS = List.of(
            new StatMod(AMMO_MAX,0.75f, AVG_MUL,MAGAZINE),
            new StatMod(PIERCE,-1F,ADD,ROUND),
            new StatMod(FIRE_RATE,0.95f, AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,-1F,ADD,AFFECTS_BASE_DMG),
            new StatMod(VEL_MULT,0.9f, AVG_MUL,ROUND),
            new StatMod(FIRE_RATE,-2.5f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(INACCURACY_DEG,2.5f,ADD,BARREL,RECEIVER),
            new StatMod(INACCURACY_DEG,-0.5f,ADD,STOCK)
    );
    public static final List<TraitLevel> COPPER_TRAITS= List.of(
            new TraitLevel(SHOCKING,1,CASING,ROUND,RECEIVER)
    );
    public static final List<StatMod> IRON_STATS = List.of(
            new StatMod(AMMO_MAX,0.9f, AVG_MUL, CASING),
            new StatMod(FIRE_RATE,1.05f, AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,0.5F,ADD,AFFECTS_BASE_DMG),
            new StatMod(VEL_MULT,0.9f, AVG_MUL,ROUND),
            new StatMod(GRAVITY_MOD,1.1f, AVG_MUL,ROUND),
            new StatMod(FIRE_RATE,5f,ADD,MAGAZINE,RECEIVER),
            new StatMod(FIRE_RATE,-5F,ADD,BARREL,STOCK)
    );
    public static final List<StatMod> GOLD_STATS = List.of(
            new StatMod(AMMO_MAX,0.6f, AVG_MUL,MAGAZINE),
            new StatMod(PIERCE,-2f,ADD,ROUND),
            new StatMod(MAX_DMG,-1.5f,ADD,ROUND,CASING,RECEIVER),
            new StatMod(MIN_DMG,-1.5f,ADD,ROUND,CASING,RECEIVER),
            new StatMod(INACCURACY_DEG, -1.5f,ADD,BARREL,ACTION),
            new StatMod(INACCURACY_DEG,4F,ADD,STOCK),
            new StatMod(FIRE_RATE, -7.5f,ADD,AFFECTS_FIRE_RATE),
            new StatMod(PROJ_COUNT,3f,ADD,ROUND)
    );
    public static final List<StatMod> DIAMOND_STATS = List.of(
            new StatMod(AMMO_MAX,1.1f, AVG_MUL,MAGAZINE),
            new StatMod(PIERCE,1f,ADD,ROUND),
            new StatMod(MAX_DMG,0.5f,ADD,ROUND,ACTION),
            new StatMod(MIN_DMG,1.5f,ADD,ROUND,ACTION),
            new StatMod(VEL_MULT,0.75f,AVG_MUL,AFFECTS_VEL),
            new StatMod(FIRE_RATE,3f,ADD,ACTION,STOCK),
            new StatMod(PROJ_COUNT, -2f,ADD,ROUND),
            new StatMod(INACCURACY_DEG, -3f,ADD,STOCK,RECEIVER),
            new StatMod(INACCURACY_DEG,0.75f, AVG_MUL,ROUND)
    );

    public static final List<TraitLevel>DIAMOND_TRAITS = List.of(
            new TraitLevel(BLINDING,1,ROUND),
            new TraitLevel(LACERATING,1,ROUND)
    );

    public static final List<StatMod> EMERALD_STATS = List.of(
            new StatMod(AMMO_MAX,1.1f, AVG_MUL,MAGAZINE),
            new StatMod(PIERCE,1f,ADD,ROUND),
            new StatMod(MAX_DMG,2.5f,ADD,ROUND,ACTION),
            new StatMod(MIN_DMG,-1f,ADD,ROUND,ACTION),
            new StatMod(VEL_MULT,0.75f,AVG_MUL,AFFECTS_VEL),
            new StatMod(FIRE_RATE,5f,ADD,ACTION,STOCK),
            new StatMod(PROJ_COUNT, -2f,ADD,ROUND),
            new StatMod(INACCURACY_DEG, -2.5f,ADD,STOCK),
            new StatMod(INACCURACY_DEG,1.25f, AVG_MUL,ROUND)
    );
    public static final List<TraitLevel>EMERALD_TRAITS = List.of(
            new TraitLevel(LACERATING,2,ROUND)
    );

    public static final List<StatMod> NETHERITE_STATS = List.of(
            new StatMod(AMMO_MAX,1.5f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,0.8f, AVG_MUL, CASING),
            new StatMod(FIRE_RATE,1.25F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,1.05F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,1.05F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(INACCURACY_DEG,0.8F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(PIERCE,3f,ADD,ROUND),
            new StatMod(MAX_DMG,1f,ADD,ROUND),
            new StatMod(MIN_DMG,2.5f,ADD,ROUND),
            new StatMod(INACCURACY_DEG, -2.5f,ADD,BARREL),
            new StatMod(PROJ_COUNT,-0.2f, MUL_TOTAL,ROUND)
    );

    public static final List<StatMod> VOID_STATS = List.of(
            new StatMod(AMMO_MAX,1.75f, AVG_MUL,MAGAZINE),
            new StatMod(AMMO_MAX,0.75f, AVG_MUL, CASING),
            new StatMod(FIRE_RATE,0.6F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MIN_DMG,1.1F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,1.1F,AVG_MUL,AFFECTS_FIRE_RATE),
            new StatMod(INACCURACY_DEG,0.9F,AVG_MUL,STOCK,RECEIVER,ACTION,ROUND),
            new StatMod(PIERCE,0.05f,MUL_TOTAL,ROUND),
            new StatMod(MAX_DMG,2f,ADD,ROUND),
            new StatMod(MIN_DMG,3.5f,ADD,ROUND),
            new StatMod(INACCURACY_DEG, -3f,ADD,BARREL)
    );

    public static final List<TraitLevel> VOID_TRAITS = List.of(
            new TraitLevel(VOID_TOUCHED,1,ROUND),
            new TraitLevel(VOID_TOUCHED,1)
    );
    public static final List<StatMod> GUNPOWDER_STATS = List.of(
            new StatMod(AMMO_MAX,1f, AVG_MUL,PROPELLANT) //This is here because I am too lazy to check that it wont throw a fit at an empty list.
    );
    public static final List<StatMod> TNT_STATS =List.of(
            new StatMod(AMMO_MAX,0.5f,MUL_TOTAL,PROPELLANT),
            new StatMod(MIN_DMG,0.25f,MUL_TOTAL,PROPELLANT),
            new StatMod(MAX_DMG,0.25f,MUL_TOTAL,PROPELLANT),
            new StatMod(INACCURACY_DEG,0.5f,MUL_TOTAL,PROPELLANT),
            new StatMod(VEL_MULT,0.5f,MUL_TOTAL,PROPELLANT)
    );
    public static final List<TraitLevel> TNT_TRAITS = List.of(
            new TraitLevel(EXPLOSIVE,1)
    );
    public static final List<StatMod> PRISMARINE_STATS = List.of(
            new StatMod(PROJ_COUNT,2F,ADD,ROUND),
            new StatMod(MIN_DMG,0.5f,ADD,ROUND),
            new StatMod(MAX_DMG,1.5f,ADD,ROUND)
    );
    public static final List<TraitLevel> PRISMARINE_TRAITS = List.of(
            new TraitLevel(SHATTERING,2),
            new TraitLevel(LACERATING,1)
    );

    public static final List<StatMod> PRISMARINE_CRYSTAL_STATS = List.of(
            new StatMod(PROJ_COUNT,1F,ADD,ROUND),
            new StatMod(MAX_DMG,1f,ADD,ROUND)
    );

    public static final List<StatMod> SLIME_STATS = List.of(
            new StatMod(MAX_DMG,-0.25F,MUL_TOTAL,ROUND),
            new StatMod(MIN_DMG,-0.25F,MUL_TOTAL,ROUND),
            new StatMod(INACCURACY_DEG,6f,ADD,STOCK)
    );
    public static final List<TraitLevel> SLIME_TRAITS = List.of(
            new TraitLevel(CONCUSSIVE,1,ROUND)
    );

    public static final List<TraitLevel> PRISMARINE_CRYSTAL_TRAITS = List.of(
            new TraitLevel(SHATTERING,1),
            new TraitLevel(TRACING,1),
            new TraitLevel(BLINDING,1)
    );

    public static final List<StatMod> BLAZEROD_STATS =List.of(
            new StatMod(MIN_DMG,0.25f,ADD,BARREL),
            new StatMod(MIN_DMG, 0.5f,ADD,ROUND)
    );
    public static final List<TraitLevel> BLAZEROD_TRAITS = List.of(
            new TraitLevel(BLAZING,1)
    );

    public static final List<StatMod> QUARTZ_STATS = List.of(
            new StatMod(MIN_DMG,0.75f,ADD,ROUND),
            new StatMod(MAX_DMG,0.75f,ADD,ROUND),
            new StatMod(PROJ_COUNT,2f,ADD,ROUND)
    );
    public static final List<TraitLevel> QUARTZ_TRAITS = List.of(
            new TraitLevel(SHATTERING,1),
            new TraitLevel(LACERATING,1)
    );

    public static final List<StatMod> BREEZE_ROD_STATS = List.of(
            new StatMod(GRAVITY_MOD,-0.01f,ADD,BARREL,ROUND),
            new StatMod(VEL_MULT,0.02f,ADD,BARREL,ROUND,PROPELLANT),
            new StatMod(MIN_DMG,0.75f,ADD,BARREL,ROUND,PROPELLANT),
            new StatMod(MAX_DMG,0.75f,ADD,BARREL,ROUND,PROPELLANT)
    );
    public static final List<TraitLevel> BREEZE_ROD_TRAITS = List.of(
            new TraitLevel(CONCUSSIVE,3)
    );
    public static final List<StatMod> AMETHYST_STATS = List.of(
            new StatMod(PROJ_COUNT,3f,ADD,ROUND),
            new StatMod(PROJ_COUNT,0.25f,MUL_TOTAL,ROUND),
            new StatMod(MAX_DMG,-1.25f,ADD,ROUND),
            new StatMod(MIN_DMG,-0.75f,ADD,ROUND)
    );
    public static final List<TraitLevel> AMETHYST_TRAITS = List.of(
            new TraitLevel(SHATTERING,2),
            new TraitLevel(LACERATING,1)
    );
    public static final List<StatMod> DRAGONS_BREATH_STATS = List.of(
            new StatMod(MIN_DMG,0.75f,ADD,ROUND),
            new StatMod(MAX_DMG,1f,ADD,ROUND),
            new StatMod(AMMO_MAX,1.1F,AVG_MUL,ROUND),
            new StatMod(INACCURACY_DEG,9f,ADD,ROUND)
    );
    public static final List<TraitLevel> DRAGONS_BREATH_TRAITS = List.of(
            new TraitLevel(LINGERING,1),
            new TraitLevel(BLAZING,3)
    );

    public static final List<StatMod> NETHER_STAR_STATS = List.of(
            new StatMod(MIN_DMG,0.75f,ADD,ROUND),
            new StatMod(MAX_DMG,0.75f,ADD,ROUND),
            new StatMod(INACCURACY_DEG,7.5F,ADD,ROUND,PROPELLANT),
            new StatMod(PROJ_COUNT,5f,ADD,ROUND,PROPELLANT),
            new StatMod(MIN_DMG,1.25f,AVG_MUL,PROPELLANT),
            new StatMod(MAX_DMG,1.25f,AVG_MUL,PROPELLANT)
    );
    public static final List<TraitLevel> NETHER_STAR_TRAITS = List.of(
            new TraitLevel(SHATTERING,2),
            new TraitLevel(SHOCKING,2),
            new TraitLevel(TRACING,2)
    );

}
