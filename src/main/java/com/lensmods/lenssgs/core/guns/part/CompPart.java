package com.lensmods.lenssgs.core.guns.part;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.lensmods.lenssgs.LensSGS;
import com.lensmods.lenssgs.api.item.GunType;
import com.lensmods.lenssgs.api.part.IPartSerializer;
import com.lensmods.lenssgs.api.part.PartType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.util.function.Function;

public class CompPart extends AbstractGunPart{

    private GunType gunType = GunType.ALL;
    private PartType partType;

    public CompPart(ResourceLocation name) {
        super(name);
    }

    @Override
    public PartType getType() {
        return partType;
    }

    @Override
    public IPartSerializer<?> getSerializer() {
        return PartSerializer.COMP_PART;
    }

    public static class Serializer extends AbstractGunPart.Serializer<CompPart> {

        public Serializer(ResourceLocation serializerId, Function<ResourceLocation, CompPart> function) {
            super(serializerId, function);
        }

        @Override
        public CompPart read(ResourceLocation id, JsonObject obj) {
            CompPart part = super.read(id,obj,false);
            String gunTStr = GsonHelper.getAsString(obj,"gun_type");
            part.gunType = GunType.get(gunTStr);
            if (!part.gunType.isGun()) {
                throw new JsonParseException("Unknown gun type: " + gunTStr);
            }
            part.partType = PartType.get(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,GsonHelper.getAsString(obj,"part_type")));
            return part;
        }

        @Override
        public CompPart read(ResourceLocation id, FriendlyByteBuf buf) {
            CompPart part = super.read(id, buf);
            part.gunType = GunType.get(buf.readUtf());
            part.partType = PartType.get(buf.readResourceLocation());
            return part;
        }
        @Override
        public void write(FriendlyByteBuf buffer, CompPart part) {
            super.write(buffer, part);
            buffer.writeUtf(part.gunType.getName());
            buffer.writeResourceLocation(part.partType.getName());
        }

    }
}
