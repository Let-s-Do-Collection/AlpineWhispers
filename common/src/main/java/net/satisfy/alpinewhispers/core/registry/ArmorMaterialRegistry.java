package net.satisfy.alpinewhispers.core.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.alpinewhispers.AlpineWhispers;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ArmorMaterialRegistry {
    public static final Holder<ArmorMaterial> WINTER_HAT;

    static {
        EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        defense.put(ArmorItem.Type.HELMET, 1);
        defense.put(ArmorItem.Type.CHESTPLATE, 0);
        defense.put(ArmorItem.Type.LEGGINGS, 0);
        defense.put(ArmorItem.Type.BOOTS, 0);
        defense.put(ArmorItem.Type.BODY, 0);

        Supplier<Ingredient> repair = () -> Ingredient.of(ItemTags.WOOL);
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(AlpineWhispers.identifier("winter_hat")));

        WINTER_HAT = Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, AlpineWhispers.identifier("winter_hat"),
                new ArmorMaterial(defense, 0, SoundEvents.ARMOR_EQUIP_LEATHER, repair, layers, 0.0F, 0.0F)
        );
    }
}
