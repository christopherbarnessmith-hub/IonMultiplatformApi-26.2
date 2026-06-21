package com.ionapi.registry;

import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public interface RegistryRef<T> extends Supplier<T> {
    Identifier id();
}
