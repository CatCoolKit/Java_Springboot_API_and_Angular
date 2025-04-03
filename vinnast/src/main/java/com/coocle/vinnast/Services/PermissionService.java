package com.coocle.vinnast.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coocle.vinnast.Entity.Permission;
import com.coocle.vinnast.Mapper.PermissionMapper;
import com.coocle.vinnast.Repositories.Permissionepository;
import com.coocle.vinnast.Response.PermissionResponse;
import com.coocle.vinnast.request.PermissionRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    Permissionepository permissionepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        permission = permissionepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void deletePermission(String permission) {
        permissionepository.deleteById(permission);
    }
}
