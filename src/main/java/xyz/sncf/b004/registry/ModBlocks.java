package xyz.sncf.b004.registry;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import xyz.sncf.b004.block.SmokeBlock;

@Mod.EventBusSubscriber(modid = "b004", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    // Initialisation du DeferredRegister pour les blocs
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "b004");

    // Enregistrement du bloc de fumée
    public static final RegistryObject<Block> SMOKE_BLOCK = BLOCKS.register("smoke_block", SmokeBlock::new);

    // Méthode pour enregistrer le DeferredRegister
    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
