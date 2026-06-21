package com.ionapi.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface IonRegistrar {
    RegistryRef<Item> item(String name, Supplier<Item> factory);

    RegistryRef<Block> block(String name, Supplier<Block> factory);

    default RegistryRef<Block> blockWithItem(String name, Supplier<Block> blockFactory, Item.Properties itemProperties) {
        RegistryRef<Block> block = block(name, blockFactory);
        item(name, () -> new BlockItem(block.get(), itemProperties.setId(itemKey(name))));
        return block;
    }

    Identifier id(String name);

    default ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, id(name));
    }

    default ResourceKey<Block> blockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, id(name));
    }

    boolean isRegistered();

    void registerAll();
}
