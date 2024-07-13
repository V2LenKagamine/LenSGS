package com.lensmods.lenssgs.core.data;

import com.lensmods.lenssgs.core.datacomps.GunMaterial;
import com.lensmods.lenssgs.init.LenDataReg;
import net.minecraft.core.HolderLookup;

import java.util.HashMap;
import java.util.Map;

public class MaterialMap {
    private static Map<String,GunMaterial> theMap;

    public static Map<String,GunMaterial> loadedMats(HolderLookup.Provider access) {
        if (theMap == null) {
            Map<String,GunMaterial> returned = new HashMap<>();
            var temp = access.lookup(LenDataReg.GUN_MAT_KEY).get();
            temp.listElements().forEach(mat -> returned.put(mat.value().getMatName(),mat.value()));
            return returned;
        }
        else return theMap;
    }
}
