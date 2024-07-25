package com.lensmods.lenssgs.core.weaponsystems;

import com.lensmods.lenssgs.core.data.AllowedParts;
import com.lensmods.lenssgs.core.data.MaterialStats;
import com.lensmods.lenssgs.core.datacomps.GunPartHolder;
import com.lensmods.lenssgs.core.datacomps.GunStatTraitPair;
import com.lensmods.lenssgs.core.datacomps.TraitLevel;
import com.lensmods.lenssgs.core.entity.GenericProjectile;
import com.lensmods.lenssgs.core.items.GunBaseItem;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.init.LenConfig;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.lensmods.lenssgs.init.LenEnts;
import com.lensmods.lenssgs.init.LenSounds;
import com.lensmods.lenssgs.networking.messages.CTSFire;
import com.lensmods.lenssgs.networking.messages.CTSReload;
import com.lensmods.lenssgs.networking.messages.STCFireSync;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

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
            if(cdtrack.hasCooldown(held) && cdtrack.getRemaining(held) > LenConfig.gun_tolerance) {
                return;
            }
            int projAmt = WeaponAmmoStats.getProjAmt(held);
            GunStatTraitPair lastAmmoLoaded = WeaponAmmoStats.getLastAmmo(held);
            if(lastAmmoLoaded == null || !WeaponAmmoStats.safeGunStats(held)) {
                player.displayClientMessage(LenUtil.translatableOf("no_ammo"),true);
            }
            else {
                List<TraitLevel> ammotraits = new ArrayList<>(lastAmmoLoaded.getTraits());
                int cdTime =held.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getFirerate() + lastAmmoLoaded.getStats().getFirerate();
                cdtrack.putCooldown(held,Math.round(cdTime));
                PacketDistributor.sendToPlayer(player,new STCFireSync(cdTime));
                List<TraitLevel> gunTraits = held.get(LenDataComponents.GUN_STAT_TRAITS).getTraits();
                ammotraits.addAll(gunTraits);
                Map<String,Integer> tempStorage = new LinkedHashMap<>(10);
                List<TraitLevel> tosend = new ArrayList<>();
                for(TraitLevel trait : ammotraits) {
                    if(tempStorage.containsKey(trait.trait())){
                        int temp = tempStorage.get(trait.trait());
                        tempStorage.put(trait.trait(),trait.level()+temp);
                    }else
                    {
                        tempStorage.put(trait.trait(),trait.level());
                    }
                }
                tempStorage.forEach((trait,level) -> tosend.add(new TraitLevel(trait,level)));
                GenericProjectile[] spawned = new GenericProjectile[projAmt];
                for (int i = 0; i < projAmt; i++) {
                    GenericProjectile boolet = new GenericProjectile(LenEnts.GENERIC_PROJ.get(), worl, player, held);
                    boolet.set_dmg(WeaponAmmoStats.rollDmg(held));
                    boolet.set_pierce(WeaponAmmoStats.getPierce(held));
                    boolet.setGravityMod(WeaponAmmoStats.getGrav(held));
                    boolet.setVelMult(WeaponAmmoStats.getVelMult(held));
                    boolet.setTraits(tosend);
                    worl.addFreshEntity(boolet);
                    spawned[i] = boolet;
                    boolet.tick();
                }
                boolean eternal = gunTraits.stream().anyMatch(trait-> trait.trait().equals(MaterialStats.ETERNAL));
                boolean nocon = false;
                if(eternal) {
                    nocon = LenUtil.randBetween(0f,100f) <= gunTraits.stream().filter(trait-> trait.trait().equals(MaterialStats.ETERNAL)).findFirst().get().level()*5f;
                }
                if (!player.isCreative() || !nocon) {
                    int temp = held.get(LenDataComponents.AMMO_COUNTER);
                    held.set(LenDataComponents.AMMO_COUNTER, temp - WeaponAmmoStats.AMMO_POINTS_MUL);
                }
                breaker:
                for (GunPartHolder part : held.get(LenDataComponents.GUN_COMP).getPartList()) {
                    switch (part.getSubType()) {
                        case AllowedParts.RECEIVER_BULLPUP -> {
                            worl.playSound(null, player.getX(), player.getY(), player.getZ(), LenSounds.GUN_SHOT_BULLPUP.get(), SoundSource.PLAYERS);
                            break breaker;
                        }
                        case AllowedParts.RECEIVER_PISTOL -> {
                            worl.playSound(null, player.getX(), player.getY(), player.getZ(), LenSounds.GUN_SHOT_PISTOL.get(), SoundSource.PLAYERS);
                            break breaker;
                        }
                        case AllowedParts.RECEIVER_STANDARD -> {
                            worl.playSound(null, player.getX(), player.getY(), player.getZ(), LenSounds.GUN_SHOT_RIFLE.get(), SoundSource.PLAYERS);
                            break breaker;
                        }
                    }
                }
            }
        }
    }
    public static void handleReloadMsg(CTSReload mess,@NotNull ServerPlayer player) {
        if(player.isSpectator()) {
            return;
        }
        ItemStack main = player.getItemInHand(InteractionHand.MAIN_HAND);
        if(main.getItem() instanceof GunBaseItem) {
            if(main.getOrDefault(LenDataComponents.AMMO_COUNTER,null) != null && main.getOrDefault(LenDataComponents.GUN_COMP,null) != null
                    && main.getOrDefault(LenDataComponents.GUN_STAT_TRAITS,null)!=null) {
                boolean mag = false;
                String magname = "none";
                for (GunPartHolder part : main.get(LenDataComponents.GUN_COMP).getPartList()) {
                    if (part.getName().contains(AllowedParts.MAGAZINE)) {
                        magname = part.getSubType();
                        mag =true;
                        break;
                    }
                }
                if(ReloadTracker.getReloadTracker(player).hasReloadTimer(player)) {
                    if (mag&& main.get(LenDataComponents.GUN_STAT_TRAITS).getStats().getAmmo_max() > WeaponAmmoStats.AMMO_POINTS_MUL) {
                        int reloadtime;
                        switch (magname)  {
                            case AllowedParts.MAGAZINE_SHORT -> reloadtime = 40;
                            case AllowedParts.MAGAZINE_NORMAL -> reloadtime = 70;
                            case AllowedParts.MAGAZINE_EXTENDED -> reloadtime = 100;
                            case AllowedParts.MAGAZINE_BELT -> reloadtime = 160;
                            default -> reloadtime = 10;
                        }
                        player.getCooldowns().addCooldown(main.getItem(),reloadtime);
                        ReloadTracker.getReloadTracker(player).putReloadTimer(reloadtime);
                    }else {
                        int reloadtime = 90;
                        player.getCooldowns().addCooldown(main.getItem(),reloadtime);
                        ReloadTracker.getReloadTracker(player).putReloadTimer(reloadtime);
                    }
                }
            }
        }
    }
}
