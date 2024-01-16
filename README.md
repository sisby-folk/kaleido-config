<!--suppress HtmlDeprecatedTag, XmlDeprecatedElement, HtmlDeprecatedAttribute -->
<center><p align="center">A drop-in reflective configuration library suitable for minecraft mods. </p></center> 

---

Kaleido is a standalone JIJ-able implementation of [Quilt Config](https://github.com/QuiltMC/quilt-config).

It's completely environment independent, only requiring a path to a configuration folder.

It supports:
 - Toml (via NightConfig)

Kaleido shadows its dependencies to minimize size and avoid conflicts with other instances of Quilt Config on the classpath.

#### Quilt Config 1.2

As of 1.2, Quilt Config has adopted the TOML/JSON5 serializer classes originally added by Quilt Loader. This simplifies the implementation of Kaleido significantly, but quilt config is still unsafe to JIJ directly due to its inclusion in QL potentially causing version mismatches. Though now only 50 lines of wrapper code, kaleido does still solve this problem. 

### Usage

```
repositories {
    maven { url 'https://repo.sleeping.town/' }
}

dependencies {
    implementation 'folk.sisby:kaleido-config:0.1.1+1.1.0-beta.3'
    include 'folk.sisby:kaleido-config:0.1.1+1.1.0-beta.3'
}
```

```
import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

public class CoolNewConfig extends WrappedConfig {
    @Comment("Whether to greet you on startup via the log")
    public final Boolean enabled = false;
    /* Supports Boolean, Integer, Long, Double, Float, String, or any enum */
    
    @Comment("A list of names to call you in the logs")
    public final List<String> coolNames = ValueList.create("", "buddy", "pal", "amigo");
    /* Also supports Map<String, T> via ValueMap.create() */

    public final EasterEggs easterEggs = new EasterEggs();
    public static final class EasterEggs implements Section {
        @Comment("The chance for the greeting functionality to be run again (applies recursively)")
        @FloatRange(min=0.0D, max=0.9D) /* Also supports @IntegerRange and @Matches(regex) */
        public final Double repetitionChance = 0.1D;
    }
}
```

```
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

Usage Examples: 
[Euphonium](https://github.com/sisby-folk/euphonium/blob/1.20/src/main/java/folk/sisby/euphonium/EuphoniumConfig.java),
[Switchy](https://github.com/sisby-folk/switchy/blob/1.19/core/src/main/java/folk/sisby/switchy/SwitchyConfig.java),
[Crunchy Crunchy](https://github.com/sisby-folk/crunchy-crunchy-advancements/blob/1.18/src/main/java/folk/sisby/crunchy_crunchy_advancements/CrunchyConfig.java),
[PicoHUD](https://github.com/sisby-folk/picohud/blob/1.19/src/main/java/folk/sisby/picohud/PicoHudConfig.java),
[Inventory Tabs](https://github.com/sisby-folk/inventory-tabs/blob/1.19/src/main/java/folk/sisby/inventory_tabs/InventoryTabsConfig.java),
[Starcaller](https://github.com/sisby-folk/starcaller/blob/1.20.4/src/main/java/folk/sisby/starcaller/StarcallerConfig.java).
