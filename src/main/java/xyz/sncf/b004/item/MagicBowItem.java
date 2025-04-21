package xyz.sncf.b004.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;


public class MagicBowItem extends BowItem {

    private static final int COOLDOWN_TICKS = 40;

    public MagicBowItem(Properties properties) {
        super(properties.durability(512));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity shooter, int timeLeft) {
        if (!(shooter instanceof Player player)) return;



        ItemStack arrowStack = player.getProjectile(stack);

        // Vérifie si le projectile est une flèche spéciale
        if (arrowStack.getItem() instanceof SpecialArrowItem specialArrow) {
            System.out.println(">>> Special arrow DETECTED: " + specialArrow.getClass().getSimpleName());
            // Crée une flèche personnalisée
            AbstractArrow arrow = specialArrow.createArrow(level, arrowStack, shooter);
            arrow.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 3.0F, 1.0F);

            // Ajoute l'entité flèche dans le monde
            level.addFreshEntity(arrow);
            arrowStack.shrink(1);

            // Gère l'usure de l'arc et le cooldown
            stack.hurtAndBreak(1, shooter, e -> e.broadcastBreakEvent(shooter.getUsedItemHand()));
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        } else {
            super.releaseUsing(stack, level, shooter, timeLeft);
        }
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack.is(Items.ARROW) || stack.getItem() instanceof SpecialArrowItem;
    }
}
