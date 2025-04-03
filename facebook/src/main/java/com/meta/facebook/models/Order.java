package com.meta.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Order {

    @JsonProperty("c-name")
    private String customerName;
    @JsonProperty("p-name")
    private String productName;
    @JsonProperty("q")
    private int quantity;
}
