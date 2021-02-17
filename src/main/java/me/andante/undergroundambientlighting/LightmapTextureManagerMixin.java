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
    private float applyUndergroundAmbientLight(float l) {
        assert this.client.player != null;
        double playerY = this.client.player.getY();
        double maxY = 32.0D;

        if (playerY <= maxY) {
            double minY = -10.0D;

            l = 0.057F;
            if (playerY >= minY) l *= (float) ((Math.cos((playerY - minY) / ((maxY - minY) / Math.PI)) + 1) / 2);
        }

        return l;
    }
}
