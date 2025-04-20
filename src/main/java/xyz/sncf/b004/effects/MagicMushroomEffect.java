package xyz.sncf.b004.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class MagicMushroomEffect extends MobEffect {
    int duration;
    public MagicMushroomEffect() {
        super(MobEffectCategory.NEUTRAL, 0x55AAFF);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        int remaining = entity.getEffect(this).getDuration();
        duration = Math.max(duration, remaining);

        boolean isPositive = remaining > duration/2;


        float mult = (float) Math.abs(remaining - duration / 2) / duration * 2;

        float bonusDamage;
        float bonusSpeed;
        if (isPositive) {
            // Phase positive : soigne, donne force et vitesse
            entity.heal(0.5F * mult);
            bonusDamage = 0.5F * mult;
            bonusSpeed = 0.5F * mult;
        } else {
            // Phase négative : inflige dégâts, lenteur et faiblesse
            entity.hurt(entity.damageSources().magic(), mult);
            bonusDamage = -0.25F * mult;
            bonusSpeed = -0.5F * mult;
        }

        AttributeInstance attackDamage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        attackDamage.removeModifier(DAMAGE_MODIFIER_UUID);
        speed.removeModifier(SPEED_MODIFIER_UUID);

        AttributeModifier strengthModifier = new AttributeModifier(
                DAMAGE_MODIFIER_UUID, "Strength Modifier",
                bonusDamage, AttributeModifier.Operation.ADDITION);
        attackDamage.addTransientModifier(strengthModifier);

        AttributeModifier dynamicModifier = new AttributeModifier(
                SPEED_MODIFIER_UUID, "Speed Modifier",
                bonusSpeed, AttributeModifier.Operation.MULTIPLY_TOTAL);
        speed.addTransientModifier(dynamicModifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 10 == 0;
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