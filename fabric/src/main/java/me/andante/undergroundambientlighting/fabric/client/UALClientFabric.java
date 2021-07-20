package me.andante.undergroundambientlighting.fabric.client;

import me.andante.undergroundambientlighting.client.UALClient;
import me.andante.undergroundambientlighting.client.platform.AbstractPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class UALClientFabric implements ClientModInitializer, AbstractPlatform {
    private static final KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key." + UALClient.MOD_ID + ".undergroundambientlighting",
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_I,
            "key.category." + UALClient.MOD_ID
    ));

    public static UALClient core;

    @Override
    public void onInitializeClient() {
        UALClientFabric.core = new UALClient(this);
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }

    private void onClientTick(MinecraftClient client) {
        UALClientFabric.core.onClientTick(client);
    }

    @Override
    public boolean isUALKeyPressed() {
        return keyBinding.isPressed();
    }
}
