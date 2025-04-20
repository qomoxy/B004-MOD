package xyz.sncf.b004.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class HealWandItem extends TieredItem {

    private static final int MAX_RADIUS = 10;
    private final float cooldown;
    private final float healAmount;

    public HealWandItem(Tier pTier, Properties pProperties, float cooldown, float healAmount) {
        super(pTier, pProperties);
        this.cooldown = cooldown;
        this.healAmount = healAmount;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        // Vérifier si l'item est en cooldown
        if (pPlayer.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.pass(itemStack);
        }

        // Effectuer un raycast pour détecter les entités
        EntityHitResult entityHitResult = getEntityHitResult(pLevel, pPlayer, MAX_RADIUS);

        if (entityHitResult == null) {
            return InteractionResultHolder.pass(itemStack);
        }

        Entity targetEntity = entityHitResult.getEntity();
        if (targetEntity instanceof LivingEntity livingEntity) {
            System.out.println("Entité vivante détectée : " + livingEntity);

            // Appliquer l'effet (par exemple, soigner)
            livingEntity.heal(healAmount);
            System.out.println("Effet de soin appliqué : " + healAmount);

            // Ajouter des particules de soin côté client
            System.out.println("Ajout des particules de soin...");
            for (int i = 0; i < 5; i++) {
                double offsetX = pLevel.random.nextGaussian() * 0.2;
                double offsetY = pLevel.random.nextGaussian() * 0.2;
                double offsetZ = pLevel.random.nextGaussian() * 0.2;
                pLevel.addParticle(net.minecraft.core.particles.ParticleTypes.HEART,
                        livingEntity.getX() + offsetX,
                        livingEntity.getY() + 1.0 + offsetY,
                        livingEntity.getZ() + offsetZ,
                        0.0, 0.0, 0.0);
            }

            // Réduire la durabilité de l'item
            itemStack.hurtAndBreak(1, pPlayer, (entity) -> {
                entity.broadcastBreakEvent(pUsedHand);
            });

            // Appliquer le cooldown
            pPlayer.getCooldowns().addCooldown(this, (int) (cooldown * 20)); // Cooldown en ticks (20 ticks = 1 seconde)

            return InteractionResultHolder.success(itemStack);
        }

        return InteractionResultHolder.pass(itemStack);
    }

    private EntityHitResult getEntityHitResult(Level pLevel, Player pPlayer, double maxDistance) {
        Vec3 start = pPlayer.getEyePosition(1.0F);
        Vec3 end = start.add(pPlayer.getViewVector(1.0F).scale(maxDistance));
        AABB box = pPlayer.getBoundingBox().expandTowards(pPlayer.getViewVector(1.0F).scale(maxDistance)).inflate(1.0D);
        return ProjectileUtil.getEntityHitResult(pLevel, pPlayer, start, end, box, (entity) -> entity instanceof LivingEntity && entity != pPlayer);
    }
}
