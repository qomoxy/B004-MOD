package xyz.sncf.b004.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xyz.sncf.b004.B004;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, B004.MODID);

    public static final RegistryObject<CreativeModeTab> B004_TAB = CREATIVE_MODE_TABS.register("b004_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.IRON_HAMMER.get()))
                    .title(Component.translatable("creativetab.b004_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.IRON_HAMMER.get());
                        pOutput.accept(ModItems.GOLDEN_HAMMER.get());
                        pOutput.accept(ModItems.DIAMOND_HAMMER.get());
                        pOutput.accept(ModItems.NETHERITE_HAMMER.get());
                        pOutput.accept(ModItems.WIZARD_BOOK.get());
                        pOutput.accept(ModItems.HEAL_WAND.get());
                        pOutput.accept(ModItems.MAGIC_MUSHROOM.get());
                        pOutput.accept(ModItems.TELEPORT_CRYSTAL.get());
                        pOutput.accept(ModItems.MAGIC_BOW.get());
                        pOutput.accept(ModItems.ENDER_PEARL_ARROW.get());
                        pOutput.accept(ModItems.EXPLOSIVE_ARROW.get());
                        pOutput.accept(ModItems.SMOKE_ARROW.get());
                        pOutput.accept(ModItems.SMOKE_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.ASSASSIN_CAPE.get());
                        pOutput.accept(ModItems.SMOKE_GRENADE.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
