package com.blue0666.carpetblueaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.utils.Translations;
import com.blue0666.carpetblueaddition.settings.CarpetBlueAdditionSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class CarpetBlueServer implements CarpetExtension {
    private static final CarpetBlueServer INSTANCE = new CarpetBlueServer();
    public static final String compactName = CarpetBlue.MOD_ID;
    public static final Logger LOGGER = CarpetBlue.LOGGER;
    public static MinecraftServer minecraftServer;

    static {
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
        CarpetServer.settingsManager.parseSettingsClass(CarpetBlueAdditionSettings.class);
    }

    @Override
    public void onServerLoaded(MinecraftServer server){
        minecraftServer = server;
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        String dataJSON;
        try {
            dataJSON = IOUtils.toString(
                    Objects.requireNonNull(Translations.class.getClassLoader().getResourceAsStream(
                            String.format("assets/carpetblueaddition/lang/%s.json", lang))),
                    StandardCharsets.UTF_8);
        } catch (NullPointerException | IOException e) {
            return null;
        }
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.fromJson(dataJSON, new TypeToken<Map<String, String>>() {
        }.getType());
    }

}
