package com.blue0666.carpetblueaddition.mixins.rule.soundsuppressionintroduce;

import com.blue0666.carpetblueaddition.event.soundlistenersystem.SoundEvent;
import com.blue0666.carpetblueaddition.event.soundlistenersystem.SoundEventManager;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntity.class)
public class ArmorStandEntityMixin {
    @Inject(method = "equipStack", at = @At(value="RETURN"))
    public void emitSoundEvent(EquipmentSlot slot, ItemStack stack, CallbackInfo ci){
        if (CarpetBlueAdditionSettings.soundSuppressionIntroduce){
            Item item = stack.getItem();
            SoundEventManager.handleEntitySound((Entity)(Object)this, (item instanceof ArmorItem ? SoundEvent.ARMOR_EQUIP:SoundEvent.ARMOR_OTHER));
        }
    }
}
