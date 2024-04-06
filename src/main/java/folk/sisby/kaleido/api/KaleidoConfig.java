package folk.sisby.kaleido.api;

import org.quiltmc.config.api.serializers.TomlSerializer;
import org.quiltmc.config.implementor_api.ConfigEnvironment;

import java.nio.file.Path;

public class KaleidoConfig {
    public static ConfigEnvironment tomlEnvironment(Path configPath) {
        return new ConfigEnvironment(configPath, "toml", TomlSerializer.INSTANCE);
    }
}
