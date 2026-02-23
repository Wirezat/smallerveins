package com.wirezat.smallerveins.mixin;

import com.wirezat.smallerveins.Config;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OreFeature.class)
public class OreFeatureMixin {

    @Inject(method = "place", at = @At("HEAD"), cancellable = true)
    private void smallerveins$onPlace(FeaturePlaceContext<OreConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
        int genChance = Config.GEN_CHANCE.get();

        if (genChance <= 0) {
            cir.setReturnValue(false);
            return;
        }

        if (genChance < 100) {
            int rarity = (int) Math.round(100.0 / genChance);
            if (context.random().nextInt(Math.max(1, rarity)) != 0) {
                cir.setReturnValue(false);
            }
        }
    }

    @ModifyVariable(method = "place", at = @At("HEAD"), argsOnly = true)
    private FeaturePlaceContext<OreConfiguration> smallerveins$modifySize(FeaturePlaceContext<OreConfiguration> context) {
        int maxSize = Config.MAX_VEIN_SIZE.get();
        OreConfiguration config = context.config();

        if (config.size <= maxSize) return context;

        OreConfiguration newConfig = new OreConfiguration(config.targetStates, maxSize);
        return new FeaturePlaceContext<>(
                context.topFeature(),
                context.level(),
                context.chunkGenerator(),
                context.random(),
                context.origin(),
                newConfig
        );
    }
}