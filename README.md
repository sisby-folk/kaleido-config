# Kaleido

A JIJ-safe implementation of [Quilt Config](https://github.com/QuiltMC/quilt-config) (a really good pure-java config library)

If used in a Minecraft mod, players can pair the mod with [McQoy](modrinth.com/mod/mcqoy) at runtime for in-game configuration.

## Usage

First, include the library:

```groovy
repositories {
    maven { url 'https://repo.sleeping.town/' }
}

dependencies {
    implementation 'folk.sisby:kaleido-config:0.3.3+1.3.2'
    include 'folk.sisby:kaleido-config:0.3.3+1.3.2'
}
```

Then, create a config class. Note that fields must be non-final, but shouldn't be reassigned ([use `ReflectiveConfig` instead]([wiki](https://github.com/QuiltMC/developer-wiki/blob/main/wiki/configuration/getting-started/en.md))).

```java
public class GreeterConfig extends WrappedConfig {
    @Comment("Whether to greet you on startup via the log")
    public boolean enabled = false;
    /* Supports boolean, int, long, double, float, String, or any enum */
    
    @Comment("A list of names to call you in the logs")
    public List<String> coolNames = ValueList.create("", "buddy", "pal", "amigo");
    /* Also supports Map<String, T> via ValueMap.create() */
    /* You can modify these programmatically via standard map/list methods (even in WrappedConfig) */

    public EasterEggs easterEggs = new EasterEggs();
    public static class EasterEggs implements Section {
        @Comment("The chance for the greeting functionality to be run again (applies recursively)")
        @FloatRange(min=0.0F, max=0.9F) /* Also supports @IntegerRange and @Matches(regex) */
        public float repetitionChance = 0.1F;
    }
}
```

Lastly, use the `createToml` helper to create an instance of the config bound to a file:

```java
public class Greeter {
    // The file is created when this field is initialized, so put it inside a class with early-run / initializer code.
    public static final GreeterConfig CONFIG = GreeterConfig.createToml(/* config path: */ Path.of(""), /* parent folder (for multiple config files): */ "", /* file name: */ "greeter", GreeterConfig.class);

    public static void main(String[] args) {
        if (CONFIG.enabled) {
            Random random = new Random();
            do {
                System.out.printf("Hi %s: %s", CONFIG.coolNames.get(random.nextInt(CONFIG.coolNames.size()), String.join(", ", args)));
            } while (random.nextFloat() <= CONFIG.easterEggs.repetitionChance);
        }
    }
}
```

**More complex config classes:**
- [PicoHUD](https://github.com/sisby-folk/picohud/blob/1.19/src/main/java/folk/sisby/picohud/PicoHudConfig.java) - with a self-contained instance, which is a nice pattern for single-mixin mods
- [Item Descriptions](https://github.com/cassiancc/Item-Descriptions/blob/next-1.10/src/main/java/cc/cassian/item_descriptions/client/config/ModConfig.java) - using ReflectiveConfig
- [Surveyor](https://github.com/sisby-folk/surveyor/blob/1.20/src/main/java/folk/sisby/surveyor/config/SurveyorConfig.java) - with heavy use of sections and enums
- [Inventory Tabs](https://github.com/sisby-folk/inventory-tabs/blob/1.19/src/main/java/folk/sisby/inventory_tabs/InventoryTabsConfig.java) - with heavy usage of comments and data-driven maps

## Breakages

We won't publish a breaking version of Kaleido. If we're ever sold on a breaking change, we'll change the project classpath to prevent JIJ conflicts.

## Afterword

This project isn't intended to steal attention from the actual effort put into Quilt Config - we're just re-exposing it in another context.

Quilt Config is an extremely nice to use config solution, and in this (trivially created) form, it's extremely powerful as a version-universal, platform-universal way to define configuration for minecraft mods.
We felt this was going to go unacknowledged if it wasn't openly supported (or had finicky issues when JIJ'd alongside Quilt Loader, as raw QConf does) - so Kaleido seeks to rectify that.

If the original Quilt Config repo is ever archived or the maven goes down, we will convert this repo into a fork and re-create open issues.
