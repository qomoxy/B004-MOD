package xyz.sncf.b004.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.level.Level;

public class CustomBat extends Bat {
    private int lifetimeTicks = 1200; // 60 secondes (20 ticks par seconde)

    public CustomBat(EntityType<? extends Bat> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            lifetimeTicks--;
            if (lifetimeTicks <= 0) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }
}
