package xyz.sncf.b004.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.sncf.b004.registry.ModBlocks;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Function;

public class SmokeBomb {

    private Runnable onFinish;

    private float tickCounter;
    private float duration;
    private float maxSize;

    private Vec3 pos;
    private Vec3 scale;

    private ArrayList<BlockPos> smokeBlocks = new ArrayList<>();

    public SmokeBomb(Vec3 position, float duration, float maxSize, Vec3 scale, Runnable onFinish) {
        this.pos = position;
        this.tickCounter = 0;
        this.duration = duration;
        this.maxSize = maxSize;
        this.scale = scale;
        this.onFinish = onFinish;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        tickCounter++;
        if (tickCounter % 2 != 0) return;
        float adv = tickCounter / 20F / duration;
        if (adv >= 1) {
            // Si la durée est écoulée, on arrête l'effet

            for (BlockPos block : smokeBlocks) {
                event.getServer().overworld().setBlock(block, Blocks.AIR.defaultBlockState(), 3);
            }

            if (onFinish != null) {
                onFinish.run();
            }
            return;
        }
        double size = baseEvaluate(adv) * maxSize;
        int ceiledSize = (int) Math.ceil(size);

        BlockPos center = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
        ArrayList<BlockPos> blocks = new ArrayList<>();

        for (int dx = -ceiledSize; dx <= ceiledSize; dx++) {
            for (int dy = -ceiledSize; dy <= ceiledSize; dy++) {
                for (int dz = -ceiledSize; dz <= ceiledSize; dz++) {
                    Vec3 currentPos = new Vec3(dx, dy, dz);
                    Vec3 vec = scale.multiply(currentPos);
                    if (vec.length() > size) continue;
                    BlockPos current = center.offset((int)vec.x,(int)vec.y,(int)vec.z);
                    blocks.add(current);
                }
            }
        }

        // Pour garder une trace des blocs actuels
        ArrayList<BlockPos> newSmokeBlocks = new ArrayList<>();

        for (BlockPos block : blocks) {
            BlockState state = event.getServer().overworld().getBlockState(block);

            // Vérifie si le bloc peut être remplacé (air ou blocs simples comme herbe, fleurs, etc.)
            if (state.is(ModBlocks.SMOKE_BLOCK.get())){
                newSmokeBlocks.add(block);
                continue;
            }
            if (state.isAir() || state.canBeReplaced()) {
                event.getServer().overworld().setBlock(block, ModBlocks.SMOKE_BLOCK.get().defaultBlockState(), 3);
                newSmokeBlocks.add(block);
            }
        }

// Supprime les blocs de fumée qui ne sont plus dans la zone
        for (BlockPos oldBlock : smokeBlocks) {
            if (!newSmokeBlocks.contains(oldBlock)) {
                event.getServer().overworld().setBlock(oldBlock, Blocks.AIR.defaultBlockState(), 3);
            }
        }
        smokeBlocks = newSmokeBlocks;
    }


    private double baseEvaluate(float adv){
        if(adv < 0.25f) return Math.sqrt(Math.sqrt(adv*4));
        else if (adv < 0.5f) return 1;
        else return 1 - Math.pow(adv-0.5f,2) * 4F;
    }
}
