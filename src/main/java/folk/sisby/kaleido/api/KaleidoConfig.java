package folk.sisby.kaleido.api;

import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import folk.sisby.kaleido.impl.NightConfigSerializer;
import org.quiltmc.config.impl.ConfigImpl;
import org.quiltmc.config.implementor_api.ConfigEnvironment;

import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("unused")
public class KaleidoConfig {
    public static <T extends ReflectiveConfig> T create(ConfigEnvironment environment, String familyId, String id, Class<T> configCreatorClass) {
        return ConfigImpl.createReflective(environment, familyId, id, Paths.get(""), b -> {}, configCreatorClass, b -> {});
    }

    public static <T extends ReflectiveConfig> T createToml(Path configPath, String familyId, String id, Class<T> configCreatorClass) {
        return ConfigImpl.createReflective(tomlEnvironment(configPath), familyId, id, Paths.get(""), b -> {}, configCreatorClass, b -> {});
    }

    public static ConfigEnvironment tomlEnvironment(Path configPath) {
        return new ConfigEnvironment(configPath, "toml", new NightConfigSerializer<>("toml", new TomlParser(), new TomlWriter()));
    }
}