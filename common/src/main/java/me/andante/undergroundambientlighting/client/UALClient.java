package me.andante.undergroundambientlighting.client;

import me.andante.undergroundambientlighting.client.platform.AbstractPlatform;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UALClient {
    public static final String MOD_ID = "undergroundambientlighting";
    public static final String MOD_NAME = "Underground Ambient Lighting";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static AbstractPlatform platform;

    private static boolean enabled = true;
    private static boolean keyWasDown = false;

    private static final String TEXT_TOGGLE = "text.undergroundambientlighting.toggle";
    private static final TranslatableText TEXT_TOGGLE_ENABLED = new TranslatableText(TEXT_TOGGLE, new TranslatableText(TEXT_TOGGLE + ".enabled").formatted(Formatting.GRAY));
    private static final TranslatableText TEXT_TOGGLE_DISABLED = new TranslatableText(TEXT_TOGGLE, new TranslatableText(TEXT_TOGGLE + ".disabled").formatted(Formatting.GRAY));

    public UALClient(AbstractPlatform platform) {
        log("Initializing");
        UALClient.platform = platform;
        log("Initialized");
    }

    public void onClientTick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null) return;

        if (platform.isUALKeyPressed() && !UALClient.keyWasDown) {
            keyWasDown = true;
            UALClient.enabled = !UALClient.enabled;

            player.sendMessage(UALClient.enabled ? TEXT_TOGGLE_ENABLED : TEXT_TOGGLE_DISABLED, true);
            player.playSound(UALClient.enabled ? SoundEvents.BLOCK_TRIPWIRE_CLICK_OFF : SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, 0.5f, UALClient.enabled ? 1.0f : 0.75f);
        } else if (!platform.isUALKeyPressed() && UALClient.keyWasDown) {
            UALClient.keyWasDown = false;
        }
    }

    public static boolean isEnabled() {
        return UALClient.enabled;
    }

    public static void log(String message) {
        log(Level.INFO, message);
    }
    public static void log(Level level, String message) {
        LOGGER.log(level, "[{}] {}", MOD_NAME, message);
    }
}
