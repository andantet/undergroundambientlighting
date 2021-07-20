package me.andante.undergroundambientlighting.forge;

import me.andante.undergroundambientlighting.UndergroundAmbientLightingCore;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(UndergroundAmbientLightingCore.MOD_ID)
public class UALForge {
    public UALForge() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> UndergroundAmbientLightingForgeClient::new);
    }
}
