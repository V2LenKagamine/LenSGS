package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.core.util.LenUtil;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static com.lensmods.lenssgs.core.data.AllowedParts.*;
import static com.lensmods.lenssgs.core.data.MaterialStats.*;

public class LenLang extends LanguageProvider {
    public LenLang(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    public static final Component GUN_INSTRUCTIONS = LenUtil.translatableOf("gun_instructions");

    @Override
    protected void addTranslations() {
        //Sounds
        keyof("gun_click","Gun Clicks");
        keyof("gun_shot","Gun Fires");
        keyof("void_reave","Void Beckons");
        keyof("gun_reload","Gun Reloads");
        //Generic
        add("itemGroup.lenssgs","Len's SGS");
        add("death.attack.lenssgs.firearm","%s was forcefully overdosed with bullets.");
        keyof("gun_instructions","To make a gun: You need three things, an Action, a Receiver,and a Barrel." +
                " Construct any variant of these using 1 of a material and a part crafter. Then, put all three in a crafting table together." +
                " Bullets can similarly be made using a Casing, Round, And Propellant." +
                " Guns may optionally contain a Stock,Magazine and Sights. These can be added by crafting table, and parts can be swapped at any time." +
                " Magazines,Stocks, and Sights can not be removed once added;but can be swapped for another." +
                " Sights allow zooming with your use key, but are otherwise cosmetic; make them your favorite color!" +
                " To reload a gun, simply hit the 'reload' keybind and wait." +
                " You can see what can be a gun material by searching the correct tag." +
                " Holding the correct keybinds while hoevering a gun material will reveal more about its stats." +
                " TOTAL simply adds or subtracts to the stat." +
                " Avg. Multiply's will be added together, then divided by how many there were of that type." +
                " This means that anything < 1 will LOWER that stat, and anything > 1 will RAISE that stat." +
                " Total Multiplies will be applied at the end, when all other stats are calculated, and effect the 'Final' stat.");
        keyof("gun_recipe","Len's SGS Instructions");
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
        add("refills_ammo","Can be used to restore some ammo of a bullet-stack.");
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
        keyof(LINGERING,"Lingering");
        keyof(HALLOW_POINT,"Hallow-Point");
        keyof(ETERNAL,"Eternal");
        //Keybinds
        add("key.categories.lenssgs","Len's Gun Shenanigans");
        bindkeyof("cycle.back", "Tooltip Cycle Back");
        bindkeyof("cycle.next", "Tooltip Cycle Forward");
        bindkeyof("displayConstruction","Show Gun Parts");
        bindkeyof("displayStats","Display Gun Stats");
        bindkeyof("displayTraits", "Display Gun Traits");
        bindkeyof("reload","Reload Gun");
        //Items
        itemkeyof("gunprinter_paper","Gun-Printer Paper");
        itemkeyof("void_metal","Void Metal Ingot");
        itemkeyof("wyrm_steel","Wyrm Steel Ingot");
        itemkeyof("blitz_gold","Blitz Gold Ingot");
        itemkeyof("hocufe_ingot","HoCuFe Ingot");
        itemkeyof("nurukukan_ingot","Nuru-Kukan Ingot");
        //Effect keys
        add("item.minecraft.tipped_arrow.effect.laceration_potion","Arrow of Laceration");
        add("item.minecraft.potion.effect.laceration_potion","Potion of Laceration");
        add("item.minecraft.splash_potion.effect.laceration_potion","Splash Potion of Laceration");
        add("item.minecraft.lingering_potion.effect.laceration_potion","Lingering Potion of Laceration");
        add("effect.lenssgs.lacerated","Lacerated");
        add("effect.lenssgs.shatter","Shattered");
        //Parts
        keyof(RECEIVER,"Receiver:");
        keyof(ACTION,"Action:");
        keyof(BARREL,"Barrel:");
        keyof(STOCK,"Stock:");
        keyof(MAGAZINE, "Magazine:");
        keyof(ROUND, "Projectile:");
        keyof(PROPELLANT,"Propellant:");
        keyof(CASING,"Casing:");
        keyof(SCOPE,"Sights:");
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
        keyof(ROUND_BIRDSHOT,"Birdshot Round");
        keyof(PROPELLANT_LIGHT,"Light Propellant");
        keyof(PROPELLANT_NORMAL,"Standard Propellant");
        keyof(PROPELLANT_HEAVY,"Heavy Propellant");
        keyof(SCOPE_IRONS, "Iron Sights");
        keyof(SCOPE_SHORT,"Reflex Sights");
        keyof(SCOPE_MEDIUM, "Med-Range Sights");
        keyof(SCOPE_LONG,"Long-Range Sights");
        keyof("part_crafter","Part Crafter");
        //Materials
        keyof("wood","Wooden");
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
        keyof("magma_cream","Magma Cream");
        keyof("wyrm_steel","Wyrm Steel");
        keyof("blitz_gold","Blitz Gold");
        keyof("hocufe","HoCuFe");
        keyof("nurukukan","Nuru-Kukan");
        keyof("copper_rod","Copper Bolt");
        keyof("end_rod","End Rod");
        keyof("fish","Fish?");
        keyof("fire_charge","Fire Charge");
        //Modded Mats
        keyof("bronze","Bronze");
        keyof("refined_obsidian","Refined Obsidian");
        keyof("refined_glowstone","Refined Glowstone");
        keyof("steel","Steel");
        keyof("osmium","Osmium");
        keyof("tin","Tin");
        keyof("lead","Lead");
        keyof("uranium","Uranium");
        keyof("aluminum","Aluminum");
        keyof("electrum","Electrum");
        keyof("plutonium","Plutonium");
        keyof("silver","Silver");
        //Sgear
        keyof("blaze_gold","Blaze Gold");
        keyof("crimson_iron","Crimson Iron");
        keyof("crimson_steel","Crimson Steel");
        keyof("azure_silver","Azure Silver");
        keyof("azure_electrum","Azure Electrum");
        keyof("tyran_steel","Tyran Steel");
        //modern Industrialization
        keyof("stainless_steel","Stainless Steel");
        keyof("tungsten","Tungsten");
        keyof("titanium","Titanium");
        keyof("depleted","Depleted Uranium");
        keyof("invar_blade","Diamond-Tipped Saw-blade");
        //Mekanism
        keyof("infused_alloy","Infused Alloy");
        keyof("reinforced_alloy","Reinforced Alloy");
        keyof("atomic_alloy","Atomic Alloy");
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
