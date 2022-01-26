package com.evdictionaries.repository;

import com.evdictionaries.models.Class;
import com.evdictionaries.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class,Long> {
    @Query("SELECT u FROM Class u WHERE u.user = :user")
    List<Class> findAllByUserId(@Param("user") User user);
}
