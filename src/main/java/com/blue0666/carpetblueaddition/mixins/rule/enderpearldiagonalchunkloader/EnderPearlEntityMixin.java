package com.blue0666.carpetblueaddition.mixins.rule.enderpearldiagonalchunkloader;

import com.blue0666.carpetblueaddition.event.enderpearlChunkLoader.EnderPearlChunkLoaderHelper;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static java.lang.Math.abs;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMixin extends ThrownItemEntity {
    @Unique
    int i=0,j=0;
    @Unique
    double x=0F,z=0F;
    @Unique private long chunkTicketExpiryTicks = 0L;

    public EnderPearlEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method="tick()V",at = @At("RETURN"))
    public void loadChunk(CallbackInfo ci){
        if (CarpetBlueAdditionSettings.enderpearlDiagonalChunkLoader){
            double x2 = this.getX();
            double z2 = this.getZ();
            int i2=ChunkSectionPos.getSectionCoord(MathHelper.floor(this.getPos().getX()));
            int j2=ChunkSectionPos.getSectionCoord(MathHelper.floor(this.getPos().getZ()));
            if (abs(x-x2)>=0.001&&abs(z-z2)>=0.001){
                if ((i!=i2 ||j!=j2 ||--this.chunkTicketExpiryTicks<=0L) &&this.getOwner() instanceof ServerPlayerEntity){
                    handleChunkLoading(this.getEntityWorld(), new BlockPos(this.getPos()));
                    this.chunkTicketExpiryTicks=40L;
                }
            }
            i=i2;j=j2;x=x2;z=z2;
        }
    }

    @Unique
    private void handleChunkLoading(World world, BlockPos pos) {
        if (!world.isClient) {
            ChunkPos chunkPos = new ChunkPos(pos);
            EnderPearlChunkLoaderHelper.addEnderPearlTicket((ServerWorld) world, chunkPos);
        }
    }
}