package com.coocle.vinnast.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.coocle.vinnast.DTO.UserDTO;
import com.coocle.vinnast.Entity.User;
import com.coocle.vinnast.Exceptions.AppException;
import com.coocle.vinnast.Repositories.UserRepository;
import com.coocle.vinnast.Response.UserResponse;
import com.coocle.vinnast.Services.UserService;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {

    // spotless:off
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserDTO request;
    private UserResponse response;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);
        request = UserDTO.builder()
                .username("NguyenVanA")
                .password("12345678")
                .firstname("Henry")
                .lastname("Porter")
                .dob(dob)
                .build();

        response = UserResponse.builder()
                .id("cf0600f538b3")
                .username("NguyenVanA")
                .firstname("Henry")
                .lastname("Porter")
                .dob(dob)
                .build();

        user = User.builder()
                .id("cf0600f538b3")
                .username("NguyenVanA")
                .firstname("Henry")
                .lastname("Porter")
                .dob(dob)
                .build();
    }
    // spotless:on

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createUser(request);

        // THEN

        assertThat(response.getId()).isEqualTo("cf0600f538b3");
        assertThat(response.getUsername()).isEqualTo("NguyenVanA");
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN

        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
    }

    @Test
    @WithMockUser(username = "NguyenVanA")
    void getMyInfo_valid_success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfo();

        assertThat(response.getUsername()).isEqualTo("NguyenVanA");
        assertThat(response.getId()).isEqualTo("cf0600f538b3");
    }

    @Test
    @WithMockUser(username = "NguyenVanA")
    void getMyInfo_userNotFound_error() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());

        assertThat(exception.getErrorCode().getCode()).isEqualTo(1006);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("User not existed!");
    }
}
