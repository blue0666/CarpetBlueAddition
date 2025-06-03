package com.blue0666.carpetblueaddition.mixins.rule.soundsuppressionintroduce;

import com.blue0666.carpetblueaddition.event.soundlistenersystem.SoundEvent;
import com.blue0666.carpetblueaddition.event.soundlistenersystem.SoundEventManager;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DoorBlock.class)
public class DoorBlockMixin {
    @Inject(method = "playOpenCloseSound", at = @At("TAIL"))
    private void emitSoundEvent(Entity entity, World world, BlockPos pos, boolean open, CallbackInfo ci){
        if (CarpetBlueAdditionSettings.soundSuppressionIntroduce){
            if (!world.isClient()) {
                if (open) {
                    // 模拟门被打开
                    SoundEventManager.handleBlockSound(world, pos, SoundEvent.DOOR_OPEN);
                } else {
                    SoundEventManager.handleBlockSound(world, pos, SoundEvent.DOOR_CLOSE);
                }
            }
        }
    }
}
