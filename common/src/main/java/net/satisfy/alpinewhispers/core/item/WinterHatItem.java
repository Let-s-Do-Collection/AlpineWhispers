package net.satisfy.alpinewhispers.core.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.satisfy.alpinewhispers.client.model.armor.WinterHatModel;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class WinterHatItem extends ArmorItem {
    private final ResourceLocation hatTexture;
    private static final Map<Item, WinterHatModel<?>> models = new HashMap<>();

    public WinterHatItem(ArmorMaterial armorMaterial, Type type, Properties properties, ResourceLocation hatTexture) {
        super(BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(armorMaterial), type, properties);
        this.hatTexture = hatTexture;
    }

    public ResourceLocation getHatTexture() {
        return hatTexture;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return this.type.getSlot();
    }


    public static Model getHatModel(Item item, ModelPart baseHead) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        WinterHatModel<?> model = models.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.WINTER_HAT.get()) {
                return new WinterHatModel<>(modelSet.bakeLayer(WinterHatModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        assert model != null;
        model.copyHead(baseHead);

        return model;
    }
}
