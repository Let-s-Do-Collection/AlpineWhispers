package net.satisfy.alpinewhispers.core.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.satisfy.alpinewhispers.core.entity.AlpineSheepEntity;
import org.jetbrains.annotations.NotNull;

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
}