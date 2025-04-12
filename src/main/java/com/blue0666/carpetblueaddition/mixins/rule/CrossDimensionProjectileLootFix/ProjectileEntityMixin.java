package com.blue0666.carpetblueaddition.mixins.rule.CrossDimensionProjectileLootFix;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
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

    @Shadow
    private UUID ownerUuid;
    @Unique
    @Nullable
    private Entity owner = null;
    @Inject(
            method = "getOwner",
            at = @At("HEAD"),
            cancellable = true)
    public void getOwnerMixin(CallbackInfoReturnable<Entity> cir) {
        if (CarpetBlueAdditionSettings.CrossDimensionProjectileLootFix) {
            if (this.owner != null && !this.owner.removed) {
                cir.setReturnValue(this.owner);
                cir.cancel();
            } else if (this.ownerUuid != null && this.world instanceof ServerWorld serverWorld) {
                this.owner = serverWorld.getEntity(this.ownerUuid);
                cir.setReturnValue(this.owner);
                cir.cancel();
            } else {
                cir.setReturnValue(null);
                cir.cancel();
            }
        }
    }
}

