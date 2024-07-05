package com.lensmods.lenssgs.core.guns.part;

import com.google.gson.JsonObject;
import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.api.part.IGunPart;
import com.lensmods.lenssgs.api.part.IPartSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class PartSerializer {
    public static final CompPart.Serializer COMP_PART = new CompPart.Serializer(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"comp_part"),CompPart::new);

    public static IGunPart deserialize(ResourceLocation id, JsonObject json) {
        return COMP_PART.read(id, json);
    }

    public static IGunPart read(FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation();
        return COMP_PART.read(id,buf);
    }

    public static <T extends IGunPart> void write(T part,FriendlyByteBuf buf) {
        ResourceLocation id = part.getId();
        ResourceLocation type = part.getSerializer().getName();
        buf.writeResourceLocation(id);
        buf.writeResourceLocation(type);
        IPartSerializer<T> serializer = (IPartSerializer<T>) part.getSerializer();
        serializer.write(buf,part);
    }
}
