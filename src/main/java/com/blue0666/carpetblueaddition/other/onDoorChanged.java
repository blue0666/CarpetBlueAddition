package com.blue0666.carpetblueaddition.other;

import net.minecraft.util.math.BlockPos;

public interface onDoorChanged {
    void onDoorOpened(BlockPos interactPos);
    void onDoorClosed(BlockPos interactPos);
}