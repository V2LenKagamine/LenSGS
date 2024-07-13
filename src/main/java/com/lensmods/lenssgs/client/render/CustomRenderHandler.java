package com.lensmods.lenssgs.client.render;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.lensmods.lenssgs.LensSGS;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;

public class CustomRenderHandler implements IGeometryLoader<CustomRenderGeo> {
    public static final CustomRenderHandler INSTANCE = new CustomRenderHandler();
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(LensSGS.MODID, "gun_renderer");

    private CustomRenderHandler() {
    }

    @Override
    public CustomRenderGeo read(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException {
        //jsonObject.remove("loader");
        //BlockModel base = context.deserialize(jsonObject, ElementsModel.class);
        return new CustomRenderGeo();
    }
}

