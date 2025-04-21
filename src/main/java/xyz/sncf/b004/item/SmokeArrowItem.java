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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import xyz.sncf.b004.block.SmokeBomb;
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

        serverLevel.playSound(
                null,
                x, y, z,
                SoundEvents.FIRE_EXTINGUISH,
                SoundSource.PLAYERS,
                3.0f,
                0.4f + serverLevel.random.nextFloat() * 0.4f
        );
    }
}
