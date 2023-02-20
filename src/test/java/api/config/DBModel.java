package api.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DBModel {

    @JsonProperty("url")
    private String url;
    @JsonProperty("user")
    private String user;
    @JsonProperty("password")
    private String password;

}
