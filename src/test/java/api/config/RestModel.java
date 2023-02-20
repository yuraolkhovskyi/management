package api.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RestModel {

    @JsonProperty("base-url")
    private String baseUrl;
    @JsonProperty("api")
    private ApiPropertyModel api;
}
