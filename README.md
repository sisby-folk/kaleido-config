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
    implementation 'folk.sisby:kaleido-config:0.1+1.0.1-beta.3'
    include 'folk.sisby:kaleido-config:0.1+1.0.1-beta.3'
}
```

```
import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

public class CoolNewConfig extends WrappedConfig {
    @Comment("Enables the functionality!")
    public final Boolean enabled = false;
    
    @Comment("A list of names to call you in the debug logs")
    public final List<String> coolNames = ValueList.create("", "buddy", "pal", "amigo");
}
```
Field restrictions can be found [here](https://github.com/QuiltMC/quilt-config/blob/18eb16f8bc33afd026ebe22eac62a5613db0395a/src/main/java/org/quiltmc/config/api/Config.java#L183-L193).

```
/* Main Class / Initializer */
public class CoolProject {
    public static final CoolNewConfig CONFIG = CoolNewConfig.createToml(FabricLoader.getInstance().getConfigDir(), "coolFolder", "coolFilename", CoolNewConfig.class);
    
    public static void main() {
        System.out.println(CONFIG.coolNames.get(new Random().nextInt(CONFIG.coolNames.size()));
    }
}
```