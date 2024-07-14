package com.lensmods.lenssgs.core.data;

import com.lensmods.lenssgs.core.datacomps.StatMod;
import com.lensmods.lenssgs.core.datacomps.TraitLevel;

import java.util.List;

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

    //STAT MOD MODE
    public static final String MUL_TOTAL = "total_multiply"; // Add all together, multiply final product by this number.
    public static final String AVG_MUL = "multiply"; // Add all together, multiply by that avg
    public static final String ADD = "add"; //adds to base stat.

    public static final List<StatMod> WOOD = List.of(
            new StatMod(INACCURACY_DEG, -0.5f,ADD,AllowedParts.STOCK)
    );

    public static final List<StatMod> STONE_STATS = List.of(
            new StatMod(MAX_DMG,-1f,ADD,AllowedParts.CASING),
            new StatMod(MIN_DMG,-1f,ADD,AllowedParts.CASING),
            new StatMod(VEL_MULT,-0.25f,ADD,AllowedParts.AFFECTS_VEL),
            new StatMod(PROJ_COUNT, -2f,ADD,AllowedParts.ROUND),
            new StatMod(AMMO_MAX,0.5f, AVG_MUL,AllowedParts.MAGAZINE),
            new StatMod(INACCURACY_DEG,-2f,ADD,AllowedParts.BARREL,AllowedParts.ROUND),
            new StatMod(GRAVITY_MOD, 0.001f,ADD,AllowedParts.ROUND)
    );

    public static final List<StatMod> COPPER_STATS = List.of(
            new StatMod(AMMO_MAX,0.75f, AVG_MUL,AllowedParts.MAGAZINE),
            new StatMod(PIERCE,-1F,ADD,AllowedParts.ROUND),
            new StatMod(FIRE_RATE,0.95f, AVG_MUL,AllowedParts.AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,-1F,ADD,AllowedParts.AFFECTS_BASE_DMG),
            new StatMod(VEL_MULT,0.1f, AVG_MUL,AllowedParts.ROUND)
    );
    public static final List<StatMod> IRON_STATS = List.of(
            new StatMod(AMMO_MAX,0.9f, AVG_MUL,AllowedParts.CASING),
            new StatMod(FIRE_RATE,1.05f, AVG_MUL,AllowedParts.AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,1F,ADD,AllowedParts.AFFECTS_BASE_DMG),
            new StatMod(VEL_MULT,-0.1f, AVG_MUL,AllowedParts.ROUND),
            new StatMod(GRAVITY_MOD,1.1f, AVG_MUL,AllowedParts.ROUND)
    );
    public static final List<StatMod> GOLD_STATS = List.of(
            new StatMod(AMMO_MAX,0.6f, AVG_MUL,AllowedParts.MAGAZINE),
            new StatMod(PIERCE,-2f,ADD,AllowedParts.ROUND),
            new StatMod(MAX_DMG,-2f,ADD,AllowedParts.AFFECTS_BASE_DMG),
            new StatMod(MIN_DMG,-2f,ADD,AllowedParts.AFFECTS_BASE_DMG),
            new StatMod(INACCURACY_DEG, -1.5f,ADD,AllowedParts.BARREL),
            new StatMod(PROJ_COUNT,3f,ADD,AllowedParts.ROUND)
    );
    public static final List<StatMod> DIAMOND_STATS = List.of(
            new StatMod(AMMO_MAX,1.25f, AVG_MUL,AllowedParts.MAGAZINE),
            new StatMod(PIERCE,2f,ADD,AllowedParts.ROUND),
            new StatMod(MAX_DMG,3.5f,ADD,AllowedParts.ROUND),
            new StatMod(MIN_DMG,-1.5f,ADD,AllowedParts.ROUND),
            new StatMod(VEL_MULT,-0.25f,ADD,AllowedParts.AFFECTS_VEL),
            new StatMod(PROJ_COUNT, -2f,ADD,AllowedParts.ROUND),
            new StatMod(INACCURACY_DEG,1.25f, AVG_MUL,AllowedParts.AFFECTS_PROJ_COUNT) //This is intentional trust me
    );
    public static final List<StatMod> NETHERITE_STATS = List.of(
            new StatMod(AMMO_MAX,1.5f, AVG_MUL,AllowedParts.MAGAZINE),
            new StatMod(AMMO_MAX,0.8f, AVG_MUL,AllowedParts.CASING),
            new StatMod(PIERCE,3f,ADD,AllowedParts.ROUND),
            new StatMod(MAX_DMG,2.5f,ADD,AllowedParts.ROUND),
            new StatMod(MIN_DMG,4f,ADD,AllowedParts.ROUND),
            new StatMod(INACCURACY_DEG, -2.5f,ADD,AllowedParts.BARREL),
            new StatMod(PROJ_COUNT,-0.2f, MUL_TOTAL,AllowedParts.ROUND)
    );
    public static final List<StatMod> GUNPOWDER_STATS = List.of(
            new StatMod(AMMO_MAX,1f, AVG_MUL,AllowedParts.PROPELLANT) //This is here because I am too lazy to check that it wont throw a fit at an empty list.
    );
    public static final List<StatMod> BLAZEROD_STATS =List.of(
            new StatMod(MIN_DMG,0.5f,ADD,AllowedParts.BARREL),
            new StatMod(MIN_DMG, 1f,ADD,AllowedParts.ROUND)
    );
    public static final List<TraitLevel> BLAZEROD_TRAITS = List.of(
            new TraitLevel(BLAZING,1)
    );
}
