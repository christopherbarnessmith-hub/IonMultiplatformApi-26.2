package com.ionapi.registry;

import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public final class SimpleRegistryRef<T> implements RegistryRef<T> {
    private final Identifier id;
    private final Supplier<T> value;

    public SimpleRegistryRef(Identifier id, Supplier<T> value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public T get() {
        return value.get();
    }

    @Override
    public Identifier id() {
        return id;
    }
}
