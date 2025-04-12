package com.blue0666.carpetblueaddition;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarpetBlue implements ModInitializer {
    public static final String MOD_ID = "carpetblueaddition";
    public static final String MOD_NAME = "CarpetBlueAddition";
    private static String VERSION;

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    @Override
    public void onInitialize() {
        VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
        CarpetBlueServer.init();
    }

    public static String getVersion() {
        return VERSION;
    }
}
