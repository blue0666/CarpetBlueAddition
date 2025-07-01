package com.blue0666.carpetblueaddition.mixins.rule.soundsuppressionintroduce;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(ChestBlock.class)
abstract public class ChestBlockMixin extends AbstractChestBlock<ChestBlockEntity> {
    protected ChestBlockMixin(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityTypeSupplier) {
        super(settings, blockEntityTypeSupplier);
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        // 提示玩家不能打开用于更新抑制的陷阱箱
        if (CarpetBlueAdditionSettings.soundSuppressionIntroduce && world.getBlockEntity(pos) instanceof ChestBlockEntity) {
            String blockName = ((ChestBlockEntity) world.getBlockEntity(pos)).getDisplayName().getString();
            if ("声音抑制器".equals(blockName) || "SoundSuppressor".equalsIgnoreCase(blockName)) {
                if (world.isClient){
                    player.sendMessage(Text.of("你不能打开用于声音抑制的陷阱箱"), true);
                }
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }

}
