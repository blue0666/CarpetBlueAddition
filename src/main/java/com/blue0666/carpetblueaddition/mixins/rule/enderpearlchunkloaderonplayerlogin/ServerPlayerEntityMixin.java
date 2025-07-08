package com.blue0666.carpetblueaddition.mixins.rule.enderpearlchunkloaderonplayerlogin;

import com.blue0666.carpetblueaddition.CarpetBlue;
import com.blue0666.carpetblueaddition.event.enderpearlChunkLoader.EnderPearlChunkLoaderHelper;
import com.blue0666.carpetblueaddition.interfaces.onChangingPlayerEnderPearlList;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ServerPlayerEntity.class)
abstract public class ServerPlayerEntityMixin extends PlayerEntity implements onChangingPlayerEnderPearlList {
    @Unique
    public Set<EnderPearlEntity> getEnderPearls() {
        return this.enderPearls;
    }

    @Unique
    public void setEnderPearls(Set<EnderPearlEntity> enderPearls) {
        this.enderPearls = enderPearls;
    }

    @Unique
    public Set<EnderPearlEntity> enderPearls = new HashSet<>();

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "writeCustomDataToNbt",at = @At("RETURN"))
    public void writeCustomDataToNbtMixin(NbtCompound nbt, CallbackInfo ci){
        if (CarpetBlueAdditionSettings.enderpearlChunkLoaderOnPlayerLogin && !this.enderPearls.isEmpty()) {
            NbtList pearlsNbt = new NbtList();

            for (EnderPearlEntity pearl : this.enderPearls) {
                if (pearl.isRemoved()){
                    CarpetBlue.LOGGER.warn(
                            "Trying to write a removed ender pearl for player " +this.getName().toString()+", skipping"
                    );
                }
                else{
                    NbtCompound pearlTag = new NbtCompound();
                    pearl.saveNbt(pearlTag);
                    NbtElement worldNbt = World.CODEC.encodeStart(NbtOps.INSTANCE, pearl.getWorld().getRegistryKey()).getOrThrow();
                    pearlTag.put("ender_pearl_dimension", worldNbt);
                    pearlsNbt.add(pearlTag);
                }
            }
            // 将末影珍珠列表存入 NBT
            nbt.put("EnderPearls", pearlsNbt);
        }
    }

    @Inject(method = "readCustomDataFromNbt",at = @At("RETURN"))
    public void readCustomDataFromNbtMixin(NbtCompound nbt, CallbackInfo ci){
        if (CarpetBlueAdditionSettings.enderpearlChunkLoaderOnPlayerLogin && nbt.contains("EnderPearls", 9)) {
            NbtList pearlsNbt = nbt.getList("EnderPearls", 10);

            for (int i = 0; i < pearlsNbt.size(); i++) {
                NbtCompound pearlTag = pearlsNbt.getCompound(i);
                RegistryKey<World> world = World.CODEC.parse(NbtOps.INSTANCE, pearlTag.get("ender_pearl_dimension")).getOrThrow();
                ServerWorld serverWorld = ((ServerPlayerEntity)(Object)this).getEntityWorld().getServer().getWorld(world);
                //从世界的未加载区块中尝试唤醒珍珠
                if (serverWorld!=null){
                    Entity entity = EntityType.loadEntityWithPassengers(
                            pearlTag, serverWorld, entity1 -> entity1
                    );
                    if (entity != null) {
                        EnderPearlChunkLoaderHelper.addEnderPearlTicket(serverWorld, entity);
                        serverWorld.spawnEntity(entity);
                    } else {
                        CarpetBlue.LOGGER.warn(
                                "Failed to spawn player ender pearl in level ({}), skipping",
                                world
                        );
                    }
                } else {
                    CarpetBlue.LOGGER.warn(
                            "Trying to load ender pearl without level ({}) being loaded, skipping",
                            world
                    );
                }
            }
        }
    }
}