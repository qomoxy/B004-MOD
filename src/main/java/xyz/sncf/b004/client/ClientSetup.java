package xyz.sncf.b004.client;

import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.sncf.b004.client.particle.MagicMushroomParticle;
import xyz.sncf.b004.init.ModParticleTypes;

public class ClientSetup {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.MAGIC_MUSHROOM_PARTICLE.get(), MagicMushroomParticle.Provider::new);
    }
}
