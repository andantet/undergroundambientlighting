package me.andante.undergroundambientlighting.mixin.client;

import me.andante.undergroundambientlighting.client.UALClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixin {
    @Shadow @Final private MinecraftClient client;

    @ModifyVariable(method = "update", ordinal = 5, at = @At(value = "STORE", ordinal = 0))
    private float applyUndergroundAmbientLight(float original) {
        if (UALClient.isEnabled()) {
            assert this.client.player != null;
            double playerY = this.client.player.getY();
            int startY = 63;

            if (playerY <= startY) {
                int endY = 32;
                float addedLight = 0.038f;
                original += playerY >= endY
                    ? addedLight * (float) ((Math.cos((playerY - endY) / (Math.abs(startY - endY) / Math.PI)) + 1) / 2)
                    : addedLight;
            }
        }

        return original;
    }
}
