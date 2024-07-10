<!--suppress HtmlDeprecatedTag, XmlDeprecatedElement, HtmlDeprecatedAttribute -->
<center><p align="center">A drop-in reflective configuration library suitable for java projects, including minecraft mods. </p></center> 

---

Kaleido is a standalone JIJ-able implementation of [Quilt Config](https://github.com/QuiltMC/quilt-config).

It's completely environment independent, requiring only a path to a configuration folder.

Kaleido shadows Quilt Config to minimize size and avoid classpath conflicts.

#### Quilt Config 1.2+

Since 1.2, Quilt Config has made a lot of changes that seem to pay full attention to its standalone usage, as well as the importance of keeping its minecraft-dependent code and minecraft-independent code separate.

This does include inbuilt serializers (that we originally had to shadow), but not nicely exposing the API for arbitrary config paths or solving potential conflicts introduced by updating the loader on an old minecraft version.

Shoutouts to ix0rai et al's work on this so far! Here's to potentially solving the "single mixin mod that's only incompatible with a version because of a bundled config screen" problem for good.

### Usage

```groovy
repositories {
    maven { url 'https://repo.sleeping.town/' }
}

dependencies {
    implementation 'folk.sisby:kaleido-config:0.3.1+1.3.1'
    include 'folk.sisby:kaleido-config:0.3.1+1.3.1'
}
```

Config usage is best defined by the [Wiki](https://github.com/QuiltMC/developer-wiki/blob/main/wiki/configuration/getting-started/en.md).

The only difference is the way to create the config instance - here's a sample:

```java
public class CoolMainClass {
    public static final CoolNewConfig CONFIG = CoolNewConfig.createToml(FabricLoader.getInstance().getConfigDir(), "coolFolder", "coolFilename", CoolNewConfig.class);
    
    public static void main() {
        Random random = new Random();
        if(CONFIG.enabled) {
            do {
                System.out.println("Hi " + CONFIG.coolNames.get(random.nextInt(CONFIG.coolNames.size()));
            } while (random.nextFloat() <= CONFIG.easterEggs.repetitionChance)
        }
    }
}
```

The wiki covers ReflectiveConfig, which **supports changing the value of primitives from code** and is a little more type-robust.

If you'd prefer to use WrappedConfig, which lacks these features but **avoids the need to import config packages anywhere but your config class** - here's quick sample:

```java
import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

public class CoolNewConfig extends WrappedConfig {
    @Comment("Whether to greet you on startup via the log")
    public Boolean enabled = false;
    /* Supports boolean, int, long, double, float, String, or any enum */
    
    @Comment("A list of names to call you in the logs")
    public List<String> coolNames = ValueList.create("", "buddy", "pal", "amigo");
    /* Also supports Map<String, T> via ValueMap.create() */

    public EasterEggs easterEggs = new EasterEggs();
    public static class EasterEggs implements Section {
        @Comment("The chance for the greeting functionality to be run again (applies recursively)")
        @FloatRange(min=0.0D, max=0.9D) /* Also supports @IntegerRange and @Matches(regex) */
        public double repetitionChance = 0.1D;
    }
}
```

Note that you can add to and remove from maps and lists (e.g. to auto-populate data-driven options), and this will be reflected in-file, even in WrappedConfig.

Usage Examples: 
[Euphonium](https://github.com/sisby-folk/euphonium/blob/1.20/src/main/java/folk/sisby/euphonium/EuphoniumConfig.java),
[Switchy](https://github.com/sisby-folk/switchy/blob/1.19/core/src/main/java/folk/sisby/switchy/SwitchyConfig.java),
[Crunchy Crunchy](https://github.com/sisby-folk/crunchy-crunchy-advancements/blob/1.18/src/main/java/folk/sisby/crunchy_crunchy_advancements/CrunchyConfig.java),
[PicoHUD](https://github.com/sisby-folk/picohud/blob/1.19/src/main/java/folk/sisby/picohud/PicoHudConfig.java),
[Inventory Tabs](https://github.com/sisby-folk/inventory-tabs/blob/1.19/src/main/java/folk/sisby/inventory_tabs/InventoryTabsConfig.java),
[Starcaller](https://github.com/sisby-folk/starcaller/blob/1.20.4/src/main/java/folk/sisby/starcaller/StarcallerConfig.java).
