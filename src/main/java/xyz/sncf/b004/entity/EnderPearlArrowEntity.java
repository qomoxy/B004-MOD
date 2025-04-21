package xyz.sncf.b004.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import xyz.sncf.b004.registry.ModEntities;
import xyz.sncf.b004.registry.ModItems;
import xyz.sncf.b004.item.EnderPearlArrowItem;

public class EnderPearlArrowEntity extends AbstractArrow {

    // Constructeur utilisé par Minecraft (EntityType + Level)
    public EnderPearlArrowEntity(EntityType<? extends EnderPearlArrowEntity> type, Level level) {
        super(type, level);
    }

    // Constructeur personnalisé quand le joueur tire
    public EnderPearlArrowEntity(Level level, LivingEntity shooter) {
        super(ModEntities.ENDER_PEARL_ARROW.get(), shooter, level);
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        if (this.getOwner() instanceof LivingEntity shooter) {
            ((EnderPearlArrowItem) ModItems.ENDER_PEARL_ARROW.get()).onHit(
                    this.level(),
                    result,
                    shooter
            );
        }
        this.discard();
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(ModItems.ENDER_PEARL_ARROW.get());
    }
}
