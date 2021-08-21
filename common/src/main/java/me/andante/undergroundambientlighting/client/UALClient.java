package me.andante.undergroundambientlighting.client;

import me.andante.undergroundambientlighting.UndergroundAmbientLighting;
import me.andante.undergroundambientlighting.client.platform.AbstractClientPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.Level;

@Environment(EnvType.CLIENT)
public class UALClient {
    public static AbstractClientPlatform platform;

    private static boolean enabled = true;
    private static boolean keyWasDown = false;

    private static final String TEXT_TOGGLE = "text.undergroundambientlighting.toggle";
    private static final TranslatableText TEXT_TOGGLE_ENABLED = new TranslatableText(TEXT_TOGGLE, new TranslatableText(TEXT_TOGGLE + ".enabled").formatted(Formatting.GRAY));
    private static final TranslatableText TEXT_TOGGLE_DISABLED = new TranslatableText(TEXT_TOGGLE, new TranslatableText(TEXT_TOGGLE + ".disabled").formatted(Formatting.GRAY));

    public UALClient(AbstractClientPlatform platform) {
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
        UndergroundAmbientLighting.LOGGER.log(level, "[{}] {}", UndergroundAmbientLighting.MOD_NAME, message);
    }
}
