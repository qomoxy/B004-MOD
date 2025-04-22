package xyz.sncf.b004.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Skeleton;
import xyz.sncf.b004.entity.SkeletonVanguardEntity;

public class SkeletonVanguardRenderer extends SkeletonRenderer {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("b004", "textures/entity/skeleton_vanguard.png");

    public SkeletonVanguardRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    // Correction du type du paramètre
    public ResourceLocation getTextureLocation(SkeletonVanguardEntity entity) {
        return TEXTURE;
    }
}