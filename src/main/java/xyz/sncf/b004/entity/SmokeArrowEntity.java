package xyz.sncf.b004.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import xyz.sncf.b004.block.SmokeBomb;
import xyz.sncf.b004.item.SmokeArrowItem;
import xyz.sncf.b004.registry.ModEntities;
import xyz.sncf.b004.registry.ModItems;

public class SmokeArrowEntity extends AbstractArrow {
    private SmokeBomb smokeBomb;
    // Obligatoire pour l'enregistrement
    public SmokeArrowEntity(EntityType<? extends SmokeArrowEntity> type, Level level) {
        super(type, level);
    }

    // Utilisé lors du tir avec createArrow()
    public SmokeArrowEntity(Level level, LivingEntity shooter) {
        super(ModEntities.SMOKE_ARROW.get(), shooter, level);
    }

    @Override
    protected void onHit(HitResult result) {
        if (this.getOwner() instanceof LivingEntity shooter) {
            ((SmokeArrowItem) ModItems.SMOKE_ARROW.get()).onHit(this.level(), result, shooter);
        }

        smokeBomb = new SmokeBomb(result.getLocation(), 10, 6, new Vec3(1,0.5F,1),
                () -> MinecraftForge.EVENT_BUS.unregister(smokeBomb));
        MinecraftForge.EVENT_BUS.register(smokeBomb);
        this.discard();
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.SMOKE_ARROW.get());
    }
}
