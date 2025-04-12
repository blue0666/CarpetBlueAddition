package com.blue0666.carpetblueaddition;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

import java.util.logging.Logger;

public class CarpetBlueClient implements ClientModInitializer {
    private static final CarpetBlueClient INSTANCE = new CarpetBlueClient();
    public static final Logger LOGGER = Logger.getLogger(CarpetBlue.MOD_NAME);

    public static CarpetBlueClient getInstance() {
        return INSTANCE;
    }

    public void onClientTick(MinecraftClient client) {
    }

    @Override
    public void onInitializeClient() {

    }
}
