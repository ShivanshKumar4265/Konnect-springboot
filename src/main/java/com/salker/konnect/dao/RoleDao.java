package com.salker.konnect.dao;

import com.salker.konnect.POJO.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {
    Role findByRole(String role);
}
