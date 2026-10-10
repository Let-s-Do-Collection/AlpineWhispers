package net.satisfy.alpinewhispers.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.alpinewhispers.core.block.SnowGlobeBlock;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SnowGlobeFlakeParticle extends TextureSheetParticle {
    private static final double RADIUS = 5.0 / 16.0;
    private static final double MIN_Y = 3.0 / 16.0;
    private static final double MAX_Y = 13.0 / 16.0;

    private final double centerX;
    private final double centerZ;
    private final double floorY;
    private final double ceilY;
    private final float swirlSpeed;
    private final float baseSize;
    private final BlockPos globePos;

    protected SnowGlobeFlakeParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites) {
        super(level, x, y, z);
        this.centerX = Math.floor(x) + 0.5;
        this.centerZ = Math.floor(z) + 0.5;
        this.floorY = Math.floor(y) + MIN_Y;
        this.ceilY = Math.floor(y) + MAX_Y;
        this.globePos = BlockPos.containing(x, y, z);
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.hasPhysics = false;
        this.gravity = 0.0F;
        this.friction = 0.9F;
        this.lifetime = 50 + this.random.nextInt(40);
        this.baseSize = 0.012F + this.random.nextFloat() * 0.014F;
        this.quadSize = this.baseSize;
        this.swirlSpeed = (this.random.nextBoolean() ? 1.0F : -1.0F) * (0.004F + this.random.nextFloat() * 0.004F);
        float shade = 0.92F + this.random.nextFloat() * 0.08F;
        this.setColor(shade, shade, 1.0F);
        this.pickSprite(sprites);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        float storm = this.stormIntensity();
        double dx = this.x - this.centerX;
        double dz = this.z - this.centerZ;
        double swirl = this.swirlSpeed * (1.0 + storm * 6.0);
        this.xd += -dz * swirl;
        this.zd += dx * swirl;
        double turbulence = 0.0015 + storm * 0.012;
        this.xd += (this.random.nextDouble() - 0.5) * turbulence;
        this.zd += (this.random.nextDouble() - 0.5) * turbulence;
        if (storm > 0.0F) {
            this.yd += (this.random.nextDouble() - 0.35) * 0.006 * storm;
            if (this.age > this.lifetime - 20 && storm > 0.6F) {
                this.lifetime++;
            }
        }
        this.yd -= 0.0008;
        this.yd = Math.max(this.yd, -0.012);

        this.xd *= this.friction;
        this.yd *= 0.96;
        this.zd *= this.friction;

        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;

        dx = this.x - this.centerX;
        dz = this.z - this.centerZ;
        double dist = Math.sqrt(dx * dx + dz * dz);
        if (dist > RADIUS) {
            double scale = RADIUS / dist;
            this.x = this.centerX + dx * scale;
            this.z = this.centerZ + dz * scale;
            this.xd *= -0.4;
            this.zd *= -0.4;
        }
        if (this.y > this.ceilY) {
            this.y = this.ceilY;
            this.yd = -Math.abs(this.yd) * 0.3;
        }
        if (this.y <= this.floorY) {
            this.y = this.floorY;
            if (storm > 0.3F && this.random.nextFloat() < storm * 0.5F) {
                this.yd = 0.01 + this.random.nextDouble() * 0.03 * storm;
            } else {
                this.xd = 0.0;
                this.yd = 0.0;
                this.zd = 0.0;
            }
        }
        this.setPos(this.x, this.y, this.z);

        int fade = this.lifetime - this.age;
        this.alpha = fade < 15 ? fade / 15.0F : 1.0F;
    }

    private float stormIntensity() {
        BlockState state = this.level.getBlockState(this.globePos);
        if (!(state.getBlock() instanceof SnowGlobeBlock)) {
            return 0.0F;
        }
        return state.getValue(SnowGlobeBlock.POWER) / 15.0F;
    }

    @Override
    public float getQuadSize(float partialTick) {
        return this.baseSize * (0.85F + 0.15F * Mth.sin((this.age + partialTick) * 0.4F));
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new SnowGlobeFlakeParticle(level, x, y, z, xd, yd, zd, this.sprites);
        }
    }
}
