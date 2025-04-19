package com.blue0666.carpetblueaddition.event.soundlistenersystem;

import com.blue0666.carpetblueaddition.other.onBlockStateChanged;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import static com.blue0666.carpetblueaddition.util.BoxAreaHelper.createBoxArea;

public class SoundEventManager {
    private static final int LISTENING_RADIUS = 16; // 监听半径
    public static void handleBlockSound(World world, BlockPos interactPos, int soundLevel){
        Box searchArea = createBoxArea(interactPos,LISTENING_RADIUS);

        BlockPos.stream(searchArea).forEach(pos -> {
            BlockEntity entity =world.getBlockEntity(pos);
            if (entity instanceof TrappedChestBlockEntity) {
                ((onBlockStateChanged)entity).canReceiveSound(interactPos, soundLevel);
            }
        });
    }
    public static void handleEntitySound(Entity entity, int soundLevel){
        BlockPos blockPos = new BlockPos(entity.getX()+0.5,entity.getY(),entity.getZ()+0.5);
        World world = entity.getEntityWorld();
        Box searchArea = createBoxArea(blockPos,LISTENING_RADIUS);

        BlockPos.stream(searchArea).forEach(pos -> {
            BlockEntity blockentity =world.getBlockEntity(pos);
            if (blockentity instanceof TrappedChestBlockEntity) {
                ((onBlockStateChanged)blockentity).canReceiveSound(blockPos, soundLevel);
            }
        });
    }
}
