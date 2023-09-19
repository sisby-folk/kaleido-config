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
public class CoolNewConfig extends ReflectiveConfig {
    @Comment("Enables the functionality!")
    public static boolean enabled = false;
}
```

Feel free to read [these docs](https://github.com/QuiltMC/quilt-config/blob/18eb16f8bc33afd026ebe22eac62a5613db0395a/src/main/java/org/quiltmc/config/api/Config.java#L143-L171) for information on field types.

```
/* Main Class / Initializer */
public class CoolProject {
    public static final CoolNewConfig CONFIG = KaleidoConfig.createToml(FabricLoader.getInstance().getConfigDir(), "coolFolder", "coolFilename", CoolNewConfig.class);
}
```