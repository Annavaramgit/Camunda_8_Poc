package com.aus.corsafe;

import com.aus.corsafe.entity.SecurityQuestion;
import com.aus.corsafe.repository.SecurityQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication

public class CoRSafePocApplication implements CommandLineRunner {

	private SecurityQuestionRepository securityQuestionRepository;

	public CoRSafePocApplication(SecurityQuestionRepository securityQuestionRepository){
		this.securityQuestionRepository=securityQuestionRepository;
	}
	public static void main(String[] args) {

		SpringApplication.run(CoRSafePocApplication.class, args);
	}

	private final List<String> securityQuestions = List.of(
			"What was the name of your favorite childhood pet?",
			"What is your favorite sport?",
			"What is the meaning of life?",
			"What does it mean to be happy?",
			"Is there such a thing as fate or free will?",
			"What is the nature of reality?",
			"How do you define truth?",
			"What legacy do you want to leave behind?",
			"What is the relationship between power and responsibility?",
			"How do you determine right from wrong?",
			"What is the importance of human connection?"
	);
	@Override
	public void run(String... args) throws Exception {

		try {
			for (String question : securityQuestions) {
				// Check if the question already exists
				if (securityQuestionRepository.findByQuestion(question) == null) {
					SecurityQuestion securityQuestion = new SecurityQuestion();
					securityQuestion.setQuestion(question);
					securityQuestionRepository.save(securityQuestion);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
