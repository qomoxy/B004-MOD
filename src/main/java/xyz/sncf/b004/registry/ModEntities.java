package xyz.sncf.b004.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.sncf.b004.B004;
import xyz.sncf.b004.entity.EnderPearlArrowEntity;
import xyz.sncf.b004.entity.ExplosiveArrowEntity;
import xyz.sncf.b004.entity.SmokeArrowEntity;

@Mod.EventBusSubscriber(modid = B004.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, B004.MODID);

    public static final RegistryObject<EntityType<EnderPearlArrowEntity>> ENDER_PEARL_ARROW =
            ENTITIES.register("ender_pearl_arrow",
                    () -> EntityType.Builder.<EnderPearlArrowEntity>of(
                                    EnderPearlArrowEntity::new,
                                    MobCategory.MISC
                            )
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("ender_pearl_arrow"));

    public static final RegistryObject<EntityType<ExplosiveArrowEntity>> EXPLOSIVE_ARROW =
            ENTITIES.register("explosive_arrow",
                    () -> EntityType.Builder.<ExplosiveArrowEntity>of(
                                    ExplosiveArrowEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .build("explosive_arrow"));

    public static final RegistryObject<EntityType<SmokeArrowEntity>> SMOKE_ARROW =
            ENTITIES.register("smoke_arrow",
                    () -> EntityType.Builder.<SmokeArrowEntity>of(
                                    SmokeArrowEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .build("smoke_arrow"));


    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }
}
