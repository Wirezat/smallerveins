package com.wirezat.smallerveins;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.IntValue MAX_VEIN_SIZE;
    public static final ModConfigSpec.IntValue GEN_CHANCE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("SmallerVeins Configuration");

        MAX_VEIN_SIZE = builder
                .comment(
                        "Maximum vein size for all ores. Veins larger than this are capped to this value.",
                        "From personal experience, Values lower than 4 almost stops spawning since this affects spawn attempts"
                )
                .defineInRange("maxVeinSize", 4, 1, 64);

        GEN_CHANCE = builder
                .comment(
                        "Chance that an ore vein generates at all.",
                        "100 = 100%, 50 = 50%, 0 = 0%"
                )
                .defineInRange("genChance", 100, 0, 100);

        SPEC = builder.build();
    }
}