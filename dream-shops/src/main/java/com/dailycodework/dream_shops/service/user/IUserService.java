package com.dailycodework.dream_shops.service.user;

import com.dailycodework.dream_shops.dto.UserDTO;
import com.dailycodework.dream_shops.model.User;
import com.dailycodework.dream_shops.request.CreateUserRequest;
import com.dailycodework.dream_shops.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDTO convertUserToDTO(User user);

    User getAuthenticatedUser();
}
