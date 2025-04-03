package com.coocle.vinnast.Response;

import java.time.LocalDate;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    String id;
    String username;
    String firstname;
    String lastname;
    LocalDate dob;
    Set<RoleResponse> roles;
}
