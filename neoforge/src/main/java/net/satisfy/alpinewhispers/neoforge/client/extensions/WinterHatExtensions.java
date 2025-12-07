package net.satisfy.alpinewhispers.neoforge.client.extensions;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.satisfy.alpinewhispers.client.model.armor.WinterHatModel;
import net.satisfy.alpinewhispers.core.item.WinterHatItem;
import org.jetbrains.annotations.NotNull;

public class WinterHatExtensions implements IClientItemExtensions {
    @Override
    public @NotNull Model getGenericArmorModel(@NotNull LivingEntity entity, @NotNull ItemStack stack, @NotNull EquipmentSlot slot, @NotNull HumanoidModel<?> original) {
        if (slot == EquipmentSlot.HEAD && stack.getItem() instanceof WinterHatItem winterHatItem) {
            return WinterHatModel.getHatModel(winterHatItem, original.head);
        }
        return original;
    }
}
