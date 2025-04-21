package xyz.sncf.b004.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import xyz.sncf.b004.item.SmokeArrowItem;
import xyz.sncf.b004.registry.ModEntities;
import xyz.sncf.b004.registry.ModItems;

public class SmokeArrowEntity extends AbstractArrow {

    // Obligatoire pour l'enregistrement
    public SmokeArrowEntity(EntityType<? extends SmokeArrowEntity> type, Level level) {
        super(type, level);
    }

    // Utilisé lors du tir avec createArrow()
    public SmokeArrowEntity(Level level, LivingEntity shooter) {
        super(ModEntities.SMOKE_ARROW.get(), shooter, level);
    }

    @Override
    protected void onHit(HitResult result) {
        if (this.getOwner() instanceof LivingEntity shooter) {
            ((SmokeArrowItem) ModItems.SMOKE_ARROW.get()).onHit(this.level(), result, shooter);
        }
        this.discard();
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.SMOKE_ARROW.get());
    }
}
