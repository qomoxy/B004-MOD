package xyz.sncf.b004.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.sncf.b004.B004;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, B004.MODID);

    // Hammers
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new HammerItem(Tiers.IRON, 5, -3.2F, new Item.Properties().durability(250)));

    public static final RegistryObject<Item> GOLDEN_HAMMER = ITEMS.register("golden_hammer",
            () -> new HammerItem(Tiers.GOLD, 3, -2.8F, new Item.Properties().durability(32)));

    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new HammerItem(Tiers.DIAMOND, 7, -3.0F, new Item.Properties().durability(1561)));

    public static final RegistryObject<Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer",
            () -> new HammerItem(Tiers.NETHERITE, 9, -3.0F, new Item.Properties().durability(2031).fireResistant()));

    public static final RegistryObject<Item> HEAL_WAND = ITEMS.register("heal_wand",
            () -> new HealWandItem(Tiers.IRON, new Item.Properties().durability(100), 5, 5));

    public static final RegistryObject<Item> MAGIC_MUSHROOM = ITEMS.register("magic_mushroom",
            () -> new MagicMushroomItem(4, 0.3f, 30));

    public static final RegistryObject<Item> WIZARD_BOOK = ITEMS.register("wizard_book",
            () -> new WizardBookItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
