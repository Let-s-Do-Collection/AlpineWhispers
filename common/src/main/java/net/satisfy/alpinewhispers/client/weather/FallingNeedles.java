package net.satisfy.alpinewhispers.client.weather;

import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.satisfy.alpinewhispers.core.config.AlpineWhispersConfig;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;
import net.satisfy.foundation.registry.FoundationParticles;

public class FallingNeedles {
    private static final int RADIUS = 12;
    private static final int SAMPLES_PER_TICK = 100;

    public static void init() {
        ClientTickEvent.CLIENT_POST.register(FallingNeedles::tickClient);
        ClientCommandRegistrationEvent.EVENT.register((dispatcher, context) -> dispatcher.register(
                ClientCommandRegistrationEvent.literal("needle").executes(ctx -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    LocalPlayer player = minecraft.player;
                    if (minecraft.level == null || player == null) {
                        return 0;
                    }
                    Vec3 look = player.getLookAngle();
                    int created = 0;
                    for (int i = 0; i < 20; i++) {
                        double x = player.getX() + look.x * 2.0 + (minecraft.level.random.nextDouble() - 0.5);
                        double y = player.getEyeY() + 0.5 + minecraft.level.random.nextDouble() * 0.5;
                        double z = player.getZ() + look.z * 2.0 + (minecraft.level.random.nextDouble() - 0.5);
                        Particle particle = minecraft.particleEngine.createParticle(FoundationParticles.NEEDLE.get(), x, y, z, 0.0, 0.0, 0.0);
                        if (particle != null) {
                            created++;
                            if (i % 2 == 0) {
                                tint(particle, minecraft.level.random);
                            }
                        }
                    }
                    int result = created;
                    ctx.getSource().arch$sendSuccess(() -> Component.literal("[Needle] created " + result + "/20 (half tinted)"), false);
                    return 1;
                })));
    }

    private static void tickClient(Minecraft minecraft) {
        ClientLevel level = minecraft.level;
        LocalPlayer player = minecraft.player;
        if (minecraft.isPaused() || level == null || player == null || !AlpineWhispersConfig.fallingNeedlesEnabled) {
            return;
        }

        RandomSource random = level.random;
        BlockPos center = player.blockPosition();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < SAMPLES_PER_TICK; i++) {
            pos.set(center.getX() + random.nextInt(RADIUS * 2 + 1) - RADIUS, center.getY() + random.nextInt(RADIUS * 2 + 1) - RADIUS / 2, center.getZ() + random.nextInt(RADIUS * 2 + 1) - RADIUS);
            BlockState state = level.getBlockState(pos);
            if (!isNeedleLeaves(state) || random.nextFloat() > 0.3F) {
                continue;
            }

            pos.move(0, -1, 0);
            if (!level.getBlockState(pos).isAir()) {
                continue;
            }

            Particle particle = minecraft.particleEngine.createParticle(FoundationParticles.NEEDLE.get(), pos.getX() + random.nextDouble(), pos.getY() + 0.4, pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
            if (particle != null) {
                tint(particle, random);
            }
        }
    }

    private static void tint(Particle particle, RandomSource random) {
        float shade = 0.82F + random.nextFloat() * 0.1F;
        particle.setColor(shade, Math.min(1.0F, shade + 0.03F), Math.min(1.0F, shade + 0.08F));
    }

    private static boolean isNeedleLeaves(BlockState state) {
        return state.is(Blocks.SPRUCE_LEAVES) || state.is(ObjectRegistry.AROLLA_PINE_LEAVES.get());
    }
}
