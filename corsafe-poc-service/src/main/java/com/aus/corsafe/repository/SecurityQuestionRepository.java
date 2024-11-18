package com.aus.corsafe.repository;

import com.aus.corsafe.entity.SecurityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion,Integer> {
    SecurityQuestion findByQuestion(String question);
}
