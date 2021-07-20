package me.andante.undergroundambientlighting.fabric.client;

import me.andante.undergroundambientlighting.client.UAL;
import me.andante.undergroundambientlighting.client.platform.AbstractPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class UALFabricClient implements ClientModInitializer, AbstractPlatform {
    private static final KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key." + UAL.MOD_ID + ".undergroundambientlighting",
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_I,
            "key.category." + UAL.MOD_ID
    ));

    public static UAL core;

    @Override
    public void onInitializeClient() {
        UALFabricClient.core = new UAL(this);
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }

    private void onClientTick(MinecraftClient client) {
        UALFabricClient.core.onClientTick(client);
    }

    @Override
    public boolean isUALKeyPressed() {
        return keyBinding.isPressed();
    }
}
