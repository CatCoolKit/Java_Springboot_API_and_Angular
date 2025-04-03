package com.coocle.vinnast.Mapper;

import org.mapstruct.Mapper;

import com.coocle.vinnast.Entity.Permission;
import com.coocle.vinnast.Response.PermissionResponse;
import com.coocle.vinnast.request.PermissionRequest;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
