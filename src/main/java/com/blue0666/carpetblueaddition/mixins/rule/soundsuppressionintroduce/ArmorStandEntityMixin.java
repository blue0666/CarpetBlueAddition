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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntity.class)
abstract public class ArmorStandEntityMixin {
    @Shadow
    abstract public ItemStack getEquippedStack(EquipmentSlot slot);

    @Unique
    ItemStack itemStackArmorStand;

    /**
     * 注意到这个equipStack方法将盔甲架上方的物品覆写为手上的，但是手上物品的修改是在整个方法结束后，在后续的另一个setStackInHand中进行的，所以
     * 如果需要制作物品分身，盔甲架和手上物品的判定是在一开始进行的(Head)而打断必须在两个方法中间(即盔甲架修改的RETURN后,setStackInHand的HEAD前)
     */
    @Inject(method = "equipStack", at = @At(value="HEAD"))
    public void getItemOnArmorStandFirst(EquipmentSlot slot, ItemStack stack, CallbackInfo ci){
        if (CarpetBlueAdditionSettings.soundSuppressionIntroduce){
            itemStackArmorStand=this.getEquippedStack(slot);
        }
    }

    @Inject(method = "equipStack", at = @At(value="RETURN"))
    public void getInteractionResult(EquipmentSlot slot, ItemStack stack, CallbackInfo ci){
        if (CarpetBlueAdditionSettings.soundSuppressionIntroduce){
            if (!stack.isEmpty() && itemStackArmorStand.isEmpty()){
                SoundEventManager.handleEntitySound((Entity)(Object)this, SoundEvent.ARMOR_EQUIP);
            }
        }
    }
}
