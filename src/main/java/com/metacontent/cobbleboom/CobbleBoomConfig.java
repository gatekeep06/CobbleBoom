package com.metacontent.cobbleboom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Map;

public class CobbleBoomConfig {
    public final Map<String, Float> moves;

    private CobbleBoomConfig(Map<String, Float> moveIds) {
        this.moves = moveIds;
    }

    private CobbleBoomConfig() {
        this(Map.of("explosion", 10f, "selfdestruct", 10f,"mistyexplosion", 10f));
    }

    public static CobbleBoomConfig init() {
        CobbleBoom.LOGGER.info("Initializing " + CobbleBoom.ID + " config");
        CobbleBoomConfig config;
        Gson gson = (new GsonBuilder()).disableHtmlEscaping().setPrettyPrinting().create();
        File file = new File(FabricLoader.getInstance().getConfigDir() + "/" + CobbleBoom.ID + ".json");
        file.getParentFile().mkdirs();
        if (file.exists()) {
            try (FileReader fileReader = new FileReader(file)) {
                config = gson.fromJson(fileReader, CobbleBoomConfig.class);
            }
            catch (Throwable throwable) {
                CobbleBoom.LOGGER.error(throwable.getMessage(), throwable);
                config = new CobbleBoomConfig();
            }
        }
        else {
            config = new CobbleBoomConfig();
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            gson.toJson(config, printWriter);
        }
        catch (Throwable throwable) {
            CobbleBoom.LOGGER.error(throwable.getMessage(), throwable);
        }

        return config;
    }
}
