package com.ionapi.fabric;

import net.fabricmc.api.ModInitializer;

public final class IonApiFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // The API is activated through ServiceLoader when a dependent mod calls IonPlatform.
    }
}
