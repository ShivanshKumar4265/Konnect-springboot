package com.salker.konnect.dao;

import com.salker.konnect.POJO.ConnectionIdAuthCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthDao extends JpaRepository<ConnectionIdAuthCode, Integer> {

    ConnectionIdAuthCode findByConnectionId(@Param("connection_id") String connection_id);

    @Query("SELECT c FROM ConnectionIdAuthCode c WHERE c.connectionId = :connectionId AND c.authCode = :authCode")
    ConnectionIdAuthCode findByConnectionIdAndAuthCode(@Param("connectionId") String connectionId, @Param("authCode") String authCode);
}
