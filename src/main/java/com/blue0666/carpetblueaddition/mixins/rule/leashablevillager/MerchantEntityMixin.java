package com.blue0666.carpetblueaddition.mixins.rule.leashablevillager;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractTraderEntity.class)
public abstract class MerchantEntityMixin extends PassiveEntity {
    protected MerchantEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player){
        return CarpetBlueAdditionSettings.leashableVillager;
    }
}
