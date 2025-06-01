package com.blue0666.carpetblueaddition.mixins.rule.enderpearlchunkloaderonplayerlogin;

import com.blue0666.carpetblueaddition.CarpetBlue;
import com.blue0666.carpetblueaddition.event.enderpearlChunkLoader.EnderPearlChunkLoaderHelper;
import com.blue0666.carpetblueaddition.interfaces.onChangingPlayerEnderPearlList;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.types.templates.CompoundList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.thrown.ThrownEnderpearlEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ServerPlayerEntity.class)
abstract public class ServerPlayerEntityMixin extends PlayerEntity implements onChangingPlayerEnderPearlList {
    @Shadow public abstract ServerWorld getServerWorld();

    public ServerPlayerEntityMixin(World world, GameProfile profile) {
        super(world, profile);
    }

    @Unique
    public Set<ThrownEnderpearlEntity> getEnderPearls() {
        return this.enderPearls;
    }

    @Unique
    public void setEnderPearls(Set<ThrownEnderpearlEntity> enderPearls) {
        this.enderPearls = enderPearls;
    }

    @Unique
    public Set<ThrownEnderpearlEntity> enderPearls = new HashSet<>();


    @Inject(method = "writeCustomDataToTag",at = @At("RETURN"))
    public void writeCustomDataToTagMixin(CompoundTag tag, CallbackInfo ci) {
        if (CarpetBlueAdditionSettings.enderpearlChunkLoaderOnPlayerLogin && !this.enderPearls.isEmpty()) {
            ListTag pearlsNbt = new ListTag();

            for (ThrownEnderpearlEntity pearl : this.enderPearls) {
                if (pearl.removed) {
                    continue;
                }
                CompoundTag pearlTag = new CompoundTag();
                pearl.toTag(pearlTag);

                // 获取维度名称（字符串形式）
                Identifier dimensionId = Registry.DIMENSION_TYPE.getId(pearl.getEntityWorld().getDimension().getType());
                if (dimensionId != null) {
                    pearlTag.putString("ender_pearl_dimension", dimensionId.toString());
                }
                pearlsNbt.add(pearlTag);
            }

            tag.put("EnderPearls", pearlsNbt);
        }
    }

    @Inject(method = "readCustomDataFromTag",at = @At("RETURN"))
    public void readCustomDataFromTagMixin(CompoundTag nbt, CallbackInfo ci){
//        System.out.println(nbt);
        if (CarpetBlueAdditionSettings.enderpearlChunkLoaderOnPlayerLogin && nbt.contains("EnderPearls", 9)) {
            ListTag pearlsNbt = nbt.getList("EnderPearls", 10);

            for (int i = 0; i < pearlsNbt.size(); i++) {
                CompoundTag pearlTag = pearlsNbt.getCompound(i);
                String dimensionStr = pearlTag.getString("ender_pearl_dimension");
                if (dimensionStr.isEmpty()) {
                    continue;
                }
                Identifier dimensionId = new Identifier(dimensionStr);
                MinecraftServer server = this.getServerWorld().getServer();
                ServerWorld serverWorld = server.getWorld(Registry.DIMENSION_TYPE.get(dimensionId));
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
