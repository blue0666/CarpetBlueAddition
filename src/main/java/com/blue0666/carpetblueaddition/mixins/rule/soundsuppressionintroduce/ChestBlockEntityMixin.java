package com.blue0666.carpetblueaddition.mixins.rule.soundsuppressionintroduce;

import com.blue0666.carpetblueaddition.exception.IAESoundSuppresionException;
import com.blue0666.carpetblueaddition.interfaces.onBlockStateChanged;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.text.Text;
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


@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin extends LootableContainerBlockEntity implements onBlockStateChanged {
    protected ChestBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Shadow
    protected abstract Text getContainerName();

    @Unique
    private static final int LISTENING_RADIUS = 8;//监听半径
    @Unique
    private int soundChannel=0;


    @Unique
    public void updateSoundChannel(){
        if (CarpetBlueAdditionSettings.soundSuppressionIntroduce){
            if ((Object)this instanceof ChestBlockEntity){
                World world = this.getWorld();
                if (world != null && !world.isClient()) {
                    // 获取陷阱箱背部的红石信号强度
                    BlockState state = this.getCachedState();
                    Direction direction = state.get(ChestBlock.FACING).getOpposite();
                    this.soundChannel = world.getEmittedRedstonePower(pos.offset(direction), direction);
                    // 根据红石信号强度设置监听模式
                }
            }
        }
    }

    @Unique
    @Nullable
    public String getBlockName() {
        return this.getDisplayName().getString();
    }

    @Override
    @Unique
    public void canReceiveSound(BlockPos interactPos, int soundLevel) throws IAESoundSuppresionException{
        this.updateSoundChannel();
        String blockName = this.getBlockName();
        if (soundChannel == soundLevel&&("声音抑制器".equals(blockName) || "SoundSuppressor".equalsIgnoreCase(blockName))){
            throw new IAESoundSuppresionException(interactPos, "声音抑制触发");
        }
    }
}
