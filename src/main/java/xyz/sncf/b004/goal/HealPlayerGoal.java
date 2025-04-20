package xyz.sncf.b004.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.EnumSet;

public class HealPlayerGoal extends Goal {
    private final IronGolem golem;
    private final Player owner;
    private int cooldown;

    public HealPlayerGoal(IronGolem golem, Player owner) {
        this.golem = golem;
        this.owner = owner;
        this.cooldown = 0;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (owner == null || owner.isSpectator() || !owner.isAlive()) {
            return false;
        }
        return owner.getHealth() < owner.getMaxHealth() && golem.distanceTo(owner) < 10.0D;
    }

    @Override
    public void tick() {
        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (golem.distanceTo(owner) < 3.0D) {
            owner.heal(2.0F); // Heal 1 heart (2 health points)
            owner.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 1)); // Short regeneration boost
            cooldown = 100; // 5 seconds cooldown (100 ticks)
        } else {
            golem.getNavigation().moveTo(owner, 1.0D); // Move closer to the player
        }
    }
}