package com.evdictionaries.repository;

import com.evdictionaries.models.ERole;
import com.evdictionaries.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByCode(ERole name);
}
