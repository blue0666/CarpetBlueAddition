package com.blue0666.carpetblueaddition.mixins.rule.enderpearlchunkloaderonplayerlogin;

import com.blue0666.carpetblueaddition.event.enderpearlChunkLoader.EnderPearlChunkLoaderHelper;
import com.blue0666.carpetblueaddition.other.onChangingPlayerEnderPearlList;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;
import java.util.UUID;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract World getEntityWorld();

    @Inject(method = "remove", at = @At("RETURN"))
    public void removeEnderPearlFromList(CallbackInfo ci) {
        if ((Object) this instanceof EnderPearlEntity pearl) {
            if (CarpetBlueAdditionSettings.enderpearlChunkLoaderOnPlayerLogin) {
                if (pearl.getOwner() != null) {
                    Set<EnderPearlEntity> set = ((onChangingPlayerEnderPearlList) pearl.getOwner()).getEnderPearls();
                    set.remove(pearl);
                }
            }
        }
    }
}
