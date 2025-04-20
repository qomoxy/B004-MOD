package xyz.sncf.b004.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ambient.Bat;

import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.projectile.SmallFireball;
import org.jetbrains.annotations.NotNull;
import xyz.sncf.b004.entity.CustomBat;
import xyz.sncf.b004.entity.CustomFireball;
import xyz.sncf.b004.goal.DefendPlayerGoal;
import xyz.sncf.b004.goal.FollowPlayerGoal;
import xyz.sncf.b004.goal.HealPlayerGoal;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class WizardBookItem extends Item {
    private static final int COOLDOWN_TICKS = 20 * 15; // 15 secondes

    public WizardBookItem(Properties properties) {
        super(properties.durability(64).rarity(Rarity.UNCOMMON));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && stack.getDamageValue() < stack.getMaxDamage()) {
            applyRandomEffect(level, player);
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void applyRandomEffect(Level level, Player player) {
        int effect = level.random.nextInt(7) + 1;

        switch (effect) {
            case 1 -> badEffect(level, player);
            case 2 -> shootFireball(player);
            case 3 -> spawnGolem(level, player);
            case 4 -> knightEffect(player);
            case 5 -> speedEffect(player);
            case 6 -> {} // Rien
            case 7 -> rideBat(level, player);
        }
    }

    // Effet 1 : Cécité + Zombies
    private void badEffect(Level level, Player player) {
        player.addEffect(new MobEffectInstance(
                MobEffects.BLINDNESS, 200, 1));

        for (int i = 0; i < 3; i++) {
            Zombie zombie = new Zombie(level);
            zombie.setCustomName(Component.literal("Zombie maudit"));
            zombie.setCustomNameVisible(true);
            zombie.setPos(player.getX(), player.getY(), player.getZ());
            level.addFreshEntity(zombie);
        }
    }

    // Effet 2 : Boule de feu
    private void shootFireball(Player player) {
        Vec3 lookAngle = player.getLookAngle();
        CustomFireball fireball = new CustomFireball(
                player.level(), player, lookAngle.x, lookAngle.y, lookAngle.z);
        fireball.setPos(player.getX(), player.getEyeY(), player.getZ());
        player.level().addFreshEntity(fireball);
    }

    private void spawnGolem(Level level, Player player) {
        IronGolem golem = new IronGolem(EntityType.IRON_GOLEM, level);
        golem.setPos(player.getX(), player.getY(), player.getZ());
        golem.setCustomName(Component.literal("Golem protecteur"));
        golem.setCustomNameVisible(true);
        golem.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 2));

        // Add custom goals
        golem.goalSelector.addGoal(1, new FollowPlayerGoal(golem, player, 1.0D, 5.0F, 2.0F));

        level.addFreshEntity(golem);
    }

    // Effet 4 : Chevalier
    private void knightEffect(Player player) {
        player.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_BOOST, 45, 1));
        player.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_RESISTANCE, 45, 1));
    }

    // Effet 5 : Vitesse + Saut + Nourriture
    private void speedEffect(Player player) {
        player.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SPEED, 40, 2));
        player.addEffect(new MobEffectInstance(
                MobEffects.JUMP, 40, 1));
        player.addEffect(new MobEffectInstance(
                MobEffects.INVISIBILITY, 40, 1));
        player.getFoodData().eat(20, 1.0F);
    }

    private void rideBat(Level level, Player player) {
        if (level.isClientSide()) return;

        CustomBat bat = new CustomBat(EntityType.BAT, level);
        bat.setPos(player.getX(), player.getY() + 1.0D, player.getZ());
        bat.setXRot(player.getXRot());
        bat.setYRot(player.getYRot());

        bat.setCustomName(Component.literal("Chauve-souris magique"));
        bat.setCustomNameVisible(true);

        // Rend la chauve-souris invulnérable et visible
        bat.setInvulnerable(true);
        bat.setInvisible(false);

        level.addFreshEntity(bat);
        player.startRiding(bat);
    }
}