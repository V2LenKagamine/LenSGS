package com.lensmods.lenssgs.datagen;

import com.lensmods.lenssgs.LensSGS;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LenLang extends LanguageProvider {
    public LenLang(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        keyof("gunfake1", "This gun is entirely fake.");
        keyof("gunfake2", "That, or the parts are all broken!");
        keyof("mindmg","Min Damage:");
        keyof("maxdmg","Max Damage:");
        keyof("firerate", "Fire-Rate:");
        keyof("ammomax", "Max Ammo:");
        keyof("velocity_mult", "Bullet Speed:*");
        keyof("grav_mult", "Bullet Gravity:");
        keyof("ammo_current","Ammo in Mag:");
        //Parts
        keyof("receiver_pistol","Pistol Receiver");
        keyof("receiver_standard","Rifle Receiver");
        keyof("receiver_bullpup","Bullpup Receiver");
        keyof("action_manual","Manual Action");
        keyof("action_single","Single Action");
        keyof("action_automatic","Automatic Action");
        keyof("barrel_stub","Stub Barrel");
        keyof("barrel_normal","Normal Barrel");
        keyof("barrel_long","Long Barrel");
        keyof("barrel_extended","Extended Barrel");
        keyof("stock_short","Short Stock");
        keyof("stock_full","Full Stock");
        keyof("magazine_short","Short Magazine");
        keyof("magazine_normal","Standard Magazine");
        keyof("magazine_extended", "Extended Magazine");
        keyof("magazine_belt","Belt-Fed Adapter");
        keyof("casing_small","Small Casing");
        keyof("casing_normal","Standard Casing");
        keyof("casing_large","Large Casing");
        keyof("casing_shell","Shell Casing");
        keyof("round_standard","Standard Round");
        keyof("round_buck","Buckshot Round");
        keyof("round_bird","Birdshot round");
        keyof("prop_light","Light Propellant");
        keyof("prop_normal","Standard Propellant");
        keyof("prop_heavy","Heavy Propellant");
        keyof("part_crafter","Part Crafter");
        //Materials
        keyof("copper","Copper");
        keyof("iron","Iron");
        keyof("gold","Gold");

    }

    protected void keyof(String key,String toTranslate) {
        add(LensSGS.MODID + "." + key,toTranslate);
    }
}
