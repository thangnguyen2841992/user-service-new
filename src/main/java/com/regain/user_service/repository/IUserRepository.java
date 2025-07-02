package com.regain.user_service.repository;

import com.regain.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository  extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);


    @Query(value = "select * from user a inner join role_id b on b.user_user_id = a.user_id inner join role c on b.roles_role_id = c.role_id where c.role_name = 'ROLE_USER'", nativeQuery = true)
    List<User> findAllUsers();
}
