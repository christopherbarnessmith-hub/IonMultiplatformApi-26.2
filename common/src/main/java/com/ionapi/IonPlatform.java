package com.ionapi;

import com.ionapi.internal.PlatformAdapter;
import com.ionapi.registry.IonRegistrar;

import java.util.ServiceLoader;

public final class IonPlatform {
    private static final PlatformAdapter ADAPTER = ServiceLoader.load(PlatformAdapter.class)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No IonPlatform adapter was found."));

    public static String loaderName() {
        return ADAPTER.loaderName();
    }

    public static Env environment() {
        return ADAPTER.environment();
    }

    public static boolean isClient() {
        return environment() == Env.CLIENT;
    }

    public static boolean isModLoaded(String modId) {
        return ADAPTER.isModLoaded(modId);
    }

    public static IonRegistrar registrar(String modId) {
        return ADAPTER.registrar(modId);
    }

    private IonPlatform() {
    }
}
