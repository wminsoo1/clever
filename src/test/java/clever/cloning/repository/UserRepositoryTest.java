package clever.cloning.repository;

import clever.cloning.model.Lecture;
import clever.cloning.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LectureRepository lectureRepository;

    @Test
    void testSaveUserWithLectures() {
        // Given
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        user.setNickName("Test Nickname");
        user.setToken("testToken");

        // Create dummy lectures
        Lecture lecture1 = new Lecture("Lecture 1", "Description 1", "https://example.com/lecture1");
        Lecture lecture2 = new Lecture("Lecture 2", "Description 2", "https://example.com/lecture2");
        lectureRepository.save(lecture1);
        lectureRepository.save(lecture2);
        user.setUserLecture(List.of(lecture1, lecture2));

        // When
        userRepository.save(user);

    }


}