package com.evdictionaries.repository;

import com.evdictionaries.models.Class;
import com.evdictionaries.models.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectsRepository extends JpaRepository<Subjects,Long> {
    @Query("SELECT u FROM Subjects u WHERE u.classEntity = :classEntity")
    List<Subjects> findAllByClassId(@Param("classEntity") Class aClass);
}
