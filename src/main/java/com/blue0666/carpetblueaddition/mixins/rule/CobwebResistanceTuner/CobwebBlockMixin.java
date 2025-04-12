package com.blue0666.carpetblueaddition.mixins.rule.CobwebResistanceTuner;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.block.CobwebBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CobwebBlock.class)
public class CobwebBlockMixin {
    @ModifyArgs(
            method = "onEntityCollision",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V"
            )
    )
    public void modifySlowMovementArgs(Args args, BlockState state, World world, BlockPos pos, Entity entity) {
        // 修改 Vec3d 的参数为细雪的值
        if (CarpetBlueAdditionSettings.CobwebResistanceTuner){
            args.set(1, new Vec3d((double)0.9F,(double)1.5F,(double)0.9F));
        }

    }
}

