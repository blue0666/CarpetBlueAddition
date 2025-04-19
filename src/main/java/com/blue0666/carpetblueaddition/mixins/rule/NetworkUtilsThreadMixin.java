package com.blue0666.carpetblueaddition.mixins.rule;

import com.blue0666.carpetblueaddition.exception.IAESoundSuppresionException;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.thread.ThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NetworkThreadUtils.class)
public class NetworkUtilsThreadMixin {
    @SuppressWarnings({"MixinExtrasOperationParameters", "unchecked"})
    @WrapOperation(method = "method_11072", at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/network/Packet;apply(Lnet/minecraft/network/listener/PacketListener;)V"
            )
    )
    private static <T extends PacketListener> void exceptionReason(Packet packet, T listener, Operation<Void> original) {
        try {
            original.call(packet, listener);
        } catch (RuntimeException e) {
            IAESoundSuppresionException iae;
            if (e instanceof IAESoundSuppresionException) {
                iae = (IAESoundSuppresionException) e;
            } else if (e instanceof CrashException crashException && crashException.getCause() instanceof IAESoundSuppresionException exception) {
                iae = exception;
            } else {
                throw e;
            }
            exceptionReason((Packet<ServerPlayPacketListener>) packet, listener, iae);
            throw e;
        }
    }

    // 如果是玩家操作导致的，记录异常原因
    @Unique
    private static <T extends PacketListener> void exceptionReason(Packet<ServerPlayPacketListener> packet, T listener, IAESoundSuppresionException iaeSoundSuppresionException) {
        if (listener instanceof ServerPlayNetworkHandler networkHandler) {
            iaeSoundSuppresionException.onCatch(networkHandler.player, packet);
        }
    }
}
