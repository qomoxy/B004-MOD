package xyz.sncf.b004.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
        if (!(miner instanceof Player player)) return;

        // Détermine l'axe d'extension en fonction du regard du joueur
        float pitch = player.getXRot();
        boolean isVerticalMining = Math.abs(pitch) > 45; // Seuil ajustable

        int minX = origin.getX() - radius;
        int maxX = origin.getX() + radius;
        int minY = origin.getY();
        int maxY = origin.getY();
        int minZ = origin.getZ() - radius;
        int maxZ = origin.getZ() + radius;

        if (!isVerticalMining) {
            // Extension verticale sur l'axe Y
            minY = origin.getY() - radius;
            maxY = origin.getY() + radius;

            // Restriction sur X ou Z selon la direction horizontale
            Direction direction = player.getDirection();
            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                minZ = origin.getZ();
                maxZ = origin.getZ();
            } else {
                minX = origin.getX();
                maxX = origin.getX();
            }
        }

        // Destruction des blocs
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos targetPos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(targetPos);

                    if (isCorrectToolForDrops(tool, state)) {
                        if (player.isCreative()) {
                            level.destroyBlock(targetPos, false);
                        } else {
                            level.destroyBlock(targetPos, true, player);
                            tool.hurtAndBreak(1, miner, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        }
                    }
                }
            }
        }
    }
}
