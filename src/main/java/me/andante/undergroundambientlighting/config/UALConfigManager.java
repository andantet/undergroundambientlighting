package me.andante.undergroundambientlighting.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.andante.undergroundambientlighting.UndergroundAmbientLighting;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class UALConfigManager {
    private static final File FILE = FabricLoader.getInstance().getConfigDir().toFile().toPath().resolve(UndergroundAmbientLighting.MOD_ID + ".json").toFile();

    public static void save() {
        JsonObject jsonObject = new JsonObject();

        UALConfig.MiscGroup MISC = UALConfig.MISC;
        jsonObject.addProperty(MISC.enabled.getId(), MISC.enabled.getBoolean());
        jsonObject.addProperty(MISC.startY.getId(), MISC.startY.getInt());
        jsonObject.addProperty(MISC.endY.getId(), MISC.endY.getInt());
        jsonObject.addProperty(MISC.intensity.getId(), MISC.intensity.getFloat());

        try (PrintWriter out = new PrintWriter(FILE)) {
            out.println(jsonObject.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void load() {
        try {
            String json = new String(Files.readAllBytes(FILE.toPath()));
            if (!json.equals("")) {
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);

                UALConfig.MiscGroup MISC = UALConfig.MISC;
                MISC.enabled.value = jsonObject.getAsJsonPrimitive(MISC.enabled.getId()).getAsBoolean();
                MISC.startY.value = jsonObject.getAsJsonPrimitive(MISC.startY.getId()).getAsInt();
                MISC.endY.value = jsonObject.getAsJsonPrimitive(MISC.endY.getId()).getAsInt();
                MISC.intensity.value = jsonObject.getAsJsonPrimitive(MISC.intensity.getId()).getAsFloat();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
