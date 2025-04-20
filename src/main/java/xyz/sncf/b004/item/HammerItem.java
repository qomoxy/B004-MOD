package xyz.sncf.b004.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot; // Pour EquipmentSlot
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class HammerItem extends PickaxeItem {
    public HammerItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity miner) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            // Appliquer la durabilité
            stack.hurtAndBreak(1, miner, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });

            // Casser les blocs en 3x3
            breakBlocksInRadius(level, pos, miner, stack, 1); // Rayon de 1 = 3x3
        }



        return true;
    }

    private void breakBlocksInRadius(Level level, @NotNull BlockPos origin, LivingEntity miner, ItemStack tool, int radius) {
        AABB area = new AABB(
                origin.offset(-radius, -radius, -radius),
                origin.offset(radius, radius, radius)
        );

        BlockPos.betweenClosedStream((int) area.minX, (int) area.minY, (int) area.minZ,
                        (int) area.maxX, (int) area.maxY, (int) area.maxZ)
                .forEach(blockPos -> {
                    BlockState state = level.getBlockState(blockPos);

                    if (isCorrectToolForDrops(tool, state) && miner instanceof Player player) {

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
