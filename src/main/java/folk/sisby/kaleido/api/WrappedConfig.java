package folk.sisby.kaleido.api;

import org.quiltmc.config.api.Config;
import org.quiltmc.config.impl.ConfigImpl;
import org.quiltmc.config.implementor_api.ConfigEnvironment;

import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings({"deprecation", "unused"})
public abstract class WrappedConfig extends org.quiltmc.config.api.WrappedConfig {
    public static <T extends WrappedConfig> T create(ConfigEnvironment environment, String folderName, String fileName, Class<T> configCreatorClass) {
        return ConfigImpl.create(environment, folderName, fileName, Paths.get(""), b -> {}, configCreatorClass, b -> {});
    }

    public static <T extends WrappedConfig> T createJson5(Path configPath, String folderName, String fileName, Class<T> configCreatorClass) {
        return create(KaleidoConfig.json5Environment(configPath), folderName, fileName, configCreatorClass);
    }

    @SuppressWarnings("deprecation")
    public interface Section extends Config.Section {
    }
}
