package com.lensmods.lenssgs.core.data;

import java.util.List;

public final class AllowedParts {
    //Main parts
    public static final String RECEIVER = "receiver";
    public static final String BARREL = "barrel";
    public static final String ACTION = "action";
    //Optional Parts
    public static final String MAGAZINE = "magazine";
    public static final String STOCK = "stock";
    //Receiver Types
    public static final String RECEIVER_PISTOL = "receiver_pistol";
    public static final String RECEIVER_STANDARD = "receiver_standard";
    public static final String RECEIVER_BULLPUP = "receiver_bullpup";
    //Action-subtypes
    public static final String ACTION_MANUAL = "action_manual";
    public static final String ACTION_SINGLE = "action_single";
    public static final String ACTION_AUTOMATIC = "action_automatic";
    //Barrel-subtypes
    public static final String BARREL_STUB = "barrel_stub";
    public static final String BARREL_SHORT = "barrel_short";
    public static final String BARREL_FAIR = "barrel_normal";
    public static final String BARREL_LONG = "barrel_long";
    public static final String BARREL_EXTENDED = "barrel_extended";
    //Stock-subtypes
    public static final String STOCK_SHORT = "stock_short";
    public static final String STOCK_FULL = "stock_full";
    //Mag-Subtypes
    public static final String MAGAZINE_SHORT = "magazine_short";
    public static final String MAGAZINE_NORMAL = "magazine_normal";
    public static final String MAGAZINE_EXTENDED = "magazine_extended";
    public static final String MAGAZINE_BELT = "magazine_belt";

    //Bullet main parts
    public static final String CASING = "casing";
    public static final String ROUND = "bullet";
    public static final String PROPELLANT = "prop";

    //Casing-subtypes
    public static final String CASING_SMALL = "casing_small";
    public static final String CASING_NORMAL = "casing_normal";
    public static final String CASING_LARGE = "casing_large";
    public static final String CASING_SHELL = "casing_shell";

    //Bullet-Subtypes
    public static final String ROUND_STANDARD = "round_standard";
    public static final String ROUND_BUCKSHOT = "round_buck";
    public static final String ROUND_BIRDSHOT = "round_bird";

    //Propellant-Subtypes
    public static final String PROPELLANT_LIGHT = "prop_light";
    public static final String PROPELLANT_NORMAL = "prop_normal";
    public static final String PROPELLANT_HEAVY = "prop_heavy";
    //Lists
    public static final List<String> ANY_PART = List.of(STOCK,BARREL,ACTION,MAGAZINE,CASING,ROUND,PROPELLANT,RECEIVER);
    public static final List<String> ANY_GUN_SUB_PART = List.of(RECEIVER_PISTOL, RECEIVER_BULLPUP, RECEIVER_STANDARD,ACTION_SINGLE,ACTION_MANUAL,ACTION_AUTOMATIC,
            BARREL_STUB,BARREL_SHORT,BARREL_FAIR,BARREL_LONG,BARREL_EXTENDED,STOCK_SHORT,STOCK_FULL,MAGAZINE_SHORT,MAGAZINE_NORMAL,MAGAZINE_EXTENDED,MAGAZINE_BELT);
    public static final List<String> ANY_GUN_PART = List.of(STOCK,BARREL,ACTION,MAGAZINE,RECEIVER);
    public static final List<String> ANY_BULLET_PART = List.of(CASING,ROUND,PROPELLANT);

    public static final List<String> GUN_MANDITORY = List.of(RECEIVER,BARREL,ACTION);
    public static final List<String> AMMO_MANDITORY = List.of(CASING,ROUND,PROPELLANT);


    public static final List<String> AFFECTS_AMMO = List.of(MAGAZINE,CASING);
    public static final List<String> AFFECTS_PIERCE = List.of(ROUND,CASING,BARREL);
    public static final List<String> AFFECTS_PROJ_COUNT = List.of(ROUND);
    public static final List<String> AFFECTS_DMG_MOD = List.of(STOCK,BARREL);
    public static final List<String> AFFECTS_BASE_DMG = ANY_BULLET_PART;
    public static final List<String> AFFECTS_GRAVITY = List.of(BARREL,CASING,ROUND);
    public static final List<String> AFFECTS_VEL_BASE = ANY_BULLET_PART;
    public static final List<String> AFFECTS_VEL = List.of(BARREL,CASING,ROUND,PROPELLANT);
    public static final List<String> AFFECTS_FIRE_RATE = List.of(STOCK,ACTION,CASING);
    public static final List<String> AFFECTS_INACC = List.of(STOCK,ROUND,RECEIVER);

}
