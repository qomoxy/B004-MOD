package xyz.sncf.b004.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot; // Pour EquipmentSlot
import net.minecraft.world.InteractionHand; // Si vous utilisez getUsedItemHand()
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class HammerItem extends PickaxeItem {
    public HammerItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            // Appliquer la durabilité
            stack.hurtAndBreak(1, miner, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });

            // Casser les blocs en 3x3
            breakBlocksInRadius(level, pos, miner, stack, 1); // Rayon de 1 = 3x3
        }

        // Appliquer Mining Fatigue
        if (miner instanceof Player) {
            ((Player) miner).addEffect(new MobEffectInstance(
                    MobEffects.DIG_SLOWDOWN,
                    100, // 5 secondes (20 ticks/seconde)
                    1,
                    false,
                    true
            ));
        }

        return true;
    }

    private void breakBlocksInRadius(Level level, BlockPos origin, LivingEntity miner, ItemStack tool, int radius) {
        AABB area = new AABB(
                origin.offset(-radius, -radius, -radius),
                origin.offset(radius, radius, radius)
        );

        BlockPos.betweenClosedStream((int) area.minX, (int) area.minY, (int) area.minZ,
                        (int) area.maxX, (int) area.maxY, (int) area.maxZ)
                .forEach(blockPos -> {
                    BlockState state = level.getBlockState(blockPos);

                    if (isCorrectToolForDrops(tool, state) && miner instanceof Player) {
                        Player player = (Player) miner;

                        if (player.isCreative()) {
                            level.destroyBlock(blockPos, false);
                        } else {
                            level.destroyBlock(blockPos, true, player);
                            // Correction ici :
                            tool.hurtAndBreak(1, miner, (entity) -> {
                                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                            });
                        }
                    }
                });
    }
}
