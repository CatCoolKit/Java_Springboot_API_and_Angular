package com.coocle.vinnast.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coocle.vinnast.Entity.Role;
import com.coocle.vinnast.Entity.User;
import com.coocle.vinnast.Repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("adminSAO").isEmpty()) {
                var roles = new HashSet<com.coocle.vinnast.Entity.Role>();
                roles.add(Role.builder()
                        .name("ADMIN")
                        .description("This is Admin role")
                        .build());
                User user = User.builder()
                        .username("adminSAO")
                        .password(passwordEncoder.encode("123456789"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn(
                        "admin user has been created with default username: adminSAO, password: 123456789, please change it");
            }
        };
    }
}
