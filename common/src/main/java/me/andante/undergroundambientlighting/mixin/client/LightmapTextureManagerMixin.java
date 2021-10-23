package me.andante.undergroundambientlighting.mixin.client;

import me.andante.undergroundambientlighting.client.UALClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "getBrightness", at = @At("RETURN"), cancellable = true)
    private void applyUndergroundAmbientLight(World world, int lightLevel, CallbackInfoReturnable<Float> cir) {
        if (this.client.player != null && UALClient.isEnabled() && lightLevel <= 1 && this.client.player.world.getRegistryKey() == World.OVERWORLD) {
            double playerY = this.client.player.getY();
            int startY = 63;

            if (playerY <= startY) {
                int endY = 32;
                float addedLight = 0.038f;
                cir.setReturnValue(cir.getReturnValueF() +
                    playerY >= endY
                        ? addedLight * (float) ((Math.cos((playerY - endY) / (Math.abs(startY - endY) / Math.PI)) + 1) / 2)
                        : addedLight
                );
            }
        }
    }
}
