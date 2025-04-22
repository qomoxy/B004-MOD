package xyz.sncf.b004.registry;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import xyz.sncf.b004.B004;

public class ModModelLayers {
    public static final ModelLayerLocation SUMMONER_BOSS_LAYER =
            new ModelLayerLocation(new ResourceLocation(B004.MODID, "summoner_boss"), "main");

    public static void register() {
        // Appelé pour enregistrer les layers si nécessaire
    }
}