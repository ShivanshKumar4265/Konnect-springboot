package com.salker.konnect.dao;

import com.salker.konnect.POJO.ConnectionIdAuthCode;
import com.salker.konnect.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmail(@Param("email") String email);
}
