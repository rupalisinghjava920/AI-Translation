package com.ai.translation.repository;

import com.ai.translation.entity.Translation;
import com.ai.translation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationRepository extends JpaRepository<Translation,Long> {
    List<Translation> findByUser(User user);
}
