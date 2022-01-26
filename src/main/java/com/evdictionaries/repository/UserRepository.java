package com.evdictionaries.repository;

import com.evdictionaries.models.Class;
import com.evdictionaries.models.Role;
import com.evdictionaries.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
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

  User findByVerificationCode(String verification_code);

  @Query(value ="SELECT * FROM users, user_roles,roles WHERE users.id = user_roles.user_id AND roles.id = user_roles.role_id and (roles.code = :code)", nativeQuery = true)
  List<User> findByRoles(@Param("code")String code);

  @Modifying
  @Query(value ="UPDATE User users SET users.firstname = ?1, users.lastname= ?2, users.avatar =?3, users.address =?4,users.phonenumber =?5 WHERE users.id = ?6")
  void updateUser(String firstname,String lastname,String avatar,String address,String phonenumber,Long id);
}
