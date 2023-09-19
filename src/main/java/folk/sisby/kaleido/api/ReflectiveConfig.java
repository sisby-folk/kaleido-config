package folk.sisby.kaleido.api;

import org.quiltmc.config.impl.ConfigImpl;
import org.quiltmc.config.implementor_api.ConfigEnvironment;

import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("unused")
public abstract class ReflectiveConfig extends org.quiltmc.config.api.ReflectiveConfig {
    public static <T extends ReflectiveConfig> T create(ConfigEnvironment environment, String familyId, String id, Class<T> configCreatorClass) {
        return ConfigImpl.createReflective(environment, familyId, id, Paths.get(""), b -> {}, configCreatorClass, b -> {});
    }

    public static <T extends ReflectiveConfig> T createToml(Path configPath, String familyId, String id, Class<T> configCreatorClass) {
        return create(KaleidoConfig.tomlEnvironment(configPath), familyId, id, configCreatorClass);
    }
}
