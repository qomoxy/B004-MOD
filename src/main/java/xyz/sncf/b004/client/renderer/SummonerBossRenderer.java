package xyz.sncf.b004.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;
import xyz.sncf.b004.client.model.SummonerBossModel;
import xyz.sncf.b004.entity.SummonerBossEntity;
import xyz.sncf.b004.registry.ModModelLayers;

public class SummonerBossRenderer extends MobRenderer<SummonerBossEntity, SummonerBossModel<SummonerBossEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("b004", "textures/entity/summoner_boss.png");

    public SummonerBossRenderer(EntityRendererProvider.Context context) {
        super(context,
                new SummonerBossModel<>(context.bakeLayer(ModModelLayers.SUMMONER_BOSS_LAYER)),
                0.5f
        );
    }

    @Override
    public ResourceLocation getTextureLocation(SummonerBossEntity entity) {
        return TEXTURE;
    }
}
