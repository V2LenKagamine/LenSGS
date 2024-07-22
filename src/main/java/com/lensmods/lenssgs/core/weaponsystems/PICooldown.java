package com.lensmods.lenssgs.core.weaponsystems;


import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.WeakHashMap;

public class PICooldown {
        private static final Map<Player, PICooldown> SHOOT_TRACKER_MAP = new WeakHashMap<>();

        private final Map<ItemStack, Pair<Long, Integer>> cooldownMap = Maps.newHashMap();

        public static PICooldown getCDTracker(Player player)
        {
            return SHOOT_TRACKER_MAP.computeIfAbsent(player, player1 -> new PICooldown());
        }

        public void putCooldown(ItemStack stacc, int time)
        {

            this.cooldownMap.put(stacc, Pair.of(Util.getMillis(), time * 50));
        }

        public boolean hasCooldown(ItemStack item)
        {
            Pair<Long, Integer> pair = this.cooldownMap.get(item);
            if(pair != null)
            {
                return Util.getMillis() - pair.getFirst() < pair.getSecond() - 50;//Ehh, close enough
            }
            return false;
        }

        public long getRemaining(ItemStack item)
        {
            Pair<Long, Integer> pair = this.cooldownMap.get(item);
            if(pair != null)
            {
                return pair.getFirst() - (Util.getMillis() - pair.getSecond());
            }
            return 0;
        }
}
