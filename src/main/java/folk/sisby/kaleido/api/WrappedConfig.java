package folk.sisby.kaleido.api;

import org.quiltmc.config.impl.ConfigImpl;
import org.quiltmc.config.implementor_api.ConfigEnvironment;

import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings({"deprecation", "unused"})
public abstract class WrappedConfig extends org.quiltmc.config.api.WrappedConfig {
    public static <T extends WrappedConfig> T create(ConfigEnvironment environment, String familyId, String id, Class<T> configCreatorClass) {
        return ConfigImpl.create(environment, familyId, id, Paths.get(""), b -> {}, configCreatorClass, b -> {});
    }

    public static <T extends WrappedConfig> T createToml(Path configPath, String familyId, String id, Class<T> configCreatorClass) {
        return create(KaleidoConfig.tomlEnvironment(configPath), familyId, id, configCreatorClass);
    }
}
