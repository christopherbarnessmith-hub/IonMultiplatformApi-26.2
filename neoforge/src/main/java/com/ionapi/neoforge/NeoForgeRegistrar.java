package com.ionapi.neoforge;

import com.ionapi.registry.IonRegistrar;
import com.ionapi.registry.RegistryRef;
import com.ionapi.registry.SimpleRegistryRef;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NeoForgeRegistrar implements IonRegistrar {
    private final String modId;
    private final DeferredRegister.Items items;
    private final DeferredRegister.Blocks blocks;
    private boolean registered;

    public NeoForgeRegistrar(String modId) {
        this.modId = modId;
        this.items = DeferredRegister.createItems(modId);
        this.blocks = DeferredRegister.createBlocks(modId);
    }

    @Override
    public RegistryRef<Item> item(String name, Supplier<Item> factory) {
        ensureOpen();
        Supplier<Item> holder = items.register(name, factory);
        return new SimpleRegistryRef<>(id(name), holder);
    }

    @Override
    public RegistryRef<Block> block(String name, Supplier<Block> factory) {
        ensureOpen();
        Supplier<Block> holder = blocks.register(name, factory);
        return new SimpleRegistryRef<>(id(name), holder);
    }

    @Override
    public boolean isRegistered() {
        return registered;
    }

    @Override
    public void registerAll() {
        // NeoForge attaches deferred registers through IonNeoForge.register(modBus, registrar).
    }

    public void register(IEventBus modBus) {
        if (registered) {
            return;
        }

        registered = true;
        items.register(modBus);
        blocks.register(modBus);
    }

    @Override
    public Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(modId, name);
    }

    private void ensureOpen() {
        if (registered) {
            throw new IllegalStateException("Cannot add new registrations for " + modId + " after it was attached to the NeoForge mod bus.");
        }
    }
}
