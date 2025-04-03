package com.coocle.vinnast.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coocle.vinnast.Entity.Permission;

@Repository
public interface Permissionepository extends JpaRepository<Permission, String> {}
