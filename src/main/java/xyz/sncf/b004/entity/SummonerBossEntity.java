package xyz.sncf.b004.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;
import xyz.sncf.b004.registry.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import java.util.Random;



public class SummonerBossEntity extends Monster {
    private final ServerBossEvent bossBar = new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.NOTCHED_12);
    private int phase = 1;
    private final Random random = new Random();
    private int invulnerabilityTicks = 0;

    public SummonerBossEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 500;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 750.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 15.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        updateBossBar();
        handlePhases();
        handleSpecialAbilities();
    }

    private void updateBossBar() {
        this.bossBar.setProgress(getHealth() / getMaxHealth());
        this.bossBar.setName(getDisplayName().copy().append(" - Phase " + phase));
    }

    private void handlePhases() {
        float healthRatio = getHealth() / getMaxHealth();

        if(healthRatio <= 0.66f && phase == 1) {
            phase = 2;
            triggerPhaseTransition();
        } else if(healthRatio <= 0.33f && phase == 2) {
            phase = 3;
            triggerPhaseTransition();
        }
    }

    private void triggerPhaseTransition() {
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4));
        this.level().addParticle(ParticleTypes.ENCHANTED_HIT,
                getX(), getY() + 2, getZ(), 0, 0, 0);
    }

    private void handleSpecialAbilities() {
        if(invulnerabilityTicks > 0) {
            invulnerabilityTicks--;
            spawnAuraParticles();
        }

        if(phase >= 2) {
            handleRangedRetaliation();
        }

        if(tickCount % (200 - (phase * 50)) == 0) {
            summonMinions();
        }
    }

    private void spawnZombies(int count) {
        for(int i = 0; i < count; i++) {
            Zombie zombie = new Zombie(EntityType.ZOMBIE, level());
            zombie.setPos(this.position().add(
                    random.nextGaussian() * 3,
                    0,
                    random.nextGaussian() * 3
            ));
            level().addFreshEntity(zombie);
        }
    }


    private void spawnSkeletons(int count) {
        for(int i = 0; i < count; i++) {
            Skeleton skeleton = new Skeleton(EntityType.SKELETON, level());
            skeleton.setPos(this.position().add(
                    random.nextGaussian() * 3,
                    0,
                    random.nextGaussian() * 3
            ));
            level().addFreshEntity(skeleton);
        }
    }

    private void summonMinions() {
        switch(phase) {
            case 1:
                spawnSkeletonVanguards(3);
                break;
            case 2:
                spawnSkeletonVanguards(2);
                spawnZombies(2);
                spawnSkeletons(2);
                break;
            case 3:
                spawnSkeletonVanguards(4);
                spawnZombies(3);
                spawnSkeletons(3);
                break;
        }
    }

    private void spawnSkeletonVanguards(int count) {
        for(int i = 0; i < count; i++) {
            SkeletonVanguardEntity vanguard = new SkeletonVanguardEntity(ModEntities.SKELETON_VANGUARD.get(), level());
            vanguard.setPos(this.position().add(random.nextGaussian() * 3, 0, random.nextGaussian() * 3));
            level().addFreshEntity(vanguard);
        }
    }

    private void handleRangedRetaliation() {
        if(random.nextFloat() < 0.3f) {
            Player target = level().getNearestPlayer(this, 30.0D);
            if(target != null && hasLineOfSight(target)) {
                retaliateRangedAttack(target);
            }
        }
    }


    private void retaliateRangedAttack(LivingEntity target) {
        LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level());
        lightning.moveTo(target.getX(), target.getY(), target.getZ());
        level().addFreshEntity(lightning);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if(invulnerabilityTicks > 0) return false;

        // Vérifie si l'attaque vient d'un projectile
        if(source.getDirectEntity() instanceof Projectile && phase >= 2) {
            LivingEntity shooter = null;
            if(source.getDirectEntity() instanceof AbstractArrow arrow) {
                shooter = (LivingEntity) arrow.getOwner();
            }
            if(shooter != null) {
                retaliateRangedAttack(shooter);
            }
        }

        // Téléportation si attaqué en mêlée
        if(source.getDirectEntity() instanceof LivingEntity attacker &&
                distanceTo(attacker) < 5 &&
                random.nextFloat() < 0.4f) {
            teleportAway();
            return false;
        }

        return super.hurt(source, amount);
    }

    private void teleportAway() {
        Vec3 newPos = this.position()
                .add((random.nextDouble() - 0.5) * 10,
                        0,
                        (random.nextDouble() - 0.5) * 10);

        this.teleportTo(newPos.x, newPos.y, newPos.z);
        invulnerabilityTicks = 60;
        addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 100));
    }

    private void spawnAuraParticles() {
        if(level().isClientSide) {
            for(int i = 0; i < 360; i += 10) {
                double rad = Math.toRadians(i);
                level().addParticle(ParticleTypes.ENCHANT,
                        getX() + Math.cos(rad) * 2,
                        getY() + 1.5,
                        getZ() + Math.sin(rad) * 2,
                        0, 0.1, 0);
            }
        }
    }

    // Getters pour la barre de boss
    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossBar.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossBar.removePlayer(player);
    }
}
