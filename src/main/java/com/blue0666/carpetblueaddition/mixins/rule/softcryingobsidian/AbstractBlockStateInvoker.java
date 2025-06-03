package com.blue0666.carpetblueaddition.mixins.rule.softcryingobsidian;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractBlock.AbstractBlockState.class)
public interface AbstractBlockStateInvoker {
    @Invoker("getBlock")
    Block invokeGetBlock();
}
