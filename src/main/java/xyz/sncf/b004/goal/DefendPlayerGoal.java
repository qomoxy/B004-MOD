package xyz.sncf.b004.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.animal.IronGolem;

import java.util.EnumSet;

public class DefendPlayerGoal extends Goal {
    private final IronGolem golem;
    private final Player owner;

    public DefendPlayerGoal(IronGolem golem, Player owner) {
        this.golem = golem;
        this.owner = owner;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.owner == null || this.owner.isSpectator()) {
            return false;
        }
        for (Player player : this.golem.level().players()) {
            if (!player.equals(this.owner) && player.distanceTo(this.golem) < 10.0D) {
                this.golem.setTarget(player);
                return true;
            }
        }
        return false;
    }
}