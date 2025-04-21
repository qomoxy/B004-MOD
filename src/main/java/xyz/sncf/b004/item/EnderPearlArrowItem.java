package xyz.sncf.b004.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import xyz.sncf.b004.entity.EnderPearlArrowEntity;

public class EnderPearlArrowItem extends SpecialArrowItem {

    public EnderPearlArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onHit(Level level, HitResult hitResult, LivingEntity shooter) {
        if (!level.isClientSide && shooter != null) {
            // Téléportation du joueur
            shooter.teleportTo(
                    hitResult.getLocation().x,
                    hitResult.getLocation().y,
                    hitResult.getLocation().z
            );

            // Effets de particules
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.PORTAL,
                        hitResult.getLocation().x,
                        hitResult.getLocation().y + 1.0,
                        hitResult.getLocation().z,
                        50,
                        0.5, 0.5, 0.5,
                        0.1
                );
                // Son de téléportation 🔊
                serverLevel.playSound(
                        null,
                        hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z,
                        SoundEvents.ENDERMAN_TELEPORT,
                        SoundSource.PLAYERS,
                        1.0f,
                        1.0f
                );
            }
        }
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new EnderPearlArrowEntity(level, shooter);
    }


}