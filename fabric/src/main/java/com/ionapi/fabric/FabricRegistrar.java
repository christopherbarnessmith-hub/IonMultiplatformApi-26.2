package com.ionapi.fabric;

import com.ionapi.registry.IonRegistrar;
import com.ionapi.registry.RegistryRef;
import com.ionapi.registry.SimpleRegistryRef;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class FabricRegistrar implements IonRegistrar {
    private final String modId;
    private final List<Runnable> registrations = new ArrayList<>();
    private boolean registered;

    public FabricRegistrar(String modId) {
        this.modId = modId;
    }

    @Override
    public RegistryRef<Item> item(String name, Supplier<Item> factory) {
        ensureOpen();
        Identifier id = id(name);
        Holder<Item> holder = new Holder<>();
        registrations.add(() -> holder.value = Registry.register(BuiltInRegistries.ITEM, id, factory.get()));
        return new SimpleRegistryRef<>(id, holder);
    }

    @Override
    public RegistryRef<Block> block(String name, Supplier<Block> factory) {
        ensureOpen();
        Identifier id = id(name);
        Holder<Block> holder = new Holder<>();
        registrations.add(() -> holder.value = Registry.register(BuiltInRegistries.BLOCK, id, factory.get()));
        return new SimpleRegistryRef<>(id, holder);
    }

    @Override
    public boolean isRegistered() {
        return registered;
    }

    @Override
    public void registerAll() {
        if (registered) {
            return;
        }

        registered = true;
        registrations.forEach(Runnable::run);
    }

    @Override
    public Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(modId, name);
    }

    private void ensureOpen() {
        if (registered) {
            throw new IllegalStateException("Cannot add new registrations for " + modId + " after registerAll was called.");
        }
    }

    private static final class Holder<T> implements Supplier<T> {
        private T value;

        @Override
        public T get() {
            if (value == null) {
                throw new IllegalStateException("Tried to access a registered value before registerAll was called.");
            }
            return value;
        }
    }
}
