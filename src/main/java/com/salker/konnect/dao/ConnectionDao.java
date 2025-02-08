package com.salker.konnect.dao;

import com.salker.konnect.POJO.ConnectionIdAuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionDao extends JpaRepository<ConnectionIdAuthCode, Integer> {

}
