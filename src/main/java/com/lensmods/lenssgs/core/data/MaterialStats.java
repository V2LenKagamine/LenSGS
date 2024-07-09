package com.lensmods.lenssgs.core.data;

import com.lensmods.lenssgs.core.datacomps.StatMod;

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
    //STAT MOD MODE
    public static final String MUL = "multiply"; // Add all together, multiply by that
    public static final String ADD = "add"; //adds to base stat.

    public static final List<StatMod> COPPER = List.of(
            new StatMod(AMMO_MAX,0.75f,MUL,AllowedParts.MAGAZINE),
            new StatMod(PIERCE,-1F,ADD,AllowedParts.ROUND),
            new StatMod(FIRE_RATE,0.95f,MUL,AllowedParts.AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,-1F,ADD,AllowedParts.AFFECTS_BASE_DMG),
            new StatMod(VEL_MULT,0.1f,MUL,AllowedParts.ROUND)
    );
    public static final List<StatMod> IRON = List.of(
            new StatMod(AMMO_MAX,0.9f,MUL,AllowedParts.CASING),
            new StatMod(FIRE_RATE,1.05f,MUL,AllowedParts.AFFECTS_FIRE_RATE),
            new StatMod(MAX_DMG,1F,ADD,AllowedParts.AFFECTS_BASE_DMG),
            new StatMod(VEL_MULT,-0.1f,MUL,AllowedParts.ROUND),
            new StatMod(GRAVITY_MOD,0.1f,MUL,AllowedParts.ROUND)
    );

}
