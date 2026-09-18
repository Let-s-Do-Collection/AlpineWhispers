package net.satisfy.alpinewhispers.fabric.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.satisfy.alpinewhispers.client.model.armor.WinterHatModel;
import net.satisfy.alpinewhispers.core.item.WinterHatItem;

public class WinterHatRenderer implements ArmorRenderer {
    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        WinterHatItem hat = (WinterHatItem) stack.getItem();

        Model model = WinterHatModel.getHatModel(hat, contextModel.getHead());
        if (model == null) return;

        model.renderToBuffer(matrices, vertexConsumers.getBuffer(model.renderType(hat.getHatTexture())), light, OverlayTexture.NO_OVERLAY);
    }
}
