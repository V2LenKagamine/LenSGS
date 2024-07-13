package com.lensmods.lenssgs.core.data;

import com.lensmods.lenssgs.core.datacomps.GunMaterial;
import com.lensmods.lenssgs.init.LenDataReg;
import net.minecraft.core.HolderLookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllowedPartsMap {
    private static Map<GunMaterial,List<String>> theMap;

    public static Map<GunMaterial,List<String>> loadedMats(HolderLookup.Provider access) {
        if (theMap == null) {
            Map<GunMaterial,List<String>> returned = new HashMap<>();
            var temp = access.lookup(LenDataReg.GUN_MAT_KEY).get();
            temp.listElements().forEach(mat -> returned.put(mat.value(),mat.value().getAllowedParts()));
            return returned;
        }
        else return theMap;
    }
}
