package com.lensmods.lenssgs.core.weaponsystems;


import com.google.common.collect.Maps;
import com.lensmods.lenssgs.core.items.GunBaseItem;
import com.lensmods.lenssgs.init.LenDataComponents;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.WeakHashMap;

public class PICooldown {
        private static final Map<Player, PICooldown> SHOOT_TRACKER_MAP = new WeakHashMap<>();

        private final Map<Item, Pair<Long, Integer>> cooldownMap = Maps.newHashMap();

        public static PICooldown getCDTracker(Player player)
        {
            return SHOOT_TRACKER_MAP.computeIfAbsent(player, player1 -> new PICooldown());
        }

        public void putCooldown(ItemStack stacc, GunBaseItem weapon)
        {
            int rate = Math.round(stacc.get(LenDataComponents.GUN_STATS).getFirerate());
            this.cooldownMap.put(weapon, Pair.of(Util.getMillis(), rate * 50));
        }

        public boolean hasCooldown(GunBaseItem item)
        {
            Pair<Long, Integer> pair = this.cooldownMap.get(item);
            if(pair != null)
            {
                return Util.getMillis() - pair.getFirst() < pair.getSecond() - 100;//Ehh, close enough
            }
            return false;
        }

        public long getRemaining(GunBaseItem item)
        {
            Pair<Long, Integer> pair = this.cooldownMap.get(item);
            if(pair != null)
            {
                return pair.getFirst() - (Util.getMillis() - pair.getSecond());
            }
            return 0;
        }
}
