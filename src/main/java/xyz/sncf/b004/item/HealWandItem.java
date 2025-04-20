package xyz.sncf.b004.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public class HealWandItem extends TieredItem {

    private static final int MAX_RADIUS = 10;
    private float cooldown;
    private float healAmount;

    public HealWandItem(Tier pTier, Properties pProperties, float cooldown, float healAmount) {
        super(pTier, pProperties);
        this.cooldown = cooldown;
        this.healAmount = healAmount;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        // check if entity is in range
        if (pPlayer.distanceTo(pInteractionTarget) <= MAX_RADIUS) {
            // heal the entity
            pInteractionTarget.heal(healAmount);
            // apply cooldown
            pStack.hurtAndBreak(1, pPlayer, (entity) -> {
                entity.broadcastBreakEvent(pUsedHand);
            });
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }
}
