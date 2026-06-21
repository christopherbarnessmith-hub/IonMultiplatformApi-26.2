# Ion Multiplatform API

A tiny personal Fabric + NeoForge helper API. This is intentionally much smaller than Architectury.

The goal is to let your shared mod code use one common API while each loader keeps its own small entrypoint.

## Project layout

```text
common/   Shared API interfaces and helper classes.
fabric/   Fabric implementation and Fabric jar.
neoforge/ NeoForge implementation and NeoForge jar.
```

## Shared mod code example

```java
package com.example.examplemod;

import com.ionapi.IonPlatform;
import com.ionapi.registry.IonRegistrar;
import com.ionapi.registry.RegistryRef;
import net.minecraft.world.item.Item;

public final class ExampleMod {
    public static final String MOD_ID = "examplemod";
    public static final IonRegistrar REGISTRAR = IonPlatform.registrar(MOD_ID);

    public static final RegistryRef<Item> RUBY = REGISTRAR.item(
            "ruby",
            () -> new Item(new Item.Properties().setId(REGISTRAR.itemKey("ruby")))
    );

    public static void init() {
    }

    private ExampleMod() {
    }
}
```

## Fabric mod entrypoint example

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

## NeoForge mod entrypoint example

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

## First things to customize

The scaffold is currently targeted at the `26.2` stack used by your current Ion mods:

```text
minecraft_version=26.2
fabric_loader_version=0.19.2
fabric_api_version=0.150.0+26.2
fabric_loom_version=1.16-SNAPSHOT
```

Edit `gradle.properties` if you want to retarget the whole API:

- `minecraft_version`
- `fabric_loader_version`
- `fabric_api_version`
- `neoforge_version`
- `java_version`

Keep Fabric and NeoForge targeting the same Minecraft version.

## Build

```text
gradlew build
```

The output jars will be under:

```text
fabric/build/libs
neoforge/build/libs
```

To publish both loader jars into a local folder that your mods can depend on:

```text
gradlew publishAllPublicationsToLocalIonModsRepository
```

That writes artifacts to:

```text
build/repo
```

## JitPack Maven

Ion API is published through JitPack from GitHub tags and commits. This repository includes `jitpack.yml`, which tells JitPack to use Java 25 and run:

```text
./gradlew build publishToMavenLocal --no-problems-report
```

Add JitPack to your Gradle repositories:

```groovy
repositories {
    maven {
        name = "JitPack"
        url = uri("https://jitpack.io")
    }
}
```

Use the loader-specific artifact in the matching loader module. Replace `TAG` with the GitHub release tag or commit hash you want to depend on:

```groovy
// Fabric
modImplementation "com.github.christopherbarnessmith-hub.IonMultiplatformApi-26.2:ionapi-fabric:TAG"

// NeoForge
implementation "com.github.christopherbarnessmith-hub.IonMultiplatformApi-26.2:ionapi-neoforge:TAG"
```

For the first public release, create a GitHub release/tag named `1.0.0`, then use:

```groovy
modImplementation "com.github.christopherbarnessmith-hub.IonMultiplatformApi-26.2:ionapi-fabric:1.0.0"
implementation "com.github.christopherbarnessmith-hub.IonMultiplatformApi-26.2:ionapi-neoforge:1.0.0"
```

## Using this from one of your mods

In a real mod workspace, your Fabric mod depends on the Fabric Ion API jar, and your NeoForge mod depends on the NeoForge Ion API jar. Your shared/common code should import only packages like:

```text
com.ionapi.IonPlatform
com.ionapi.registry.IonRegistrar
com.ionapi.registry.RegistryRef
```

Your shared/common mod code should not import:

```text
com.ionapi.fabric.*
com.ionapi.neoforge.*
net.fabricmc.*
net.neoforged.*
```

Only your loader entrypoint classes should touch loader-specific APIs.

For a fuller copy-paste example, see `docs/using-ionapi-in-a-mod.md`.

## Next API pieces to add

Good next steps:

- creative tab helper
- sound registration
- block entity registration
- simple lifecycle events
- small networking wrapper

Avoid adding entities, screens, worldgen, and rendering until one of your mods actually needs them.
