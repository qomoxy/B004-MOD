package xyz.sncf.b004.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class MagicMushroomParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    protected MagicMushroomParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites,
                                    double xd, double yd, double zd) {
        super(level, x, y, z);
        this.sprites = sprites;
        this.lifetime = 20;
        this.gravity = 0.0F;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        setSpriteFromAge(this.sprites);
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new MagicMushroomParticle(level, x, y, z, sprites, xd, yd, zd);
        }
    }
}
