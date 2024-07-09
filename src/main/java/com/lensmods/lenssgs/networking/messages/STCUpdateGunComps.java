package com.lensmods.lenssgs.networking.messages;

import com.lensmods.lenssgs.LensSGS;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record STCUpdateGunComps(DataComponentPatch patch) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<STCUpdateGunComps> STC_UPDATE_GUN_COMPS_TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(LensSGS.MODID,"stcupdate_gun_comps"));

    public static final StreamCodec<RegistryFriendlyByteBuf,STCUpdateGunComps> STC_UPDATE_GUN_COMPS_SCODEC = StreamCodec.composite(
            DataComponentPatch.STREAM_CODEC, STCUpdateGunComps::patch,
            STCUpdateGunComps::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return STC_UPDATE_GUN_COMPS_TYPE;
    }
}
