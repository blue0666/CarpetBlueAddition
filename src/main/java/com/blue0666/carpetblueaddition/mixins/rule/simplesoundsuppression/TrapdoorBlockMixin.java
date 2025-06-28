package com.blue0666.carpetblueaddition.mixins.rule.simplesoundsuppression;

import com.blue0666.carpetblueaddition.event.soundlistenersystem.SoundEvent;
import com.blue0666.carpetblueaddition.event.soundlistenersystem.SoundEventManager;
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
        if (CarpetBlueAdditionSettings.simpleSoundSuppression){
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
