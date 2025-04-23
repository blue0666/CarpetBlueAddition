package com.blue0666.carpetblueaddition.mixins.rule.sculkcatalystintroduce;

import com.blue0666.carpetblueaddition.event.sculkcatalystevent.EntityOnDeathManager;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "dropXp", at = @At("HEAD"), cancellable = true
    )
    protected void dropXpInterrupt(CallbackInfo ci){
        if (CarpetBlueAdditionSettings.sculkCatalystIntroduce){
            if (EntityOnDeathManager.findSculkCatalystInSphere((LivingEntity)(Object)this,this.getEntityWorld(),8)){
                ci.cancel();
            }
        }
    }
}
