package xyz.sncf.b004.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmokeBlock extends Block {
    public SmokeBlock() {
        super(BlockBehaviour.Properties.of()
                .noCollission()
                .noOcclusion()
                .strength(-1.0F, 3600000.0F) // Rend le bloc indestructible
                .lightLevel(state -> 0)); // Utilise un type de son approprié
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.empty(); // Pas de collision
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.empty(); // Pas de forme visible pour la sélection
    }

    @Override
    public void attack(BlockState state, Level world, BlockPos pos, Player player) {
        // Empêche la destruction du bloc
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return false; // Empêche le remplacement du bloc
    }
}
