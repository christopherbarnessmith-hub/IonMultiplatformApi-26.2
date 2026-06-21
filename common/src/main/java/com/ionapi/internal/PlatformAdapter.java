package com.ionapi.internal;

import com.ionapi.Env;
import com.ionapi.registry.IonRegistrar;

public interface PlatformAdapter {
    String loaderName();

    Env environment();

    boolean isModLoaded(String modId);

    IonRegistrar registrar(String modId);
}
