package com.blue0666.carpetblueaddition.mixins.rule.soundsuppressionintroduce;

import com.blue0666.carpetblueaddition.event.soundlistenersystem.SoundEvent;
import com.blue0666.carpetblueaddition.event.soundlistenersystem.SoundEventManager;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.BellBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BellBlock.class)
public class BellBlockMixin {
    @Inject(method = "ring(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z", at = @At(value="RETURN"))
    private void emitSoundEvent(World world, BlockPos pos, @Nullable Direction direction, CallbackInfoReturnable<Boolean> cir){
        if (CarpetBlueAdditionSettings.soundSuppressionIntroduce) {
            if (cir.getReturnValue()) {
                if (!world.isClient()) {
                    SoundEventManager.handleBlockSound(world, pos, SoundEvent.BELL_RING);
                }
            }
        }
    }
}

