package xyz.sncf.b004.client.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.sncf.b004.B004;
import xyz.sncf.b004.entity.SmokeArrowEntity;

public class SmokeArrowRenderer extends ArrowRenderer<SmokeArrowEntity> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(B004.MODID, "textures/entity/smoke_arrow.png");

    public SmokeArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SmokeArrowEntity entity) {
        return TEXTURE;
    }
}
