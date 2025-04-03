package com.coocle.vinnast.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coocle.vinnast.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
