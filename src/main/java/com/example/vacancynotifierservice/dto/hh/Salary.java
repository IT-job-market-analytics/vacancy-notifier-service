package com.example.vacancynotifierservice.dto.hh;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Salary {
    @JsonProperty("from")
    private int from;
    @JsonProperty("to")
    private int to;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("gross")
    private boolean gross;
    @JsonProperty("from")
    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }
    @JsonProperty("to")
    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    @JsonProperty("gross")
    public boolean isGross() {
        return gross;
    }

    public void setGross(boolean gross) {
        this.gross = gross;
    }
}
