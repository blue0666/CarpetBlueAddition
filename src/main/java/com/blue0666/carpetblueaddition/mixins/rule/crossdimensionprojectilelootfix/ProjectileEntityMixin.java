package com.blue0666.carpetblueaddition.mixins.rule.crossdimensionprojectilelootfix;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin extends Entity {
    public ProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private Entity cachedOwner;
    @Shadow
    private UUID ownerUuid;

    @Inject(method = "getOwner", at = @At("HEAD"), cancellable = true)
    private void injectGetOwner(CallbackInfoReturnable<Entity> cir) {
        // 如果缓存有效，直接返回
        if (CarpetBlueAdditionSettings.crossDimensionProjectileLootFix){
            if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
                cir.setReturnValue(this.cachedOwner);
            }

            // 如果有 ownerUuid，尝试跨维度查找
            if (this.ownerUuid != null) {
                MinecraftServer server = this.world.getServer();
                if (server != null) {
                    for (ServerWorld dimension : server.getWorlds()) {
                        Entity owner = dimension.getEntity(this.ownerUuid);
                        if (owner != null) {
                            this.cachedOwner = owner; // 更新缓存
                            cir.setReturnValue(owner);
                        }
                    }
                }
            }
        }
    }
}
