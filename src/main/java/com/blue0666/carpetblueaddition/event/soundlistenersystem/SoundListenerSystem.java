package com.blue0666.carpetblueaddition.event.soundlistenersystem;

import com.blue0666.carpetblueaddition.other.onDoorChanged;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import static com.blue0666.carpetblueaddition.util.BoxAreaHelper.createBoxArea;

public class SoundListenerSystem {
    private static final int LISTENING_RADIUS = 16; // 监听半径

    public static void handleDoorOpened(World world, BlockPos interactPos) {
        Box searchArea = createBoxArea(interactPos,LISTENING_RADIUS);

        BlockPos.stream(searchArea).forEach(pos -> {
            BlockEntity entity =world.getBlockEntity(pos);
            if (entity instanceof TrappedChestBlockEntity) {
                ((onDoorChanged)entity).onDoorOpened(interactPos);
            }
        });
    }


    /**
     * 处理门被关闭的事件。
     */
    public static void handleDoorClosed(World world, BlockPos interactPos) {
        Box searchArea = createBoxArea(interactPos,LISTENING_RADIUS);

        BlockPos.stream(searchArea).forEach(pos -> {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof TrappedChestBlockEntity) {
                ((onDoorChanged)entity).onDoorClosed(interactPos);
            }
        });
    }
}
