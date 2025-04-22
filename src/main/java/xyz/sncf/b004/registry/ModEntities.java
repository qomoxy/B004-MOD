package xyz.sncf.b004.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.sncf.b004.B004;
import xyz.sncf.b004.entity.*;

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

    public static final RegistryObject<EntityType<SmokeGrenadeEntity>> SMOKE_GRENADE =
            ENTITIES.register("smoke_grenade",
                    () -> EntityType.Builder.<SmokeGrenadeEntity>of(SmokeGrenadeEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("smoke_grenade"));

    public static final RegistryObject<EntityType<SummonerBossEntity>> SUMMONER_BOSS =
            ENTITIES.register("summoner_boss",
                    () -> EntityType.Builder.of(SummonerBossEntity::new, MobCategory.MONSTER)
                            .sized(0.8F, 2.4F)
                            .build("summoner_boss"));

    public static final RegistryObject<EntityType<SkeletonVanguardEntity>> SKELETON_VANGUARD =
            ENTITIES.register("skeleton_vanguard",
                    () -> EntityType.Builder.of(SkeletonVanguardEntity::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.99F)
                            .build("skeleton_vanguard"));

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SUMMONER_BOSS.get(), SummonerBossEntity.createAttributes().build());
        event.put(ModEntities.SKELETON_VANGUARD.get(), SkeletonVanguardEntity.createAttributes().build());
    }


    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }
}
