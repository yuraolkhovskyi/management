package api.config;

import com.sombra.management.util.JacksonUtil;
import lombok.Data;

import java.io.File;
import java.util.Objects;

@Data
public class TestProperty {

    public static final RestPropertyModel PROPERTIES;
    private static final String PATH_TO_YML_API_PROFILE = "src/test/resources/application-api.yml";

    static {
        final File file = new File(Objects.requireNonNull(PATH_TO_YML_API_PROFILE));
        PROPERTIES = JacksonUtil.readYml(file, RestPropertyModel.class);
    }

}
