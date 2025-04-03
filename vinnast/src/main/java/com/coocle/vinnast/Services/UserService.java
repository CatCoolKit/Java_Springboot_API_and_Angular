package com.coocle.vinnast.Services;

import java.util.HashSet;
import java.util.List;

import com.coocle.vinnast.Entity.Role;
import com.coocle.vinnast.constant.PredefinedRole;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coocle.vinnast.DTO.UserDTO;
import com.coocle.vinnast.Entity.User;
import com.coocle.vinnast.Exceptions.AppException;
import com.coocle.vinnast.Exceptions.ErrorCode;
import com.coocle.vinnast.Mapper.UserMapper;
import com.coocle.vinnast.Repositories.RoleRepository;
import com.coocle.vinnast.Repositories.UserRepository;
import com.coocle.vinnast.Response.UserResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserDTO userDTO) {
        log.info("UserService: create user");

//        if (userRepository.existsByUsername(userDTO.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        //        var roles = roleRepository.findAllById(userDTO.getRoles());
        //
        //        user.setRoles(new HashSet<>(roles));
        //        HashSet<String> roles = new HashSet<>();
        //        roles.add(Role.USER.name());
        // roles.add(Role.STAFF.name());
         user.setRoles(roles);
        //        user.setUsername(userDTO.username());
        //        user.setPassword(userDTO.password());
        //        user.setFirstname(userDTO.firstname());
        //        user.setLastname(userDTO.password());
        //        user.setDob(userDTO.dob());

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    // @PreAuthorize("hasAuthority('CREATE_DATA')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> userMapper.toUserResponse(user))
                .toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String userId) {
        return userMapper.toUserResponse(
                userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!")));
    }

    public UserResponse updateUserById(String userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

        userMapper.updateUser(user, userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        //        user.setPassword(userDTO.getPassword());
        //        user.setFirstname(userDTO.getFirstname());
        //        user.setLastname(userDTO.getLastname());
        //        user.setDob(userDTO.getDob());

        var roles = roleRepository.findAllById(userDTO.getRoles());

        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
