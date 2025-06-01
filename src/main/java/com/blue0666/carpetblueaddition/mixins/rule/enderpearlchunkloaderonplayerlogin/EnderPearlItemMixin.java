package com.blue0666.carpetblueaddition.mixins.rule.enderpearlchunkloaderonplayerlogin;

import com.blue0666.carpetblueaddition.interfaces.onChangingPlayerEnderPearlList;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.thrown.ThrownEnderpearlEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Set;

@Mixin(EnderPearlItem.class)
public class EnderPearlItemMixin {


    @Inject(
            method = "use",at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            shift = At.Shift.BEFORE)
            ,locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void use(World world, PlayerEntity user, Hand hand,  CallbackInfoReturnable<TypedActionResult<ItemStack>> cir,@Local ThrownEnderpearlEntity enderPearlEntity){
        if (CarpetBlueAdditionSettings.enderpearlChunkLoaderOnPlayerLogin){
            if (user!=null & user instanceof ServerPlayerEntity){
                Set<ThrownEnderpearlEntity> set = ((onChangingPlayerEnderPearlList)user).getEnderPearls();
                set.add(enderPearlEntity);
            }
        }
    }
}
