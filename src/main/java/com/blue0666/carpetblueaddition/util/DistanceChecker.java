package com.blue0666.carpetblueaddition.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class DistanceChecker {
    public static boolean isEntityWithinRange(LivingEntity entity, BlockPos blockPos, double maxDistance) {
        Vec3d entityPos = entity.getPos();
        Vec3d blockCenter = new Vec3d(
                blockPos.getX() + 0.5,
                blockPos.getY() + 0.5,
                blockPos.getZ() + 0.5
        );

        double distance = entityPos.distanceTo(blockCenter);

        return distance <= (maxDistance * maxDistance);
    }
}
