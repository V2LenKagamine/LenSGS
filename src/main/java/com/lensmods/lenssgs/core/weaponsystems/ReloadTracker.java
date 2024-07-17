package com.lensmods.lenssgs.core.weaponsystems;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.WeakHashMap;

public class ReloadTracker {
    private static final Map<Player, ReloadTracker> RELOAD_TRACKER_MAP = new WeakHashMap<>();

    private final Map<ItemStack, Pair<Long, Integer>> reloadMap = Maps.newHashMap();

    public static ReloadTracker getReloadTracker(Player player)
    {
        return RELOAD_TRACKER_MAP.computeIfAbsent(player, player1 -> new ReloadTracker());
    }
    public void removeTimer(ItemStack stack) {
        if(reloadMap.containsKey(stack)) {
            reloadMap.remove(stack);
        }
    }
    public void putReloadTime(ItemStack stacc, int time)
    {
        if(!reloadMap.containsKey(stacc)) {
            this.reloadMap.put(stacc, Pair.of(Minecraft.getInstance().level.getGameTime(), time));
        }
    }

    public boolean hasReloadTimer(ItemStack item)
    {
        Pair<Long, Integer> pair = this.reloadMap.get(item);
        if(pair != null)
        {
            return Util.getMillis() - pair.getFirst() < pair.getSecond()- 1;//Ehh, close enough
        }
        return false;
    }

    public long getRemaining(ItemStack item)
    {
        Pair<Long, Integer> pair = this.reloadMap.get(item);
        if(pair != null)
        {
            return pair.getFirst() - (Minecraft.getInstance().level.getGameTime() - pair.getSecond());
        }
        return 0;
    }

}
