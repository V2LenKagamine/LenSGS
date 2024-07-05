package com.lensmods.lenssgs.api.material;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface IMaterialSerializer<T extends IMaterial> {
    T deserialize(ResourceLocation id, String packName, JsonObject json);

    T read (ResourceLocation id, FriendlyByteBuf buff);

    void write (FriendlyByteBuf buff, T mat);

    ResourceLocation getName();
}
