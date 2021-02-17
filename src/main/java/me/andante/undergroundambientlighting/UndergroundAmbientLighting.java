package me.andante.undergroundambientlighting;

import me.andante.undergroundambientlighting.config.UALConfigManager;
import net.fabricmc.api.ClientModInitializer;

public class UndergroundAmbientLighting implements ClientModInitializer {
    public static final String MOD_ID = "undergroundambientlighting";

    @Override
    public void onInitializeClient() {
        UALConfigManager.load();
    }
}
