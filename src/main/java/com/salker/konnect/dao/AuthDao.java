package com.salker.konnect.dao;

import com.salker.konnect.POJO.ConnectionIdAuthCode;
import com.salker.konnect.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AuthDao extends JpaRepository<ConnectionIdAuthCode, Integer> {

    ConnectionIdAuthCode findByConnectionId(@Param("connection_id") String connection_id);
    ConnectionIdAuthCode findByUserId(@Param("user_id") Integer user_id);
}
