# Using Ion API in one of your mods

This assumes your mod is also split into shared, Fabric, and NeoForge source.

This API project is currently targeting the `26.2` stack. Your mod should use the same `minecraft_version` as the API jar it depends on.

## Shared code

Your shared code imports only the common Ion API:

```java
package com.example.examplemod;

import com.ionapi.IonPlatform;
import com.ionapi.registry.IonRegistrar;
import com.ionapi.registry.RegistryRef;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class ExampleMod {
    public static final String MOD_ID = "examplemod";
    public static final IonRegistrar REGISTRAR = IonPlatform.registrar(MOD_ID);

    public static final RegistryRef<Item> RUBY = REGISTRAR.item(
            "ruby",
            () -> new Item(new Item.Properties().setId(REGISTRAR.itemKey("ruby")))
    );

    public static final RegistryRef<Block> RUBY_BLOCK = REGISTRAR.blockWithItem(
            "ruby_block",
            () -> new Block(Block.Properties.of().setId(REGISTRAR.blockKey("ruby_block"))),
            new Item.Properties()
    );

    public static void init() {
    }

    private ExampleMod() {
    }
}
```

## Fabric entrypoint

```java
package com.example.examplemod.fabric;

import com.example.examplemod.ExampleMod;
import net.fabricmc.api.ModInitializer;

public final class ExampleModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ExampleMod.init();
        ExampleMod.REGISTRAR.registerAll();
    }
}
```

## NeoForge entrypoint

```java
package com.example.examplemod.neoforge;

import com.example.examplemod.ExampleMod;
import com.ionapi.neoforge.IonNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge(IEventBus modBus) {
        ExampleMod.init();
        IonNeoForge.register(modBus, ExampleMod.REGISTRAR);
    }
}
```

## Dependency shape

Fabric source depends on the Fabric Ion API artifact:

```groovy
modImplementation "com.ionapi:ionapi-fabric:0.1.0"
```

NeoForge source depends on the NeoForge Ion API artifact:

```groovy
implementation "com.ionapi:ionapi-neoforge:0.1.0"
```

If you publish this API with `publishAllPublicationsToLocalIonModsRepository`, add this repository to your mods:

```groovy
repositories {
    maven {
        url = uri("C:/Modding/IonMultiplatformApi-scaffold/build/repo")
    }
}
```

## Rule of thumb

Shared/common mod code can use:

```text
com.ionapi.*
net.minecraft.*
```

Shared/common mod code should not use:

```text
net.fabricmc.*
net.neoforged.*
com.ionapi.fabric.*
com.ionapi.neoforge.*
```
