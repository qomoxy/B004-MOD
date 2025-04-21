package xyz.sncf.b004.effects;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import xyz.sncf.b004.init.ModParticleTypes;

import java.util.UUID;

public class MagicMushroomEffect extends MobEffect {
    int duration;

    private static final float TICKS_PER_SEC = 1F;

    private static final float BONUS_HEAL_PER_SEC = 3F;
    private static final float BONUS_SPEED_MULTIPLIER = 0.5F;
    private static final float BONUS_DAMAGE_MULTIPLIER = 0.5F;

    private static final float MALUS_POISON_PER_SEC = -2F;
    private static final float MALUS_SPEED_MULTIPLIER = -0.5F;
    private static final float MALUS_DAMAGE_MULTIPLIER = -0.25F;
    public MagicMushroomEffect() {
        super(MobEffectCategory.NEUTRAL, 0x55AAFF);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        int remaining = entity.getEffect(this).getDuration();

        ApplyParticles(entity);

        if(remaining % (int)(20 / TICKS_PER_SEC) != 0) return;

        duration = Math.max(duration, remaining);

        boolean isPositive = remaining > duration/2;


        float mult = (float) Math.abs(remaining - duration / 2) / duration * 2;

        float health;
        float bonusDamage;
        float bonusSpeed;
        if (isPositive) {
            // Phase positive : soigne, donne force et vitesse
            health = BONUS_HEAL_PER_SEC * mult / TICKS_PER_SEC;
            bonusDamage = BONUS_DAMAGE_MULTIPLIER * mult;
            bonusSpeed = BONUS_SPEED_MULTIPLIER * mult;
        } else {
            // Phase négative : inflige dégâts, lenteur et faiblesse
            health = MALUS_POISON_PER_SEC * mult / TICKS_PER_SEC;
            bonusDamage = MALUS_DAMAGE_MULTIPLIER * mult;
            bonusSpeed = MALUS_SPEED_MULTIPLIER * mult;
        }

        ApplyEfects(entity, bonusDamage, bonusSpeed, health);
    }

    private void ApplyEfects(LivingEntity entity, float bonusDamage, float bonusSpeed, float health) {
        AttributeInstance attackDamage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        attackDamage.removeModifier(DAMAGE_MODIFIER_UUID);
        speed.removeModifier(SPEED_MODIFIER_UUID);

        AttributeModifier strengthModifier = new AttributeModifier(
                DAMAGE_MODIFIER_UUID, "Strength Modifier",
                bonusDamage, AttributeModifier.Operation.MULTIPLY_TOTAL);
        attackDamage.addTransientModifier(strengthModifier);

        AttributeModifier dynamicModifier = new AttributeModifier(
                SPEED_MODIFIER_UUID, "Speed Modifier",
                bonusSpeed, AttributeModifier.Operation.MULTIPLY_TOTAL);
        speed.addTransientModifier(dynamicModifier);

        // On applique le soin ou les dégâts
        if (health > 0) {
            entity.heal(health);
        } else {
            entity.hurt(entity.damageSources().magic(), -health);
        }
    }

    private void ApplyParticles(LivingEntity entity) {

        Level world = entity.level();

        if (!world.isClientSide) return;

        double x = entity.getX();
        double y = entity.getY() + entity.getEyeHeight();
        double z = entity.getZ();

        RandomSource random = entity.getRandom();
        double dirX = (random.nextDouble() - 0.5) * 0.1;
        double dirY = 0.02F;
        double dirZ = (random.nextDouble() - 0.5) * 0.1;

        world.addParticle(
                ModParticleTypes.MAGIC_MUSHROOM_PARTICLE.get(),
                x, y, z,
                dirX, dirY, dirZ
        );
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
        AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            speed.removeModifier(SPEED_MODIFIER_UUID);
        }

        // Même chose pour les autres attributs modifiés éventuellement.
        super.removeAttributeModifiers(entity, attributes, amplifier);
    }

    private static final UUID DAMAGE_MODIFIER_UUID =
            UUID.fromString("c0a1bcd2-1234-4def-9abc-1234567890ab");

    private static final UUID SPEED_MODIFIER_UUID =
            UUID.fromString("bfb1e6f1-72f3-4f9a-b27e-1234567890ac");
}