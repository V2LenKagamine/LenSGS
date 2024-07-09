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
        keyof("barrel_long","Long Barrel");
        keyof("gunfake1", "This gun is entirely fake.");
        keyof("gunfake2", "That, or the parts are all broken!");
        keyof("mindmg","Min Damage: ");
        keyof("maxdmg","Max Damage: ");
        keyof("firerate", "Fire-Rate: ");
        keyof("ammomax", "Max Ammo: ");
        keyof("velocity_mult", "Bullet Speed: *");
        keyof("grav_mult", "Bullet Gravity: ");
        keyof("ammo_current","Ammo in Mag: ");
    }

    protected void keyof(String key,String toTranslate) {
        add(LensSGS.MODID + "." + key,toTranslate);
    }
}
