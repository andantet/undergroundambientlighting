package me.andante.undergroundambientlighting.mixin;

import me.andante.undergroundambientlighting.config.UALConfig;
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
    private float applyUndergroundAmbientLight(float l) {
        if (UALConfig.MISC.enabled.getBoolean()) {
            assert this.client.player != null;
            double playerY = this.client.player.getY();
            int startY = UALConfig.MISC.startY.getInt();

            if (playerY <= startY) {
                int endY = UALConfig.MISC.endY.getInt();
                float addedLight = UALConfig.MISC.intensity.getFloat();
                l += playerY >= endY
                    ? addedLight * (float) ((Math.cos((playerY - endY) / (Math.abs(startY - endY) / Math.PI)) + 1) / 2)
                    : addedLight;
            }
        }

        return l;
    }
}
