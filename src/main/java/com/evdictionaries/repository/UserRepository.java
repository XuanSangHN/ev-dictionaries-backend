package com.evdictionaries.repository;

import com.evdictionaries.models.Class;
import com.evdictionaries.models.Role;
import com.evdictionaries.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean existsByPhonenumber(String phone);

  @Query("SELECT c FROM User c WHERE c.email = :email")
  User findByEmail(@Param("email")String email);

  @Query(value ="SELECT * FROM users, user_roles,roles WHERE users.id = user_roles.user_id AND roles.id = user_roles.role_id and (user_roles.role_id = 1 or user_roles.role_id =2)", nativeQuery = true)
  List<User> findAllByUserAdmin();

  User findByResetPasswordToken(String token);
}
