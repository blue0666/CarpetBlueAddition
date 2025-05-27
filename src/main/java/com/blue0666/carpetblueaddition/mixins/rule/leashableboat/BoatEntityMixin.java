package com.blue0666.carpetblueaddition.mixins.rule.leashableboat;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity{
    public BoatEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Unique
    private Entity holdingEntity = null;

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
            System.out.println("拴实体发包发送");
            serverWorld.getChunkManager().sendToOtherNearbyPlayers((Entity)this, new EntityAttachS2CPacket(((Entity)this), this.holdingEntity));
        }
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        System.out.println(holdingEntity);
        if (!CarpetBlueAdditionSettings.leashableBoat) {
            return;
        }

        ItemStack stack = player.getStackInHand(hand);

        if (stack.getItem() == Items.LEAD && this.canBeLeashedBy(player)) {
            if (!this.world.isClient) {
                this.attachLeash(player, true);
                stack.decrement(1);
                System.out.println("栓绳拴船");
            }
            cir.setReturnValue(ActionResult.success(this.world.isClient));
        }
    }
}
