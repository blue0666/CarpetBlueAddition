package com.blue0666.carpetblueaddition.mixins.rule.softcryingobsidian;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockMixin implements AbstractBlockStateInvoker{
    @Inject(method = "getHardness", at = @At("HEAD"), cancellable = true)
    private void modifyDeepslateHardness(BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        //#if MC>=11700
        if (CarpetBlueAdditionSettings.softCryingObsidian && this.invokeGetBlock().equals(Blocks.CRYING_OBSIDIAN)) {
            cir.setReturnValue(Blocks.END_STONE.getDefaultState().getHardness(world, pos));
            cir.cancel();
        }
    }
}
