package xyz.sncf.b004.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public abstract class SpecialArrowItem extends Item {

    public SpecialArrowItem(Properties properties) {
        super(properties);
    }

    // Tu l'avais déjà :
    public abstract void onHit(Level level, HitResult hitResult, LivingEntity shooter);

    // AJOUTER ceci :
    public abstract AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter);
}
