package xyz.sncf.b004.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class CustomFireball extends SmallFireball {
    public CustomFireball(Level level, Entity shooter, double x, double y, double z) {
        super(level, (LivingEntity) shooter, x, y, z);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(damageSources().fireball(this, this.getOwner()), 10.0F); // Dégâts amplifiés
    }
}
