package com.blue0666.carpetblueaddition.event.enderpearlChunkLoader;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import java.util.Comparator;

public class EnderPearlChunkLoaderHelper {
    private static final ChunkTicketType<ChunkPos> ENDER_PEARL_TICKET_TYPE = createChunkTicketType("ender_pearl");
    private static ChunkTicketType<ChunkPos> createChunkTicketType(String type) {
        return ChunkTicketType.create(type, Comparator.comparingLong(ChunkPos::toLong), 40);
    }

    public static void addEnderPearlTicket(ServerWorld world, ChunkPos chunkPos) {
        addTicket(world, chunkPos, ENDER_PEARL_TICKET_TYPE);
    }

    private static void addTicket(ServerWorld world, ChunkPos chunkPos, ChunkTicketType<ChunkPos> ticketType) {
        world.getChunkManager().addTicket(ticketType, chunkPos, 2, chunkPos);
    }
}
