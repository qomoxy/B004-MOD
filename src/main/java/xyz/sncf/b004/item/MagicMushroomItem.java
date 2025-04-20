package xyz.sncf.b004.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import xyz.sncf.b004.effects.ModEffects;

public class MagicMushroomItem extends Item {

    public MagicMushroomItem(int nutrition, float saturationMod, int durationSec) {
        super(new Item.Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(nutrition)
                        .saturationMod(saturationMod)
                        .effect(new MobEffectInstance(ModEffects.MAGICMUSHROOM_EFFECT.get(), durationSec*20, 0, false, false, false), 1)
                        .build()
                )
        );
    }
}
