package com.blue0666.carpetblueaddition.mixins.rule.sculkcatalystintroduce;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
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

import javax.swing.text.JTextComponent;
import java.awt.*;

@Mixin(BarrelBlock.class)
public class BarrelBlockMixin {
    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        // 提示玩家幽匿催发体是没有gui的
        if (CarpetBlueAdditionSettings.sculkCatalystIntroduce && world.getBlockEntity(pos) instanceof BarrelBlockEntity) {
            String blockName = ((BarrelBlockEntity) world.getBlockEntity(pos)).getDisplayName().getString();
            if ("幽匿催发体".equals(blockName) || "sculkcatalyst".equalsIgnoreCase(blockName)) {
                if (world.isClient){
                    player.sendMessage(new LiteralText("你不能打开一个幽匿催发体的物品栏界面"));
                }
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
