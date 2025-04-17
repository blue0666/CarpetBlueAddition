package com.blue0666.carpetblueaddition.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class BoxAreaHelper {
    public static Box createBoxArea(BlockPos bLockPos , int radius){
        return new Box(
                bLockPos.getX() - radius,
                bLockPos.getY() - radius,
                bLockPos.getZ() - radius,
                bLockPos.getX() + radius,
                bLockPos.getY() + radius,
                bLockPos.getZ() + radius
        );
    }
}
