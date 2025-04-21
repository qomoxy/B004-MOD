package xyz.sncf.b004;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import xyz.sncf.b004.client.ClientEvents;
import xyz.sncf.b004.registry.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(B004.MODID)
public class B004 {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "b004";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "b004" namespace


    public B004() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModCreativeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModEffects.register(modEventBus);
        ModParticleTypes.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register();
        modEventBus.addListener(this::commonSetup);
        modEventBus.register(xyz.sncf.b004.client.ClientModEvents.class);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.DIAMOND_HAMMER);
            event.accept(ModItems.NETHERITE_HAMMER);
            event.accept(ModItems.GOLDEN_HAMMER);
            event.accept(ModItems.IRON_HAMMER);
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
