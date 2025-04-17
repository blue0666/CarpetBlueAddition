package com.blue0666.carpetblueaddition.mixins.rule.soundsuppressionintroduce;

import com.blue0666.carpetblueaddition.exception.IAESoundSuppresionException;
import com.blue0666.carpetblueaddition.other.onDoorChanged;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin extends LootableContainerBlockEntity implements ChestAnimationProgress , onDoorChanged {
    @Shadow
    protected abstract Text getContainerName();

    public ChestBlockEntityMixin(BlockEntityType<?> type) {
        super(type);
    }

    @Unique
    private static final int LISTENING_RADIUS = 8;//监听半径
    @Unique
    private boolean listenForOpen = false;
    @Unique
    private boolean listenForClose = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        World world = this.getWorld();
        if (world != null && !world.isClient()) {
            // 获取陷阱箱背部的红石信号强度
            BlockState state = this.getCachedState();
            Direction direction = state.get(ChestBlock.FACING).getOpposite();
            int redstoneStrength = world.getEmittedRedstonePower(pos.offset(direction), direction);

            // 根据红石信号强度设置监听模式
            if (redstoneStrength == 8) {
                listenForOpen = true;
                listenForClose = false;
            } else if (redstoneStrength == 9) {
                listenForOpen = false;
                listenForClose = true;
            } else {
                listenForOpen = true;
                listenForClose = true;
            }
        }
    }

    /**
     * 处理门被打开的事件。
     */
    @Override
    @Unique
    public void onDoorOpened(BlockPos interactPos) throws IAESoundSuppresionException {
        String blockName = this.getBlockName();
        if (listenForOpen && ("声音抑制器".equals(blockName) || "SoundSuppressor".equalsIgnoreCase(blockName))) {
            throw new IAESoundSuppresionException(interactPos, "声音抑制触发");
        }
    }

    /**
     * 处理门被关闭的事件。
     */
    @Override
    @Unique
    public void onDoorClosed(BlockPos interactPos) throws IAESoundSuppresionException {
        String blockName = this.getBlockName();
        if (listenForClose && ("声音抑制器".equals(blockName) || "SoundSuppressor".equalsIgnoreCase(blockName))) {
            throw new IAESoundSuppresionException(interactPos, "声音抑制触发");
        }
    }

    @Unique
    @Nullable
    public String getBlockName() {
        return this.getDisplayName().getString();
    }
}
