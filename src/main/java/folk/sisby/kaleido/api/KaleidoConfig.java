package folk.sisby.kaleido.api;

import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import folk.sisby.kaleido.impl.NightConfigSerializer;
import org.quiltmc.config.implementor_api.ConfigEnvironment;

import java.nio.file.Path;

public class KaleidoConfig {
    public static ConfigEnvironment tomlEnvironment(Path configPath) {
        return new ConfigEnvironment(configPath, "toml", new NightConfigSerializer<>("toml", new TomlParser(), new TomlWriter()));
    }
}