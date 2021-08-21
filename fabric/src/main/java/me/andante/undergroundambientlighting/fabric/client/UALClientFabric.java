package me.andante.undergroundambientlighting.fabric.client;

import me.andante.undergroundambientlighting.UndergroundAmbientLighting;
import me.andante.undergroundambientlighting.client.UALClient;
import me.andante.undergroundambientlighting.client.platform.AbstractClientPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class UALClientFabric implements ClientModInitializer, AbstractClientPlatform {
    private static final KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key." + UndergroundAmbientLighting.MOD_ID + "." + UndergroundAmbientLighting.MOD_ID,
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_I,
            "key.category." + UndergroundAmbientLighting.MOD_ID
    ));

    public final UALClient core = new UALClient(this);

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }

    private void onClientTick(MinecraftClient client) {
        this.core.onClientTick(client);
    }

    @Override
    public boolean isUALKeyPressed() {
        return keyBinding.isPressed();
    }
}
