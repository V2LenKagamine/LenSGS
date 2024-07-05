package com.lensmods.lenssgs.core.guns.part;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.api.part.IGunPart;
import com.lensmods.lenssgs.api.part.PartType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public final class PartManager implements ResourceManagerReloadListener {
    public static final PartManager INSTANCE = new PartManager();

    public static final Marker MARKER = MarkerManager.getMarker("LenPartManager");

    private static final String DATA_PATH = "lenssgs_parts";
    private static final Map<ResourceLocation, IGunPart> MAP = Collections.synchronizedMap(new LinkedHashMap<>());
    private static final Collection<String> ERROR_LIST = new ArrayList<>();

    private PartManager() {
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        Gson gson = (new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create());
        Map<ResourceLocation, Resource> resources = resourceManager.listResources(DATA_PATH, s-> s.toString().endsWith(".json"));
        if (resources.isEmpty()) {return;}
        synchronized (MAP) {
            MAP.clear();
            ERROR_LIST.clear();
            LensSGS.L3NLOGGER.info("Reloading Part files...", MARKER);

            String pName = "ERROR";
            for(ResourceLocation id : resources.keySet()) {
                String path = id.getPath().substring(DATA_PATH.length()+1,id.getPath().length()-".json".length());
                ResourceLocation name = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), path);

                Optional<Resource> resourceOptional = resourceManager.getResource(id);
                if (resourceOptional.isPresent()) {
                    Resource iresource = resourceOptional.get();
                    pName = iresource.sourcePackId();

                    JsonObject json = null;
                    try {
                        json = GsonHelper.fromJson(gson, IOUtils.toString(iresource.open(), StandardCharsets.UTF_8), JsonObject.class);
                    } catch (IOException ex) {
                        LensSGS.L3NLOGGER.error("Could not read gun part {}", name, ex, MARKER);
                        ERROR_LIST.add(String.format("%s (%s)", name, pName));
                    }

                    if (json == null) {
                        LensSGS.L3NLOGGER.error("Could not load part {} as it's null or empty", name, MARKER);
                    } else {
                        IGunPart part = tryDeserialize(name, pName, json);
                        if (part instanceof AbstractGunPart) {
                            ((AbstractGunPart) part).pName = iresource.sourcePackId();
                        }
                        addPart(part);
                    }
                }
            }
        }
        LensSGS.L3NLOGGER.info("Yep thats about {} parts.",MAP.size(),MARKER);
    }
    @NotNull
    private static IGunPart tryDeserialize(ResourceLocation name, String packName, JsonObject json) {
        LensSGS.L3NLOGGER.info("Deserializing part {} in pack {}", name, packName);
        try {
            return PartSerializer.deserialize(name, json);
        } catch (JsonSyntaxException ex) {
            throw new RuntimeException("Error in \'"+name+"\'from pack\'" + packName+ "\'.Bad! : ",ex);
        }
    }

    private static void addPart(IGunPart part) {
        if (MAP.containsKey(part.getId())) {
            throw new IllegalStateException("Duplicate gun part " + part.getId());
        } else {
            MAP.put(part.getId(), part);
        }
    }

    public static Collection<IGunPart> getValues() {
        synchronized (MAP) {
            return MAP.values();
        }
    }
    public static IGunPart from(ItemStack stacc) {
        if (stacc.isEmpty()) {
            return null;
        }
        for (IGunPart part : getValues()) {
            if(part.getIngredient().test(stacc)) {
                return part;
            }
        }
        return null;
    }
    public static List<IGunPart> getPartsOfType(PartType type) {
        return getValues().stream().filter(part -> part.getType() == type).collect(Collectors.toList());
    }
}
