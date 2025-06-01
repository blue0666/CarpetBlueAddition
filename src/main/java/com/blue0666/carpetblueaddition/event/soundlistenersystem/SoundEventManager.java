package com.blue0666.carpetblueaddition.event.soundlistenersystem;

import com.blue0666.carpetblueaddition.interfaces.onBlockStateChanged;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import static com.blue0666.carpetblueaddition.util.BoxAreaHelper.createBoxArea;

public class SoundEventManager {
    private static int getListeningRadius() {
        return CarpetBlueAdditionSettings.soundSuppressionRadius;
    } // 监听半径

    public static void handleBlockSound(World world, BlockPos interactPos, int soundLevel) {
//        Box searchArea = createBoxArea(interactPos, getListeningRadius());
        int Radius =getListeningRadius();
        int minX=interactPos.getX() - Radius;
        int minY=interactPos.getY() - Radius;
        int minZ=interactPos.getZ() - Radius;
        int maxX=interactPos.getX() + Radius;
        int maxY=interactPos.getY() + Radius;
        int maxZ=interactPos.getZ() + Radius;

        BlockPos.stream(minX,minY,minZ,maxX,maxY,maxZ).forEach(pos -> {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof TrappedChestBlockEntity) {
                ((onBlockStateChanged) entity).canReceiveSound(interactPos, soundLevel);
            }
        });
    }

    public static void handleEntitySound(Entity entity, int soundLevel) {
        BlockPos interactPos = new BlockPos(entity.getX() + 0.5, entity.getY(), entity.getZ() + 0.5);
        World world = entity.getEntityWorld();
//        Box searchArea = createBoxArea(blockPos, getListeningRadius());
        int Radius =getListeningRadius();
        int minX=interactPos.getX() - Radius;
        int minY=interactPos.getY() - Radius;
        int minZ=interactPos.getZ() - Radius;
        int maxX=interactPos.getX() + Radius;
        int maxY=interactPos.getY() + Radius;
        int maxZ=interactPos.getZ() + Radius;

        BlockPos.stream(minX,minY,minZ,maxX,maxY,maxZ).forEach(pos -> {
            BlockEntity blockentity = world.getBlockEntity(pos);
            if (blockentity instanceof TrappedChestBlockEntity) {
                ((onBlockStateChanged) blockentity).canReceiveSound(interactPos, soundLevel);
            }
        });
    }
}
