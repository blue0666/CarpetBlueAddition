package com.blue0666.carpetblueaddition.mixins.rule.kexplosionintroduce;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Explosion.class)
public class ExplosionMixin{
    @Mutable
    @Final
    @Shadow
    private Entity entity;

    @Redirect(
            method = "collectBlocksAndDamageEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;isImmuneToExplosion()Z"
            )
    )
    public boolean isImmuneToExplosionMixin(Entity instance, @Local Entity entity){
        if (CarpetBlueAdditionSettings.kExplosionIntroduce) {
            boolean b1 = this.entity.isTouchingWater(), b2 = false;
            if (entity.getType() == EntityType.ARMOR_STAND ||
                    entity.getType() == EntityType.ITEM ||
                    entity.getType() == EntityType.ITEM_FRAME) {
                b2 = true;
            }
            return b1 && b2;
        }
        return entity.isImmuneToExplosion();
    }
}
