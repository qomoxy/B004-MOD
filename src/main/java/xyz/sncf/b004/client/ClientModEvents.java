package xyz.sncf.b004.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.sncf.b004.B004;
import xyz.sncf.b004.client.particle.MagicMushroomParticle;
import xyz.sncf.b004.client.renderer.EnderPearlArrowRenderer;
import xyz.sncf.b004.client.renderer.ExplosiveArrowRenderer;
import xyz.sncf.b004.client.renderer.SmokeArrowRenderer;
import xyz.sncf.b004.init.ModParticleTypes;
import xyz.sncf.b004.registry.ModEntities;

@Mod.EventBusSubscriber(modid = B004.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.MAGIC_MUSHROOM_PARTICLE.get(), MagicMushroomParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.ENDER_PEARL_ARROW.get(), EnderPearlArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.EXPLOSIVE_ARROW.get(), ExplosiveArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.SMOKE_ARROW.get(), SmokeArrowRenderer::new);
    }
}
