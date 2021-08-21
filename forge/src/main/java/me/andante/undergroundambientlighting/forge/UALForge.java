package me.andante.undergroundambientlighting.forge;

import me.andante.undergroundambientlighting.UndergroundAmbientLighting;
import me.andante.undergroundambientlighting.forge.client.UALClientForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(UndergroundAmbientLighting.MOD_ID)
public class UALForge {
    public UALForge() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> UALClientForge::new);
    }
}
