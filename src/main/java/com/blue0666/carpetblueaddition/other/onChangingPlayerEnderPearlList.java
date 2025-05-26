package com.blue0666.carpetblueaddition.other;

import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Set;

public interface onChangingPlayerEnderPearlList {
    Set<EnderPearlEntity> getEnderPearls() ;
}
