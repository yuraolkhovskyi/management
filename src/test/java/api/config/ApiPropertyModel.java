package api.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ApiPropertyModel {

    @JsonProperty("register")
    private String registerUri;
    @JsonProperty("authenticate")
    private String authenticateApiUri;

}
