package net.satisfy.alpinewhispers.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.alpinewhispers.core.block.FairyLightsBlock;
import net.satisfy.alpinewhispers.core.block.GarlandBlock;
import net.satisfy.alpinewhispers.core.block.TreeBaublesBlock;
import net.satisfy.alpinewhispers.core.config.AlpineWhispersConfig;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;
import net.satisfy.alpinewhispers.core.registry.SoundEventRegistry;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Client side only state: a star on top of a decorated tree "crowns" it at night and sends a wave of sparkles
 * down through the fairy lights, baubles and garlands below, with a chime every other wave. A redstone signal
 * lights it up during the day as well.
 */
public class StarTopperBlockEntity extends BlockEntity {
    public static final int WAVE_TICKS = 120;
    private static final float WAVE_SPEED = 0.3F;
    private static final int SCAN_INTERVAL = 100;
    private static final int SCAN_RADIUS = 8;
    private static final int SCAN_DEPTH = 14;
    private static final int CHIME_RANGE = 16;
    private static final Vector3f[] COLORS = {
            new Vector3f(1.0F, 0.82F, 0.35F),
            new Vector3f(1.0F, 0.35F, 0.3F),
            new Vector3f(0.45F, 0.9F, 0.5F),
            new Vector3f(1.0F, 0.95F, 0.85F)
    };

    private final List<BlockPos> decorations = new ArrayList<>();
    private int scanCooldown;
    private boolean crowning;

    public StarTopperBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.STAR_TOPPER_BLOCK_ENTITY.get(), pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, StarTopperBlockEntity star) {
        glint(level, pos, level.random);
        star.crowning = AlpineWhispersConfig.starTreeLights && (isNight(level) || level.hasNeighborSignal(pos)) && onTree(level, pos);
        if (!star.crowning) {
            star.decorations.clear();
            star.scanCooldown = 0;
            return;
        }
        if (--star.scanCooldown <= 0) {
            star.scanCooldown = SCAN_INTERVAL;
            star.scan(level, pos);
        }

        long time = level.getGameTime();
        int phase = (int) (time % WAVE_TICKS);
        if (phase == 0 && (time / WAVE_TICKS) % 2 == 0 && AlpineWhispersConfig.starChime && playerNear(level, pos)) {
            level.playLocalSound(pos, SoundEventRegistry.STAR_TOPPER_CHIME.get(), SoundSource.BLOCKS, 0.6F, 1.0F, false);
        }

        float front = phase * WAVE_SPEED;
        RandomSource random = level.random;
        for (BlockPos deco : star.decorations) {
            double distance = Math.sqrt(deco.distSqr(pos));
            if (distance < front - WAVE_SPEED || distance >= front) continue;
            Vector3f color = COLORS[Math.floorMod(deco.hashCode(), COLORS.length)];
            for (int i = 0; i < 2; i++) {
                level.addParticle(new DustParticleOptions(color, 0.7F),
                        deco.getX() + 0.2 + random.nextDouble() * 0.6, deco.getY() + 0.2 + random.nextDouble() * 0.6, deco.getZ() + 0.2 + random.nextDouble() * 0.6,
                        0, 0, 0);
            }
            if (random.nextFloat() < 0.35F) {
                level.addParticle(ParticleTypes.END_ROD, deco.getX() + 0.5, deco.getY() + 0.5, deco.getZ() + 0.5, 0, 0.005, 0);
            }
        }
    }

    private void scan(Level level, BlockPos star) {
        decorations.clear();
        for (BlockPos pos : BlockPos.betweenClosed(star.offset(-SCAN_RADIUS, -SCAN_DEPTH, -SCAN_RADIUS), star.offset(SCAN_RADIUS, -1, SCAN_RADIUS))) {
            Block block = level.getBlockState(pos).getBlock();
            if (block instanceof FairyLightsBlock || block instanceof GarlandBlock || block instanceof TreeBaublesBlock) {
                decorations.add(pos.immutable());
            }
        }
    }

    // Short glints around the star's points; animateTick is too rare for a single block to look alive.
    private static void glint(Level level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() >= 0.15F) return;
        float angle = random.nextFloat() * Mth.TWO_PI;
        float radius = 0.22F + random.nextFloat() * 0.1F;
        float spin = random.nextFloat() * Mth.TWO_PI;
        double x = pos.getX() + 0.5 + Mth.cos(spin) * Mth.cos(angle) * radius;
        double y = pos.getY() + 0.68 + Mth.sin(angle) * radius;
        double z = pos.getZ() + 0.5 + Mth.sin(spin) * Mth.cos(angle) * radius;
        level.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0, 0.002, 0.0);
    }

    private static boolean onTree(Level level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(BlockTags.LEAVES) || level.getBlockState(pos.below(2)).is(BlockTags.LEAVES);
    }

    private static boolean isNight(Level level) {
        long time = level.getDayTime() % 24000L;
        return time >= 12500L && time <= 23500L;
    }

    private static boolean playerNear(Level level, BlockPos pos) {
        Player player = level.getNearestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, CHIME_RANGE, false);
        return player != null;
    }

    public boolean isCrowning() {
        return crowning;
    }
}
