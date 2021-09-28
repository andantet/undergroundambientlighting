package me.andante.undergroundambientlighting.forge.client;

import me.andante.undergroundambientlighting.UndergroundAmbientLighting;
import me.andante.undergroundambientlighting.client.UALClient;
import me.andante.undergroundambientlighting.client.platform.AbstractClientPlatform;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class UALClientForge implements AbstractClientPlatform {
    private static final KeyBinding keyBinding = new KeyBinding(
        String.format("key.%s.%s", UndergroundAmbientLighting.MOD_ID, UndergroundAmbientLighting.MOD_ID),
        GLFW.GLFW_KEY_I,
        String.format("key.category.%s", UndergroundAmbientLighting.MOD_ID)
    );

    private final UALClient core;

    public UALClientForge() {
        this.core = new UALClient(this);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(keyBinding);
    }

    private void onClientTick(final TickEvent.ClientTickEvent event) {
        this.core.onClientTick(MinecraftClient.getInstance());
    }

    @Override
    public boolean isUALKeyPressed() {
        return keyBinding.isPressed();
    }
}
