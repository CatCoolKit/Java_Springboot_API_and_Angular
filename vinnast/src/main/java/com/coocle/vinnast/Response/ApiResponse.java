package com.coocle.vinnast.Response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    int code = 1000;
    String message;
    T result;
}
