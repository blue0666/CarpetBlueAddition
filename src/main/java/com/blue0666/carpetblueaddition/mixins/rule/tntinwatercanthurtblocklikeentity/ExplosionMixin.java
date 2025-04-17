package com.blue0666.carpetblueaddition.mixins.rule.tntinwatercanthurtblocklikeentity;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Explosion.class)
public class ExplosionMixin {
    @Mutable
    @Final
    @Shadow
    private Entity entity;

    public ExplosionMixin(@Nullable Entity entity) {
        this.entity = entity;
    }

    @Redirect(
            method = "collectBlocksAndDamageEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/MathHelper;sqrt(D)F"
            )
    )
    public float DistanceRedirect(double value, @Local Entity entity1, @Local Vec3d vec3d) {
        if (CarpetBlueAdditionSettings.tntInWaterCantHurtBlockLikeEntity) {
            boolean b1 = this.entity.isTouchingWater(), b2 = false;
            if (entity1.getType() == EntityType.ARMOR_STAND ||
                    entity1.getType() == EntityType.ITEM ||
                    entity1.getType() == EntityType.ITEM_FRAME) {
                b2 = true;
            }
            if (b1 && b2) {
                return 0F;
            }
        }
        return MathHelper.sqrt(entity1.squaredDistanceTo(vec3d));
    }
}