package com.lensmods.lenssgs.core.util;

import com.lensmods.lenssgs.LensSGS;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;
//This class is modified from silentchaos512. Go check out Silent's Gear. NOW.
@EventBusSubscriber(modid = LensSGS.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class KeyManager {
    public static final Lazy<KeyMapping> DISPLAY_STATS = Lazy.of(() -> new KeyMapping("key.lenssgs.displayStats", GLFW.GLFW_KEY_LEFT_SHIFT, "key.categories.lenssgs"));
    public static final Lazy<KeyMapping> DISPLAY_TRAITS = Lazy.of(() -> new KeyMapping("key.lenssgs.displayTraits", GLFW.GLFW_KEY_LEFT_CONTROL, "key.categories.lenssgs"));
    public static final Lazy<KeyMapping> DISPLAY_CONSTRUCTION = Lazy.of(() -> new KeyMapping("key.lenssgs.displayConstruction", GLFW.GLFW_KEY_LEFT_ALT, "key.categories.lenssgs"));
    public static final Lazy<KeyMapping> CYCLE_BACK = Lazy.of(() -> new KeyMapping("key.lenssgs.cycle.back", GLFW.GLFW_KEY_Z, "key.categories.lenssgs"));
    public static final Lazy<KeyMapping> CYCLE_NEXT = Lazy.of(() -> new KeyMapping("key.lenssgs.cycle.next", GLFW.GLFW_KEY_C, "key.categories.lenssgs"));

    private static int cycleCount = 0;

    public static int getMaterialCycleIndex(int total) {
        int i = cycleCount % total;
        return i < 0 ? i + total : i;
    }

    @SubscribeEvent
    public static void onKeyDown(ScreenEvent.KeyPressed.Post event) {

        if (DISPLAY_STATS.get().matches(event.getKeyCode(), event.getScanCode())) {
            DISPLAY_STATS.get().setDown(true);
        }
        if (DISPLAY_TRAITS.get().matches(event.getKeyCode(), event.getScanCode())) {
            DISPLAY_TRAITS.get().setDown(true);
        }
        if (DISPLAY_CONSTRUCTION.get().matches(event.getKeyCode(), event.getScanCode())) {
            DISPLAY_CONSTRUCTION.get().setDown(true);
        }
        if (CYCLE_NEXT.get().matches(event.getKeyCode(), event.getScanCode())) {
            ++cycleCount;
        }
        if (CYCLE_BACK.get().matches(event.getKeyCode(), event.getScanCode())) {
            --cycleCount;
        }
    }

    @SubscribeEvent
    public static void onKeyUp(ScreenEvent.KeyReleased.Post event) {
        if (DISPLAY_STATS.get().matches(event.getKeyCode(), event.getScanCode())) {
            DISPLAY_STATS.get().setDown(false);
        }
        if (DISPLAY_TRAITS.get().matches(event.getKeyCode(), event.getScanCode())) {
            DISPLAY_TRAITS.get().setDown(false);
        }
        if (DISPLAY_CONSTRUCTION.get().matches(event.getKeyCode(), event.getScanCode())) {
            DISPLAY_CONSTRUCTION.get().setDown(false);
        }
        if (cycleCount > 0 && !(DISPLAY_STATS.get().isDown() || DISPLAY_TRAITS.get().isDown())) {
            cycleCount = 0;
        }
    }
}