    package xyz.sncf.b004.client.model;

    import com.mojang.blaze3d.vertex.PoseStack;
    import com.mojang.blaze3d.vertex.VertexConsumer;
    import net.minecraft.client.model.EntityModel;
    import net.minecraft.client.model.geom.ModelPart;
    import net.minecraft.client.model.geom.PartPose;
    import net.minecraft.client.model.geom.builders.CubeListBuilder;
    import net.minecraft.client.model.geom.builders.LayerDefinition;
    import net.minecraft.client.model.geom.builders.MeshDefinition;
    import net.minecraft.client.model.geom.builders.PartDefinition;
    import net.minecraft.world.entity.monster.Monster;
    import xyz.sncf.b004.entity.SummonerBossEntity;

    public class SummonerBossModel<S extends Monster> extends EntityModel<SummonerBossEntity> {
        private final ModelPart root;

        public SummonerBossModel(ModelPart root) {
            this.root = root;
        }

        @Override
        public void setupAnim(SummonerBossEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            // Logique d'animation
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition mesh = new MeshDefinition();
            PartDefinition root = mesh.getRoot();

            root.addOrReplaceChild("body", CubeListBuilder.create()
                            .texOffs(0, 0) // Début UV X=0, Y=0
                            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F),
                    PartPose.ZERO);

            return LayerDefinition.create(mesh, 64, 64);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            root.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }