<!--suppress HtmlDeprecatedTag, XmlDeprecatedElement, HtmlDeprecatedAttribute -->
<center><p align="center">A drop-in reflective configuration library suitable for minecraft mods. </p></center> 

---

Kaleido is a standalone JIJ-able implementation of [Quilt Config](https://github.com/QuiltMC/quilt-config).

It supports:
 - Toml (via NightConfig)

Kaleido shadows its dependencies to minimize size and avoid conflicts with other JIJ versions.

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
        @FloatRange(min=0.0D, max=0.9D) /* Also supports @IntegerRange(min, max) and @Matches(regex) */
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
