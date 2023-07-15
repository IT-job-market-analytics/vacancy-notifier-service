
package com.example.vacancynotifierservice.dto.hh;

import com.fasterxml.jackson.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "items",
        "found",
        "pages",
        "per_page",
        "page",
        "clusters",
        "arguments",
        "alternate_url"
})
public class Vacancies {

    @JsonProperty("items")
    private List<Vacancy> vacancies;
    @JsonProperty("found")
    private Integer found;
    @JsonProperty("pages")
    private Integer pages;
    @JsonProperty("per_page")
    private Integer perPage;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("clusters")
    private Object clusters;
    @JsonProperty("arguments")
    private Object arguments;
    @JsonProperty("alternate_url")
    private String alternateUrl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<>();

    @JsonProperty("items")
    public List<Vacancy> getItems() {
        return vacancies;
    }

    @JsonProperty("items")
    public void setItems(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    @JsonProperty("found")
    public Integer getFound() {
        return found;
    }

    @JsonProperty("found")
    public void setFound(Integer found) {
        this.found = found;
    }

    @JsonProperty("pages")
    public Integer getPages() {
        return pages;
    }

    @JsonProperty("pages")
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    @JsonProperty("per_page")
    public Integer getPerPage() {
        return perPage;
    }

    @JsonProperty("per_page")
    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    @JsonProperty("page")
    public void setPage(Integer page) {
        this.page = page;
    }

    @JsonProperty("clusters")
    public Object getClusters() {
        return clusters;
    }

    @JsonProperty("clusters")
    public void setClusters(Object clusters) {
        this.clusters = clusters;
    }

    @JsonProperty("arguments")
    public Object getArguments() {
        return arguments;
    }

    @JsonProperty("arguments")
    public void setArguments(Object arguments) {
        this.arguments = arguments;
    }

    @JsonProperty("alternate_url")
    public String getAlternateUrl() {
        return alternateUrl;
    }

    @JsonProperty("alternate_url")
    public void setAlternateUrl(String alternateUrl) {
        this.alternateUrl = alternateUrl;
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
