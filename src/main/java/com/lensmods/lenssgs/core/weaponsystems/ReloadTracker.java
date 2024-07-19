package com.lensmods.lenssgs.core.weaponsystems;

import com.lensmods.lenssgs.core.items.GunBaseItem;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Map;
import java.util.WeakHashMap;
@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class ReloadTracker {

    private static final Map<Player, ReloadTracker> RELOAD_TRACKER_MAP = new WeakHashMap<>();
    protected int tillReload = 0;
    protected boolean reloading;
    @SubscribeEvent
    private static void onLevelTick(ServerTickEvent.Post unusued) {
        if(!RELOAD_TRACKER_MAP.isEmpty()) {
            RELOAD_TRACKER_MAP.forEach((player,tracker) -> {
                if(tracker.tillReload>1)
                {tracker.tillReload--;}
                else if(player.getMainHandItem().getItem() instanceof GunBaseItem&& tracker.reloading){
                    WeaponAmmoStats.attemptReload(player,player.getMainHandItem(),player.level());
                    tracker.reloading =false;
                }
            });
        }
    }

    public static ReloadTracker getReloadTracker(Player player)
    {
        return RELOAD_TRACKER_MAP.computeIfAbsent(player, player1 -> new ReloadTracker());
    }
    public void putReloadTimer(int time) {
        tillReload = time;
        reloading=true;
    }

    public boolean hasReloadTimer(Player ply)
    {
        return RELOAD_TRACKER_MAP.containsKey(ply);
    }

    public int getRemaining()
    {
        return this.tillReload;
    }

}
