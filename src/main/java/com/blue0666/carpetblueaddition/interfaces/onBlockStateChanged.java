package com.blue0666.carpetblueaddition.interfaces;

import net.minecraft.util.math.BlockPos;

public interface onBlockStateChanged {
    void canReceiveSound(BlockPos interactPos, int soundLevel);
}