package com.blue0666.carpetblueaddition.mixins.rule.simplesoundsuppression;

import com.blue0666.carpetblueaddition.interfaces.onBlockStateChanged;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(method = "neighborUpdate", at = @At("RETURN"), cancellable = true)
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify, CallbackInfo ci) {
        //更新用于声音抑制的陷阱箱方块的监听频道
        if (CarpetBlueAdditionSettings.simpleSoundSuppression && world.getBlockEntity(pos) instanceof ChestBlockEntity){
            BlockEntity chestblockentity = world.getBlockEntity(pos);
            ((onBlockStateChanged)chestblockentity).updateSoundChannel();
        }
    }
}
