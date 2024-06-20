package folk.sisby.kaleido.api;

import org.quiltmc.config.api.serializers.Json5Serializer;
import org.quiltmc.config.implementor_api.ConfigEnvironment;

import java.nio.file.Path;

public class KaleidoConfig {
    public static ConfigEnvironment json5Environment(Path configPath) {
        return new ConfigEnvironment(configPath, "json5", Json5Serializer.INSTANCE);
    }
}
