package net.satisfy.alpinewhispers.client.overlay;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.alpinewhispers.core.block.entity.PrivyBlockEntity;
import net.satisfy.alpinewhispers.core.config.AlpineWhispersConfig;
import net.satisfy.foundation.overlay.BlockInfoProvider;
import net.satisfy.foundation.overlay.InfoSection;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PrivyInfoProvider implements BlockInfoProvider {
    @Override
    public List<InfoSection> describe(Level level, BlockPos pos, BlockState state, @Nullable BlockHitResult hit) {
        if (!AlpineWhispersConfig.showPrivyInfo || !(level.getBlockEntity(pos) instanceof PrivyBlockEntity privy)) {
            return List.of();
        }
        List<InfoSection> sections = new ArrayList<>();
        int processing = privy.getProcessingCount();
        if (processing > 0) {
            sections.add(InfoSection.lines(Component.translatable("hud.alpinewhispers.privy.grinding").withStyle(ChatFormatting.YELLOW),
                    List.of(Component.translatable("hud.alpinewhispers.privy.items_left", processing).withStyle(ChatFormatting.GRAY))));
        }
        List<ItemStack> contents = privy.getContents();
        if (!contents.isEmpty()) {
            sections.add(InfoSection.icons(Component.translatable("hud.alpinewhispers.privy.in_privy"), contents, InfoSection.ROW_COLUMNS).withDecorations());
        }
        sections.add(InfoSection.title(Component.translatable("hud.alpinewhispers.privy.hint").withStyle(ChatFormatting.GRAY)));
        if (!contents.isEmpty()) {
            sections.add(InfoSection.title(Component.translatable("hud.alpinewhispers.privy.hint_take").withStyle(ChatFormatting.GRAY)));
        }
        return sections;
    }
}
