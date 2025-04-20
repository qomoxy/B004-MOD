package xyz.sncf.b004.goal;


import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class FollowPlayerGoal extends Goal {
    private final IronGolem golem;
    private final Player owner;
    private final double speedModifier;
    private final float stopDistance;
    private final float startDistance;

    public FollowPlayerGoal(IronGolem golem, Player owner, double speedModifier, float startDistance, float stopDistance) {
        this.golem = golem;
        this.owner = owner;
        this.speedModifier = speedModifier;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (owner == null || owner.isSpectator() || !owner.isAlive()) {
            return false;
        }
        double distance = this.golem.distanceTo(this.owner);
        return distance > this.startDistance;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.golem.getNavigation().isDone() && this.golem.distanceTo(this.owner) > this.stopDistance;
    }

    @Override
    public void start() {
        Path path = this.golem.getNavigation().createPath(this.owner, 0);
        if (path != null) {
            this.golem.getNavigation().moveTo(path, this.speedModifier);
        }
    }

    @Override
    public void stop() {
        this.golem.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (this.golem.distanceTo(this.owner) > this.startDistance) {
            this.golem.getNavigation().moveTo(this.owner, this.speedModifier);
        }
    }
}