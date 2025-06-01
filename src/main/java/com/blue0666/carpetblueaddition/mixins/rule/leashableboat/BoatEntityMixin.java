package com.blue0666.carpetblueaddition.mixins.rule.leashableboat;

import com.blue0666.carpetblueaddition.interfaces.onLeashingBoat;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//TODO: Fix Leashable boat function
@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity implements onLeashingBoat {
    public BoatEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Unique
    private Entity holdingEntity = null;
    @Unique
    private int holdingEntityId ;
    @Nullable
    @Unique
    private int leashNbt;

    @Unique
    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.isLeashed();
    }

    @Unique
    public void attachLeash(Entity entity, boolean broadcastPacket) {
        if (!this.world.isClient) {
            if (this.holdingEntity != null) {
                this.detachLeash(true, true);
            }

            this.holdingEntity = entity;

            if (entity != null && broadcastPacket) {
                this.sendLeashPacket(entity);
            }
        }
    }

    @Unique
    public void detachLeash(boolean dropLead, boolean sendPacket) {
        if (this.holdingEntity != null) {
            if (!this.world.isClient && dropLead) {
                this.dropItem(Items.LEAD);
            }

            this.holdingEntity = null;

            if (sendPacket) {
                this.sendLeashPacket(null);
            }
        }
    }

    @Unique
    public boolean isLeashed() {
        return this.holdingEntity != null;
    }

    @Unique
    private void sendLeashPacket(Entity entity) {
        if (this.world instanceof ServerWorld serverWorld) {
            serverWorld.getChunkManager().sendToOtherNearbyPlayers((Entity)this, new EntityAttachS2CPacket(((Entity)this), this.holdingEntity));
        }
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!CarpetBlueAdditionSettings.leashableBoat) {
            return;
        }

        ItemStack stack = player.getStackInHand(hand);

        if (stack.getItem() == Items.LEAD && this.canBeLeashedBy(player)) {
            if (!this.world.isClient) {
                this.attachLeash(player, true);
                stack.decrement(1);
            }
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

    @Environment(EnvType.CLIENT)
    @Unique
    @Override
    public void setHoldingEntityId(int id) {
        this.holdingEntityId = id;
        Entity entity = this.world.getEntityById(id);
        this.attachLeash(entity,false);
    }

    @Unique
    @Nullable
    public Entity getHoldingEntity() {
        if (this.holdingEntity == null && this.holdingEntityId != 0 && this.world.isClient) {
            this.holdingEntity = this.world.getEntityById(this.holdingEntityId);
        }

        return this.holdingEntity;
    }
}
