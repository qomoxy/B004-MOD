package xyz.sncf.b004.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.entity.EntityType;
import xyz.sncf.b004.registry.ModEntities;

public class ExplosiveArrowEntity extends AbstractArrow {
    public ExplosiveArrowEntity(EntityType<? extends ExplosiveArrowEntity> type, Level level) {
        super(type, level);
    }

    public ExplosiveArrowEntity(Level level, LivingEntity shooter) {
        super(ModEntities.EXPLOSIVE_ARROW.get(), shooter, level);
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.5F, Level.ExplosionInteraction.TNT);
            this.discard();
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY; // Pas récupérable
    }
}
