package com.example.springecom.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(long id);
    boolean existsById(long id);

    Optional<User> getUserByUsername(String username);

//    @Modifying
//    @Query("UPDATE User u SET u.username = :#{#user.username}," +
//            "u.password = :#{#user.password}, " +
//            "u.email = :#{#user.email} WHERE u.id = #{#user.id}")
//    void updateUserById(@Param("user") User user);
}
