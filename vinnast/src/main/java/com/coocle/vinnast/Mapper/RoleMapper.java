package com.coocle.vinnast.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coocle.vinnast.Entity.Role;
import com.coocle.vinnast.Response.RoleResponse;
import com.coocle.vinnast.request.RoleRequest;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
