package xyz.sncf.b004.client.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.sncf.b004.B004;
import xyz.sncf.b004.entity.EnderPearlArrowEntity;

public class EnderPearlArrowRenderer extends ArrowRenderer<EnderPearlArrowEntity> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(B004.MODID, "textures/entity/ender_pearl_arrow.png");

    public EnderPearlArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(EnderPearlArrowEntity entity) {
        return TEXTURE;
    }
}
