package me.andante.undergroundambientlighting;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.andante.undergroundambientlighting.config.UALConfig;
import me.andante.undergroundambientlighting.config.UALConfigManager;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class UALModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parentScreen -> {
            ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setDefaultBackgroundTexture(new Identifier("textures/block/stone.png"))
                .setTitle(createConfigText("title"))
                .setSavingRunnable(UALConfigManager::save);

            builder.setGlobalized(true);
            builder.setGlobalizedExpanded(false);

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            //
            // MISC CATEGORY
            //

            ConfigCategory MISC = builder.getOrCreateCategory(createMiscText());

            TranslatableText enabled = createMiscText(UALConfig.MISC.enabled.getId());
            TranslatableText startY = createMiscText(UALConfig.MISC.startY.getId());
            TranslatableText endY = createMiscText(UALConfig.MISC.endY.getId());
            TranslatableText intensity = createMiscText(UALConfig.MISC.intensity.getId());
            MISC.addEntry(
                entryBuilder.startBooleanToggle(enabled, UALConfig.MISC.enabled.getBoolean())
                    .setDefaultValue(UALConfig.MISC.enabled.getDefaultBoolean())
                    .setSaveConsumer(value -> UALConfig.MISC.enabled.value = value)
                    .setTooltip(createTooltip(enabled))
                    .build()
            ).addEntry(
                entryBuilder.startIntField(startY, UALConfig.MISC.startY.getInt())
                    .setDefaultValue(UALConfig.MISC.startY.getDefaultInt())
                    .setMin(UALConfig.MISC.startY.getMinInt())
                    .setMax(UALConfig.MISC.startY.getMaxInt())
                    .setSaveConsumer(value -> UALConfig.MISC.startY.value = value)
                    .setTooltip(createTooltip(startY))
                    .build()
            ).addEntry(
                entryBuilder.startIntField(endY, UALConfig.MISC.endY.getInt())
                    .setDefaultValue(UALConfig.MISC.endY.getDefaultInt())
                    .setMin(UALConfig.MISC.endY.getMinInt())
                    .setMax(UALConfig.MISC.endY.getMaxInt())
                    .setSaveConsumer(value -> UALConfig.MISC.endY.value = value)
                    .setTooltip(createTooltip(endY))
                    .build()
            ).addEntry(
                entryBuilder.startFloatField(intensity, UALConfig.MISC.intensity.getFloat())
                    .setDefaultValue(UALConfig.MISC.intensity.getDefaultFloat())
                    .setMin(UALConfig.MISC.intensity.getMinFloat())
                    .setMax(UALConfig.MISC.intensity.getMaxFloat())
                    .setSaveConsumer(value -> UALConfig.MISC.intensity.value = value)
                    .setTooltip(createTooltip(intensity))
                    .build()
            );

            return builder.build();
        };
    }

    private TranslatableText createTooltip(TranslatableText text) {
        return new TranslatableText(text.getKey() + ".tooltip");
    }
    private TranslatableText createMiscText(String label) {
        return createCatText("misc" + (label.isEmpty() ? "" : "." + label));
    }
    private TranslatableText createMiscText() {
        return createMiscText("");
    }
    private TranslatableText createCatText(String group) {
        return createConfigText("category." + group);
    }
    private TranslatableText createConfigText(String label) {
        return new TranslatableText("config." + UndergroundAmbientLighting.MOD_ID + "." + label);
    }
}
