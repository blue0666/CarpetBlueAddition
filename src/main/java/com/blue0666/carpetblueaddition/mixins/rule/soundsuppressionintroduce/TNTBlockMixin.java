package com.blue0666.carpetblueaddition.mixins.rule.soundsuppressionintroduce;

import com.blue0666.carpetblueaddition.event.soundlistenersystem.SoundEvent;
import com.blue0666.carpetblueaddition.event.soundlistenersystem.SoundEventManager;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntBlock.class)
public class TNTBlockMixin {
    @Inject(method="primeTnt(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/LivingEntity;)V",
            at = @At(value="RETURN"))
    private static void primeTnt(World world, BlockPos pos, LivingEntity igniter, CallbackInfo ci){
        if (CarpetBlueAdditionSettings.soundSuppressionIntroduce){
            SoundEventManager.handleBlockSound(world, pos, SoundEvent.TNT_FUSE);
        }
    }
}
