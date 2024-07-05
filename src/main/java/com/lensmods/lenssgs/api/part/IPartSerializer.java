package com.lensmods.lenssgs.api.part;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface IPartSerializer<T extends IGunPart> {
    T read(ResourceLocation id, JsonObject object);

    T read(ResourceLocation id, FriendlyByteBuf buffer);

    void write(FriendlyByteBuf buffer, T part);

    ResourceLocation getName();
}
