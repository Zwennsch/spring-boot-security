package com.svenschroeder.security_example.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long>{
    
    Optional<MyUser> findUserByName(String username);

}
