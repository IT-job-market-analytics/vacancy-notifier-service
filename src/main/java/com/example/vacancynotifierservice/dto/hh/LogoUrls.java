
package com.example.vacancynotifierservice.dto.hh;

import com.fasterxml.jackson.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "original",
        "90",
        "240"
})
public class LogoUrls {

    @JsonProperty("original")
    private String original;
    @JsonProperty("90")
    private String _90;
    @JsonProperty("240")
    private String _240;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<>();

    @JsonProperty("original")
    public String getOriginal() {
        return original;
    }

    @JsonProperty("original")
    public void setOriginal(String original) {
        this.original = original;
    }

    @JsonProperty("90")
    public String get90() {
        return _90;
    }

    @JsonProperty("90")
    public void set90(String _90) {
        this._90 = _90;
    }

    @JsonProperty("240")
    public String get240() {
        return _240;
    }

    @JsonProperty("240")
    public void set240(String _240) {
        this._240 = _240;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
