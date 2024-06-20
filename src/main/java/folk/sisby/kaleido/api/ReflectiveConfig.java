package folk.sisby.kaleido.api;

import org.quiltmc.config.api.Config;
import org.quiltmc.config.impl.ConfigImpl;
import org.quiltmc.config.implementor_api.ConfigEnvironment;

import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("unused")
public abstract class ReflectiveConfig extends org.quiltmc.config.api.ReflectiveConfig {
    public static <T extends ReflectiveConfig> T create(ConfigEnvironment environment, String folderName, String fileName, Class<T> configCreatorClass) {
        return ConfigImpl.createReflective(environment, folderName, fileName, Paths.get(""), b -> {}, configCreatorClass, b -> {});
    }

    public static <T extends ReflectiveConfig> T createToml(Path configPath, String folderName, String fileName, Class<T> configCreatorClass) {
        return create(KaleidoConfig.tomlEnvironment(configPath), folderName, fileName, configCreatorClass);
    }

    public static <T extends ReflectiveConfig> T createJson5(Path configPath, String folderName, String fileName, Class<T> configCreatorClass) {
        return create(KaleidoConfig.json5Environment(configPath), folderName, fileName, configCreatorClass);
    }

    @SuppressWarnings("deprecation")
    public interface Section extends Config.Section {
    }
}
