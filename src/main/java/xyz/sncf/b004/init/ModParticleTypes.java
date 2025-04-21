package xyz.sncf.b004.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "b004");

    public static final RegistryObject<SimpleParticleType> MAGIC_MUSHROOM_PARTICLE =
            PARTICLES.register("magic_mushroom_particle", () -> new SimpleParticleType(true));
}
