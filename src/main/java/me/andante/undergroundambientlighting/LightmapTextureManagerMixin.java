package me.andante.undergroundambientlighting;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow private boolean dirty;
    @Shadow @Final private NativeImage image;
    @Shadow @Final private NativeImageBackedTexture texture;
    @Shadow protected abstract float method_23795(float f);

    @Shadow private float field_21528;

    @Shadow protected abstract float getBrightness(World world, int i);

    @Shadow @Final private GameRenderer renderer;

    /**
     * @reason lol it looks cool
     * @author Andante & Adammrr
     */
    @Overwrite
    public void update(float delta) {
        if (this.dirty) {
            this.dirty = false;
            this.client.getProfiler().push("lightTex");
            ClientWorld clientWorld = this.client.world;
            if (clientWorld != null) {
                float f = clientWorld.method_23783(1.0F);
                float h;
                if (clientWorld.getLightningTicksLeft() > 0) {
                    h = 1.0F;
                } else {
                    h = f * 0.95F + 0.05F;
                }

                assert this.client.player != null;
                float i = this.client.player.getUnderwaterVisibility();
                float l;
                if (this.client.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                    l = GameRenderer.getNightVisionStrength(this.client.player, delta);
                } else if (i > 0.0F && this.client.player.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
                    l = i;
                } else { // MODIFIED CLAUSE
                    double playerY = this.client.player.getY();
                    double minY = -10.0D;
                    double maxY = 32.0D;

                    if (playerY <= maxY) {
                        l = 0.06F;
                        if (playerY >= minY) {
                            l *= (float) ((Math.cos((playerY - minY) / ((maxY - minY) / Math.PI)) + 1) / 2);
                        }
                    } else { // DEFAULT
                        l = 0.0F;
                    }
                }

                Vec3f vec3f = new Vec3f(f, f, 1.0F);
                vec3f.lerp(new Vec3f(1.0F, 1.0F, 1.0F), 0.35F);
                float m = this.field_21528 + 1.5F;
                Vec3f vec3f2 = new Vec3f();

                for(int n = 0; n < 16; ++n) {
                    for(int o = 0; o < 16; ++o) {
                        float p = this.getBrightness(clientWorld, n) * h;
                        float q = this.getBrightness(clientWorld, o) * m;
                        float s = q * ((q * 0.6F + 0.4F) * 0.6F + 0.4F);
                        float t = q * (q * q * 0.6F + 0.4F);
                        vec3f2.set(q, s, t);
                        float w;
                        Vec3f vec3f5;
                        if (clientWorld.getSkyProperties().shouldBrightenLighting()) {
                            vec3f2.lerp(new Vec3f(0.99F, 1.12F, 1.0F), 0.25F);
                        } else {
                            Vec3f vec3f3 = vec3f.copy();
                            vec3f3.scale(p);
                            vec3f2.add(vec3f3);
                            vec3f2.lerp(new Vec3f(0.75F, 0.75F, 0.75F), 0.04F);
                            if (this.renderer.getSkyDarkness(delta) > 0.0F) {
                                w = this.renderer.getSkyDarkness(delta);
                                vec3f5 = vec3f2.copy();
                                vec3f5.multiplyComponentwise(0.7F, 0.6F, 0.6F);
                                vec3f2.lerp(vec3f5, w);
                            }
                        }

                        vec3f2.clamp(0.0F, 1.0F);
                        float v;
                        if (l > 0.0F) {
                            v = Math.max(vec3f2.getX(), Math.max(vec3f2.getY(), vec3f2.getZ()));
                            if (v < 1.0F) {
                                w = 1.0F / v;
                                vec3f5 = vec3f2.copy();
                                vec3f5.scale(w);
                                vec3f2.lerp(vec3f5, l);
                            }
                        }

                        v = (float)this.client.options.gamma;
                        Vec3f vec3f6 = vec3f2.copy();
                        vec3f6.modify(this::method_23795);
                        vec3f2.lerp(vec3f6, v);
                        vec3f2.lerp(new Vec3f(0.75F, 0.75F, 0.75F), 0.04F);
                        vec3f2.clamp(0.0F, 1.0F);
                        vec3f2.scale(255.0F);
                        int z = (int)vec3f2.getX();
                        int aa = (int)vec3f2.getY();
                        int ab = (int)vec3f2.getZ();
                        this.image.setPixelColor(o, n, -16777216 | ab << 16 | aa << 8 | z);
                    }
                }

                this.texture.upload();
                this.client.getProfiler().pop();
            }
        }
    }
}
