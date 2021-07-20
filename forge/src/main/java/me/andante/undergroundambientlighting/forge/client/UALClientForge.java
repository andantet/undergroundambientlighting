package me.andante.undergroundambientlighting.forge.client;

import me.andante.undergroundambientlighting.UndergroundAmbientLightingCore;
import me.andante.undergroundambientlighting.platform.AbstractPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

public class UALClientForge implements AbstractPlatform {
    private static final KeyBinding keyBinding = new KeyBinding(
            "key." + UndergroundAmbientLightingCore.MOD_ID + ".undergroundambientlighting",
            GLFW.GLFW_KEY_I,
            "key.category." + UndergroundAmbientLightingCore.MOD_ID
    );

    private UndergroundAmbientLightingCore core;

    public UALClientForge() {
        this.core = new UndergroundAmbientLightingCore(this);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(keyBinding);
    }

    private void onClientTick(final TickEvent.ClientTickEvent event) {
        this.core.onClientTick(Minecraft.getInstance());
    }

    @Override
    public boolean isUALKeyPressed() {
        return keyBinding.isDown();
    }
}
