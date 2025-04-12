package com.blue0666.carpetblueaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Logger;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;

import java.util.Map;

public class CarpetBlueServer implements CarpetExtension {
    private static final CarpetBlueServer INSTANCE = new CarpetBlueServer();
    public static final String compactName = CarpetBlue.MOD_ID;
    public static final Logger LOGGER = CarpetBlue.LOGGER;
    public static MinecraftServer minecraftServer;

    public static void loadExtension() {
        CarpetServer.manageExtension(new CarpetBlueServer());
    }

    @Override
    public String version() {
        return CarpetBlue.MOD_ID;
    }

    public static CarpetBlueServer getInstance() { return INSTANCE; }

    public static void init() {
        CarpetServer.manageExtension(new CarpetBlueServer());
    }

    @Override
    public void onGameStarted() {
        CarpetBlue.LOGGER.info("Carpet-Blue-Addition started");
        CarpetServer.settingsManager.parseSettingsClass(CarpetBlueAdditionSettings.class);
    }

    @Override
    public void onServerLoaded(MinecraftServer server){
        minecraftServer = server;
    }

}
