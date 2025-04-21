package xyz.sncf.b004.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import xyz.sncf.b004.entity.ExplosiveArrowEntity;

public class ExplosiveArrowItem extends SpecialArrowItem {
    public ExplosiveArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new ExplosiveArrowEntity(level, shooter);
    }

    @Override
    public void onHit(Level level, HitResult hitResult, LivingEntity shooter) {
        // Géré dans l'entité directement
    }
}
