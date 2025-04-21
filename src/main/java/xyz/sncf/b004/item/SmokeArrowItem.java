package xyz.sncf.b004.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.AABB;
import xyz.sncf.b004.entity.SmokeArrowEntity;

public class SmokeArrowItem extends SpecialArrowItem {

    public SmokeArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new SmokeArrowEntity(level, shooter);
    }

    @Override
    public void onHit(Level level, HitResult hitResult, LivingEntity shooter) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        var pos = hitResult.getLocation();
        double x = pos.x;
        double y = pos.y;
        double z = pos.z;

        // 💨 Brouillard intense : particules en masse
        int smokeCount = 200;
        for (int i = 0; i < smokeCount; i++) {
            double offsetX = (level.random.nextDouble() - 0.5) * 5.0;
            double offsetY = (level.random.nextDouble() - 0.5) * 3.0;
            double offsetZ = (level.random.nextDouble() - 0.5) * 5.0;

            serverLevel.sendParticles(
                    ParticleTypes.LARGE_SMOKE,
                    x + offsetX,
                    y + offsetY,
                    z + offsetZ,
                    1,
                    0, 0, 0,
                    0.01
            );
        }

        // 🌫️ Brouillard au sol
        serverLevel.sendParticles(
                ParticleTypes.CAMPFIRE_COSY_SMOKE,
                x, y, z,
                100,
                2.0, 0.5, 2.0,
                0.02
        );

        // 🔊 Son d'impact
        serverLevel.playSound(
                null,
                x, y, z,
                SoundEvents.CREEPER_PRIMED,
                SoundSource.PLAYERS,
                1.0f,
                0.6f + serverLevel.random.nextFloat() * 0.4f
        );

        // 🐢 Ralentir les entités proches
        double radius = 4.0;
        for (LivingEntity entity : serverLevel.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(x - radius, y - 1, z - radius, x + radius, y + 2, z + radius)
        )) {
            if (entity != shooter) {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
            }
        }
    }
}
