package com.lensmods.lenssgs.init;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
public class LenConfig {


    //Keep at top
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue GLOBAL_DMG_MULTI = BUILDER
            .comment("Global Damage multiplier for mod items. Default is 1.")
            .comment("Is applied after all normal calculations.")
            .comment("0.1<range<10")
            .defineInRange("globaldmgmult",1f,0.1f,10f);

    private static final ModConfigSpec.DoubleValue RIFLE_STAT_MULTI = BUILDER
            .comment("Stat applied to rifle calculations. Default is 1.")
            .comment("Higher -> More damage, less fire rate.")
            .comment("0.1<range<10")
            .defineInRange("riflestatsmul",1f,0.1f,10f);
    private static final ModConfigSpec.DoubleValue BULLPUP_STAT_MULTI = BUILDER
            .comment("Stat applied to bullpup calculations. Default is 1.")
            .comment("Higher -> More fire rate, Less damage.")
            .comment("0.1<range<10")
            .defineInRange("bullpupstatsmul",1f,0.1f,10f);
    private static final ModConfigSpec.DoubleValue PISTOL_STAT_MULTI = BUILDER
            .comment("Stat Applied to Pistol Calculations Default is 1.")
            .comment("Higher -> More stat malice from using a pistol.")
            .comment("0.1<range<10")
            .defineInRange("pistolstatsmul",1f,0.1f,10f);

    private static final ModConfigSpec.IntValue GUN_FIRE_TOLERANCE = BUILDER
            .comment("Maximum tolerance between when a gun should be able to fire again and when it actually does; in miliseconds")
            .comment("Higher -> Guns will fire more often, but laggier clients will have more consistent gameplay.")
            .comment("Default value is 5. Mod creator does not recommend over 20.")
            .comment("0<range<1000")
            .defineInRange("gunfire_tolerance",5,0,1000);

    //Keep at Bottom
    public static final ModConfigSpec SPEC = BUILDER.build();

    public static double global_damage_mult;
    public static double rifle_stat_mul;
    public static double bullpup_stat_mul;
    public static double pistol_stat_mul;
    public static int gun_tolerance;

    public static void onLoad(final ModConfigEvent event)
    {
        global_damage_mult = GLOBAL_DMG_MULTI.get();
        rifle_stat_mul = RIFLE_STAT_MULTI.get();
        bullpup_stat_mul = BULLPUP_STAT_MULTI.get();
        pistol_stat_mul = PISTOL_STAT_MULTI.get();
        gun_tolerance = GUN_FIRE_TOLERANCE.get();
    }
}
