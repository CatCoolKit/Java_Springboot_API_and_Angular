package com.coocle.vinnast.Services;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coocle.vinnast.Mapper.RoleMapper;
import com.coocle.vinnast.Repositories.Permissionepository;
import com.coocle.vinnast.Repositories.RoleRepository;
import com.coocle.vinnast.Response.RoleResponse;
import com.coocle.vinnast.request.RoleRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {

    RoleRepository repository;
    Permissionepository permissionepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = repository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        var roles = repository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role) {
        repository.deleteById(role);
    }
}
