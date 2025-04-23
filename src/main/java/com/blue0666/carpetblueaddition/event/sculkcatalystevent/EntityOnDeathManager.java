package com.blue0666.carpetblueaddition.event.sculkcatalystevent;

import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class EntityOnDeathManager {
    public static boolean findSculkCatalystInSphere(LivingEntity entity, World world, int radius) {
        BlockPos entityPos = entity.getBlockPos();
        double radiusSquared = radius * radius;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos checkPos = entityPos
                            .offset(Direction.EAST, x)
                            .offset(Direction.UP, y)
                            .offset(Direction.SOUTH, z);
                    double distanceSquared = checkPos.getSquaredDistance(entityPos.getX(),entityPos.getY(),entityPos.getZ(),false);
                    //需注意方块和实体的坐标总是偏移0.5格，所以这里的另一个平方距离算法会有偏差
                    if (distanceSquared <= radiusSquared) {
                        BlockEntity blockentity = world.getBlockEntity(checkPos);
                        if (blockentity instanceof BarrelBlockEntity){
                            String blockName=((BarrelBlockEntity) blockentity).getDisplayName().getString();
                            if ( "幽匿催发体".equals(blockName) || "sculkcatalyst".equalsIgnoreCase(blockName)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
