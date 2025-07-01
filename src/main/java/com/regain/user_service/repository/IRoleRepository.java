package com.regain.user_service.repository;

import com.regain.user_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByRoleName(String roleName);

    Role save(Role role);
}
