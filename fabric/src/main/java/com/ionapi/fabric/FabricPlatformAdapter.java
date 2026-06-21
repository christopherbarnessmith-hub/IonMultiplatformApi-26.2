package com.ionapi.fabric;

import com.ionapi.Env;
import com.ionapi.internal.PlatformAdapter;
import com.ionapi.registry.IonRegistrar;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public final class FabricPlatformAdapter implements PlatformAdapter {
    @Override
    public String loaderName() {
        return "fabric";
    }

    @Override
    public Env environment() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? Env.CLIENT : Env.SERVER;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public IonRegistrar registrar(String modId) {
        return new FabricRegistrar(modId);
    }
}
