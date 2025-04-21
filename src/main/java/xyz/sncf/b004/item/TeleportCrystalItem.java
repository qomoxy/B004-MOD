package xyz.sncf.b004.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TeleportCrystalItem extends Item {

    private static final float MIN_CHARGE_SEC = 1.0f; // Minimum charge time in seconds
    private static final int MAX_DISTANCE = 20;
    private static final int COOLDOWN_SEC = 10; // Cooldown time in seconds


    public TeleportCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000; // durée max comme l'arc
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player player) || world.isClientSide) return;

        int charge = this.getUseDuration(stack) - remainingUseTicks;
        if (charge < MIN_CHARGE_SEC * 20) return;

        EntityHitResult entityHit = getEntityHitResult(world, player, MAX_DISTANCE);

        if (entityHit == null) return;

        LivingEntity target = (LivingEntity) entityHit.getEntity();

        // Position cible derrière l'entité (opposé au joueur)
        Vec3 behindVector = target.getForward().scale(-1).multiply(1, 0, 1);
        Vec3 teleportPos = target.position().add(behindVector).add(0, 0.1, 0);

        if (!isSafeTeleport(world, teleportPos)) return;

        player.teleportTo(teleportPos.x, teleportPos.y, teleportPos.z);
        stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
        player.getCooldowns().addCooldown(this, COOLDOWN_SEC * 20); // Cooldown en ticks (20 ticks = 1 seconde)
    }

    private EntityHitResult getEntityHitResult(Level pLevel, Player pPlayer, double maxDistance) {
        Vec3 start = pPlayer.getEyePosition(1.0F);
        Vec3 end = start.add(pPlayer.getViewVector(1.0F).scale(maxDistance));
        AABB box = pPlayer.getBoundingBox().expandTowards(pPlayer.getViewVector(1.0F).scale(maxDistance)).inflate(1.0D);
        return ProjectileUtil.getEntityHitResult(pLevel, pPlayer, start, end, box, (entity) -> entity instanceof LivingEntity && entity != pPlayer);
    }

    private boolean isSafeTeleport(Level world, Vec3 pos) {
        BlockState stateAtPos = world.getBlockState(BlockPos.containing(pos));
        return !stateAtPos.blocksMotion();
    }
}
