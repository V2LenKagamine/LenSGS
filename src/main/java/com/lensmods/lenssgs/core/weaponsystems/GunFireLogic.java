package com.lensmods.lenssgs.core.weaponsystems;

import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.networking.messages.CTSFire;
import net.minecraft.server.level.ServerPlayer;

public class GunFireLogic {
    public static void handleFireMsg(CTSFire message, ServerPlayer player) {
        if(player.isSpectator()) {
            return;
        }
        LensSGS.L3NLOGGER.info("yipppeeeeeeeeeeeeeee");
    }
}
