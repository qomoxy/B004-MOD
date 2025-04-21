package xyz.sncf.b004.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import xyz.sncf.b004.block.SmokeBomb;
import xyz.sncf.b004.registry.ModEntities;
import xyz.sncf.b004.registry.ModItems;

public class SmokeGrenadeEntity extends ThrowableItemProjectile {

    private SmokeBomb smokeBomb;

    public SmokeGrenadeEntity(EntityType<? extends SmokeGrenadeEntity> type, Level level) {
        super(type, level);
    }

    public SmokeGrenadeEntity(Level level, LivingEntity thrower) {
        super(ModEntities.SMOKE_GRENADE.get(), thrower, level);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.level().playSound(null, this.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 2.0F, 0.9F);

            smokeBomb = new SmokeBomb(result.getLocation(), 10, 5, new Vec3(1, 0.5F, 1),
                () -> MinecraftForge.EVENT_BUS.unregister(smokeBomb));
            MinecraftForge.EVENT_BUS.register(smokeBomb);

            this.discard(); // Supprimer l'entité
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SMOKE_GRENADE.get();
    }
}
