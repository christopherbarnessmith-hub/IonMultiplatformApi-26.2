package com.ionapi.neoforge;

import com.ionapi.registry.IonRegistrar;
import net.neoforged.bus.api.IEventBus;

public final class IonNeoForge {
    public static void register(IEventBus modBus, IonRegistrar registrar) {
        if (!(registrar instanceof NeoForgeRegistrar neoForgeRegistrar)) {
            throw new IllegalArgumentException("Expected a NeoForge registrar, got " + registrar.getClass().getName());
        }

        neoForgeRegistrar.register(modBus);
    }

    private IonNeoForge() {
    }
}
