package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.LensSGS;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static com.lensmods.lenssgs.core.data.MaterialStats.*;
import static com.lensmods.lenssgs.core.data.AllowedParts.*;

public class LenLang extends LanguageProvider {
    public LenLang(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        //Sounds
        keyof("gun_click","Gun Clicks");
        keyof("gun_shot","Gun Fires");
        keyof("void_reave","Void Beckons");
        //Generic
        add("itemGroup.lenssgs","Len's SGS");
        keyof("gunfake1", "This gun is entirely fake.");
        keyof("gunfake2", "That, or the parts are all broken!");
        keyof("gunmat_useful","This looks usable for a Gun or Ammo...");
        keyof("mindmg","Min Damage:");
        keyof("maxdmg","Max Damage:");
        keyof("firerate", "Fire-Rate:");
        keyof("ammomax", "Max Ammo:");
        keyof("velocity_mult", "Bullet Speed:*");
        keyof("grav_mult", "Bullet Gravity:");
        keyof("ammo_current","Ammo in Mag:");
        keyof("ammo_current_ammo","Rounds Remaining:");
        keyof("proj_count","Projectile Count:");
        keyof("inacc","Inaccuracy:");
        keyof("pierce","Pierce:");
        keyof("display_stats","Stats");
        keyof("display_traits","Traits");
        keyof("display_composition","Composition");
        keyof("no_ammo","You need to have reloaded with a bullet at least once before firing a gun;Even in creative!");
        keyof("refills_ammo","Can be used to restore some ammo of a bullet-stack.");
        //Stats
        keyof(AMMO_MAX,"Max Ammo Capacity:");
        keyof(PIERCE,"Peirce:");
        keyof(FIRE_RATE, "Fire-Rate:");
        keyof(PROJ_COUNT,"Projectiles:");
        keyof(MAX_DMG,"Maximum-Damage:");
        keyof(MIN_DMG,"Minimum-Damage:");
        keyof(GRAVITY_MOD,"Gravity:");
        keyof(VEL_MULT,"Velocity:");
        keyof(INACCURACY_DEG,"Inaccuracy:");
        keyof(MUL_TOTAL,"Final Multiplier:");
        keyof(AVG_MUL,"Avg. Multiplier:");
        keyof(ADD,"Total:");
        //Traits
        keyof(BLAZING,"Blazing");
        keyof(ECOLOGICAL,"Eco-Friendly");
        keyof(SHOCKING,"Thunder-Struck");
        keyof(TRACING,"Revealing");
        keyof(LACERATING,"Lacerating");
        keyof(BLINDING,"Blinding");
        keyof(EXPLOSIVE,"Exploding");
        keyof(CONCUSSIVE,"Concussing");
        keyof(VOID_TOUCHED,"Void's-Maw");
        keyof(SHATTERING,"Shattering");
        //Keybinds
        add("key.categories.lenssgs","Len's Gun Shenanigans");
        bindkeyof("cycle.back", "Tooltip Cycle Back");
        bindkeyof("cycle.next", "Tooltip Cycle Forward");
        bindkeyof("displayConstruction","Show Gun Parts");
        bindkeyof("displayStats","Display Gun Stats");
        bindkeyof("displayTraits", "Display Gun Traits");
        //Items
        itemkeyof("gunprinter_paper","Gun-Printer Paper");
        itemkeyof("void_metal","Void Metal");
        //Parts
        keyof(RECEIVER,"Receiver:");
        keyof(ACTION,"Action:");
        keyof(BARREL,"Barrel:");
        keyof(STOCK,"Stock:");
        keyof(MAGAZINE, "Magazine:");
        keyof(ROUND, "Projectile:");
        keyof(PROPELLANT,"Propellant:");
        keyof(CASING,"Casing:");
        keyof(RECEIVER_PISTOL,"Pistol Receiver");
        keyof(RECEIVER_STANDARD,"Rifle Receiver");
        keyof(RECEIVER_BULLPUP,"Bullpup Receiver");
        keyof(ACTION_MANUAL,"Break Action");
        keyof(ACTION_SINGLE,"Single Action");
        keyof(ACTION_AUTOMATIC,"Double Action");
        keyof(BARREL_STUB,"Stub Barrel");
        keyof(BARREL_SHORT,"Short Barrel");
        keyof(BARREL_FAIR,"Normal Barrel");
        keyof(BARREL_LONG,"Long Barrel");
        keyof(BARREL_EXTENDED,"Extended Barrel");
        keyof(STOCK_SHORT,"Short Stock");
        keyof(STOCK_FULL,"Full Stock");
        keyof(MAGAZINE_SHORT,"Short Magazine");
        keyof(MAGAZINE_NORMAL,"Standard Magazine");
        keyof(MAGAZINE_EXTENDED, "Extended Magazine");
        keyof(MAGAZINE_BELT,"Belt-Fed Adapter");
        keyof(CASING_SMALL,"Small Casing");
        keyof(CASING_NORMAL,"Standard Casing");
        keyof(CASING_LARGE,"Large Casing");
        keyof(CASING_SHELL,"Shell Casing");
        keyof(ROUND_STANDARD,"Standard Round");
        keyof(ROUND_BUCKSHOT,"Buckshot Round");
        keyof(ROUND_BIRDSHOT,"Birdshot round");
        keyof(PROPELLANT_LIGHT,"Light Propellant");
        keyof(PROPELLANT_NORMAL,"Standard Propellant");
        keyof(PROPELLANT_HEAVY,"Heavy Propellant");
        keyof("part_crafter","Part Crafter");
        //Materials
        keyof("stone","Stone");
        keyof("copper","Copper");
        keyof("iron","Iron");
        keyof("gold","Gold");
        keyof("diamond","Diamond");
        keyof("netherite","Netherite");
        keyof("gunpowder","Gunpowder");
        keyof("blaze","Blaze");
        keyof("void_metal","Void Metal");
        keyof("prismarine","Prismarine");
        keyof("prismarine_gem","Prismarine Crystal");
        keyof("quartz","Quartz");
        keyof("slime","Slime");
        keyof("tnt","Dynamite");
        keyof("breeze","Breeze");
        keyof("amethyst","Amethyst");
        keyof("emerald","Emerald");
        keyof("dragonsbreath","Dragon's Breath");
        keyof("netherstar","Nether Star");
    }

    protected void keyof(String key,String toTranslate) {
        add(LensSGS.MODID + "." + key,toTranslate);
    }
    protected void itemkeyof(String key,String toTranslate) {
        add("item."+ LensSGS.MODID + "." + key,toTranslate);
    }
    protected void bindkeyof(String key,String toTranslate) {
        add("key."+ LensSGS.MODID + "." + key,toTranslate);
    }
}
