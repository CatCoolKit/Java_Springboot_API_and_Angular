package com.coocle.vinnast.DTO;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Size;

import com.coocle.vinnast.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @Size(min = 5, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String firstname;
    String lastname;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    List<String> roles;
}
