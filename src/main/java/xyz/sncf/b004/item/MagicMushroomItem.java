package xyz.sncf.b004.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.sncf.b004.registry.ModEffects;

public class MagicMushroomItem extends Item {

    private static final int NUTRITION = 4;
    private static final float SATURATION_MOD = 0.3f;
    private static final int COOLDOWN_TICKS = 60 * 20; // 60 secondes

    public MagicMushroomItem(int durationSec) {
        super(new Item.Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(NUTRITION)
                        .saturationMod(SATURATION_MOD)
                        .effect(new MobEffectInstance(ModEffects.MAGICMUSHROOM_EFFECT.get(), durationSec * 20, 0, false, false, false), 1.0f)
                        .build()
                )
        );
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        }
        return super.finishUsingItem(stack, world, entity);
    }
}
