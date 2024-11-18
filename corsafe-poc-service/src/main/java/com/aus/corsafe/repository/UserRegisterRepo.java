package com.aus.corsafe.repository;

import com.aus.corsafe.entity.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRegisterRepo extends JpaRepository<UserRegister,Integer> {
    Optional<UserRegister> findByEmail(String email);
    Optional<UserRegister> findByUserId(Integer id);
}
