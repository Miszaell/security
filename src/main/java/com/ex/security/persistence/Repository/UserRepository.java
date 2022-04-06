package com.ex.security.persistence.Repository;

import com.ex.security.persistence.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET name=:name WHERE id=:id", nativeQuery = true)
    public void updateUserName(
            @Param("id") Long id,
            @Param("name") String name
    );

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET password=:password WHERE id=:id", nativeQuery = true)
    public void updateUserPassword(
            @Param("id") Long id,
            @Param("password") String password
    );
}
