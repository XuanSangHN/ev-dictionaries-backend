package com.evdictionaries.repository;

import com.evdictionaries.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic,Long> {

}
