package me.andante.undergroundambientlighting;

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
    private float applyUndergroundAmbientLight(float garbage) {
        double playerY = this.client.player.getY();
        double minY = -10.0D;
        double maxY = 32.0D;

        float l = 0.0F; // Default
        if (playerY <= maxY) {
            l = 0.06F;
            if (playerY >= minY) {
                l *= (float) ((Math.cos((playerY - minY) / ((maxY - minY) / Math.PI)) + 1) / 2);
            }
        }

        return l;
    }
}
