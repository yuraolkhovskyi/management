package api.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RestPropertyModel {

    @JsonProperty("rest")
    private RestModel rest;
    @JsonProperty("db")
    private DBModel db;

}
