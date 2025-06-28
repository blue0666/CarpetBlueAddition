package com.blue0666.carpetblueaddition.mixins.rule.newwitchdropIntroduce;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Shadow
    protected PlayerEntity attackingPlayer;

    @Shadow
    abstract public long getLootTableSeed();

    @Inject(
            method = "dropLoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getLootTable()Lnet/minecraft/util/Identifier;",
                    shift = At.Shift.BEFORE
            )
    )
    private void injectDropLoot(DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci) {
        if (CarpetBlueAdditionSettings.newWitchDropIntroduce) {
            if ((LivingEntity)((Object) this) instanceof WitchEntity) {
                Identifier customLootTable = new Identifier("custom_loot", "entities/witch");
                LootTable lootTable = ((LivingEntity)(Object) this).getWorld().getServer().getLootManager().getLootTable(customLootTable);

                LootContextParameterSet.Builder builder = (new LootContextParameterSet.Builder((ServerWorld)this.getWorld())).add(LootContextParameters.THIS_ENTITY, this).add(LootContextParameters.ORIGIN, this.getPos()).add(LootContextParameters.DAMAGE_SOURCE, damageSource).addOptional(LootContextParameters.KILLER_ENTITY, damageSource.getAttacker()).addOptional(LootContextParameters.DIRECT_KILLER_ENTITY, damageSource.getSource());
                if (causedByPlayer && this.attackingPlayer != null) {
                    builder = builder.add(LootContextParameters.LAST_DAMAGE_PLAYER, this.attackingPlayer).luck(this.attackingPlayer.getLuck());
                }

                LootContextParameterSet lootContextParameterSet = builder.build(LootContextTypes.ENTITY);
                lootTable.generateLoot(lootContextParameterSet, this.getLootTableSeed(), this::dropStack);

//                LootContext.Builder builder = this.invokeGetLootContextBuilder(causedByPlayer, source);
//                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), stack -> ((LivingEntity) (Object) this).dropStack(stack));
//                ci.cancel();
            }
        }
    }
}
