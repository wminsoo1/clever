package clever.cloning;

import clever.cloning.model.Lecture;
import clever.cloning.repository.LectureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CloningApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloningApplication.class, args);
	}

	/*
	@Bean
	public CommandLineRunner demo(LectureRepository lectureRepository) {
		return (args) -> {
			// 더미 데이터 추가
			addDummyData(lectureRepository);
		};
	}

	private void addDummyData(LectureRepository lectureRepository) {
		Lecture lecture1 = new Lecture("Introduction to Spring Boot", "Learn the basics of Spring Boot", "https://example.com/lecture1");
		Lecture lecture2 = new Lecture("Building RESTful APIs with Spring", "Create RESTful APIs using Spring", "https://example.com/lecture2");
		Lecture lecture3 = new Lecture("Database Access with Spring Data JPA", "Interact with databases using Spring Data JPA", "https://example.com/lecture3");

		// 더미 데이터를 레포지토리에 저장
		lectureRepository.saveAll(List.of(lecture1, lecture2, lecture3));
	}
	*/
}
