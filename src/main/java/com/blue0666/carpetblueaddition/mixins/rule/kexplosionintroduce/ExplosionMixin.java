package com.blue0666.carpetblueaddition.mixins.rule.kexplosionintroduce;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.GameRules;
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

    @Shadow @Final private World world;

    @Shadow @Final private Explosion.DestructionType destructionType;

    @Redirect(
            method = "collectBlocksAndDamageEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;isImmuneToExplosion(Lnet/minecraft/world/explosion/Explosion;)Z"
            )
    )
    public boolean isImmuneToExplosionMixin(Entity instance, Explosion explosion, @Local Entity entity){
        if (CarpetBlueAdditionSettings.kExplosionIntroduce) {
            boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
            boolean bl2 = this.entity == null || !this.entity.isTouchingWater();
            boolean bl3 = this.entity == null ||
                    this.entity.getType() != EntityType.BREEZE_WIND_CHARGE &&
                            this.entity.getType() != EntityType.WIND_CHARGE;
            if (bl) {
                return bl2 && bl3;
            } else {
                return !(this.destructionType == Explosion.DestructionType.TRIGGER_BLOCK) && bl2 && bl3;
            }
        }
        return entity.isImmuneToExplosion(explosion);
    }
}
