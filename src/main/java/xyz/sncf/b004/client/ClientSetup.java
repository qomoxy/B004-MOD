package xyz.sncf.b004.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.sncf.b004.registry.ModBlocks;
import xyz.sncf.b004.registry.ModEntities;
import xyz.sncf.b004.client.renderer.SummonerBossRenderer;
import xyz.sncf.b004.client.renderer.SkeletonVanguardRenderer;
import xyz.sncf.b004.registry.ModModelLayers;
import xyz.sncf.b004.client.model.SummonerBossModel;

@Mod.EventBusSubscriber(modid = "b004", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Configuration des rendus de blocs
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SMOKE_BLOCK.get(), RenderType.translucent());

        // Enregistrement des renderers d'entités
        EntityRenderers.register(ModEntities.SUMMONER_BOSS.get(), SummonerBossRenderer::new);
        EntityRenderers.register(ModEntities.SKELETON_VANGUARD.get(), SkeletonVanguardRenderer::new);
    }

    // 👉 Cet événement est appelé pour enregistrer les couches de modèle
    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                ModModelLayers.SUMMONER_BOSS_LAYER,
                SummonerBossModel::createBodyLayer
        );
    }
}
