package net.satisfy.alpinewhispers.core.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.satisfy.alpinewhispers.core.entity.AlpineSheepEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BreadOnAStickItem extends Item {
    public BreadOnAStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (player.isPassenger() && player.getVehicle() instanceof AlpineSheepEntity alpineSheepEntity) {
            if (alpineSheepEntity.boost()) {
                itemStack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemStack));
                if (itemStack.isEmpty()) {
                    ItemStack result = new ItemStack(Items.FISHING_ROD);
                    return InteractionResultHolder.sidedSuccess(result, level.isClientSide());
                }
                return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        int beige = 0xF5DEB3;
        int gold = 0xFFD700;
        if (!Screen.hasShiftDown()) {
            Component key = Component.literal("[SHIFT]").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(gold)));
            list.add(Component.translatable("tooltip.alpinewhispers.tooltip_information.hold", key).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
            return;
        }
        list.add(Component.translatable("tooltip.alpinewhispers.bread_on_a_stick.info_0").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
        list.add(Component.empty());
        list.add(Component.translatable("tooltip.alpinewhispers.bread_on_a_stick.info_1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
    }
}