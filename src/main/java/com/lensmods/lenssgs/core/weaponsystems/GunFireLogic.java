package com.lensmods.lenssgs.core.weaponsystems;

import com.lensmods.lenssgs.core.entity.GenericProjectile;
import com.lensmods.lenssgs.core.items.GunBaseItem;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.lensmods.lenssgs.init.LenEnts;
import com.lensmods.lenssgs.networking.messages.CTSFire;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GunFireLogic {
    public static void handleFireMsg(CTSFire message, @NotNull ServerPlayer player) {
        if(player.isSpectator()) {
            return;
        }
        Level worl = player.level();
        ItemStack held = player.getItemInHand(InteractionHand.MAIN_HAND);
        if(held.getItem() instanceof GunBaseItem gun && (WeaponAmmoStats.doesHaveAmmo(held) || player.isCreative()))
        {
            player.setXRot(Mth.clamp(message.xRot(),-90F,90F));//Why is xRot Up/Down? No idea.
            player.setYRot(Mth.wrapDegrees(message.yRot()));

            PICooldown cdtrack = PICooldown.getCDTracker(player);
            if(cdtrack.hasCooldown(gun) && cdtrack.getRemaining(gun) > 100) { //Todo: make Tolerance config or gamerule?
                return;
            }
            cdtrack.putCooldown(held,gun);

            int projAmt = WeaponAmmoStats.getProjAmt(held);
            GenericProjectile[] spawned = new GenericProjectile[projAmt];
            for (int i = 0; i < projAmt; i++) {
                GenericProjectile boolet = new GenericProjectile(LenEnts.GENERIC_PROJ.get(),worl,player,held);
                boolet.set_dmg(WeaponAmmoStats.rollDmg(held));
                boolet.set_pierce(WeaponAmmoStats.getPierce(held));
                boolet.setGravityMod(WeaponAmmoStats.getGrav(held));
                boolet.setVelMult(WeaponAmmoStats.getVelMult(held));
                worl.addFreshEntity(boolet);
                spawned[i] = boolet;
                boolet.tick();
            }
            if (!player.isCreative()) {
                int temp = held.get(LenDataComponents.AMMO_COUNTER);
                held.set(LenDataComponents.AMMO_COUNTER, temp - 1);
            }
        }
    }
}
