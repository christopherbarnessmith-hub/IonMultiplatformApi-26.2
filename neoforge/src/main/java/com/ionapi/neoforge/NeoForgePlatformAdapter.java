package com.ionapi.neoforge;

import com.ionapi.Env;
import com.ionapi.internal.PlatformAdapter;
import com.ionapi.registry.IonRegistrar;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;

public final class NeoForgePlatformAdapter implements PlatformAdapter {
    @Override
    public String loaderName() {
        return "neoforge";
    }

    @Override
    public Env environment() {
        return FMLEnvironment.getDist().isClient() ? Env.CLIENT : Env.SERVER;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public IonRegistrar registrar(String modId) {
        return new NeoForgeRegistrar(modId);
    }
}
