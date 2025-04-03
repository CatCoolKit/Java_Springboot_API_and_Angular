package com.meta.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderRecord(
        String customerName,
        String productName,
        int quantity) {

}
