package com.blue0666.carpetblueaddition.mixins.rule.leashableboat;

import com.blue0666.carpetblueaddition.other.onLeashingBoat;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method="onEntityAttach",at = @At("RETURN"))
    public void onEntityAttachMixin(EntityAttachS2CPacket packet, CallbackInfo ci, @Local Entity entity){
        if (entity instanceof BoatEntity){
            ((onLeashingBoat)entity).setHoldingEntityId(packet.getHoldingEntityId());
        }
    }
}
