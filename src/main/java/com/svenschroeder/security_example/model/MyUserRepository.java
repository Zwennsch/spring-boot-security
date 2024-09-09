package com.svenschroeder.security_example.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    /*
     * This method has to be exactly named this way:
     * Spring calls this method query derivation.
     * FindBy and then the name of the field is to be queried.
     */
    Optional<MyUser> findByUsername(String username);

}
