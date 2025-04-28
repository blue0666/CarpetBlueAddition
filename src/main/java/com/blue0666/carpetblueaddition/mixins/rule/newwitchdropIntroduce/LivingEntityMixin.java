package com.blue0666.carpetblueaddition.mixins.rule.newwitchdropIntroduce;

import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Invoker("getLootContextBuilder")
    protected abstract LootContext.Builder invokeGetLootContextBuilder(boolean causedByPlayer, DamageSource source);


    @Inject(
            method = "dropLoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getLootTable()Lnet/minecraft/util/Identifier;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void injectDropLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        if (CarpetBlueAdditionSettings.newWitchDropIntroduce) {
            if ((LivingEntity)((Object) this) instanceof WitchEntity) {
                // 替换为数据驱动的自定义掉落表
                Identifier customLootTable = new Identifier("custom_loot", "entities/witch");
                LootTable lootTable = ((LivingEntity)(Object) this).world.getServer().getLootManager().getTable(customLootTable);
                LootContext.Builder builder = this.invokeGetLootContextBuilder(causedByPlayer, source);
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), stack -> ((LivingEntity) (Object) this).dropStack(stack));
                ci.cancel();
            }
        }
    }
}
