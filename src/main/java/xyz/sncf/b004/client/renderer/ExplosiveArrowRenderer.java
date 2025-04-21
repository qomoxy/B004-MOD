package xyz.sncf.b004.client.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.sncf.b004.B004;
import xyz.sncf.b004.entity.ExplosiveArrowEntity;

public class ExplosiveArrowRenderer extends ArrowRenderer<ExplosiveArrowEntity> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(B004.MODID, "textures/entity/explosive_arrow.png");

    public ExplosiveArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ExplosiveArrowEntity entity) {
        return TEXTURE;
    }
}
