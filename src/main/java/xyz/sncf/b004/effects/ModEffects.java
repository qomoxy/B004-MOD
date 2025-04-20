package xyz.sncf.b004.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.sncf.b004.B004;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "b004");

    public static final RegistryObject<MobEffect> MAGICMUSHROOM_EFFECT =
            EFFECTS.register("magicmushroom_effect", MagicMushroomEffect::new);

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }
}
