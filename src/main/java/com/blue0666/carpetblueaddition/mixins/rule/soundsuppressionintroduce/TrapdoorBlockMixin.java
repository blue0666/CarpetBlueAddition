package com.blue0666.carpetblueaddition.mixins.rule.soundsuppressionintroduce;

import com.blue0666.carpetblueaddition.event.soundlistenersystem.SoundListenerSystem;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrapdoorBlock.class)
public abstract class TrapdoorBlockMixin {

    @Shadow public abstract BlockState getPlacementState(ItemPlacementContext ctx);

    @Inject(method = "playToggleSound", at = @At("TAIL"))
    private void emitSoundEvent(@Nullable PlayerEntity player, World world, BlockPos pos, boolean open, CallbackInfo ci) {
        if (CarpetBlueAdditionSettings.soundSuppressionIntruduce){
            if (!world.isClient()) {
                if (open) {
                    // 模拟门被打开
                    SoundListenerSystem.handleDoorOpened(world, pos);
                } else {
                    SoundListenerSystem.handleDoorClosed(world, pos);
                }
            }
        }
    }
}
