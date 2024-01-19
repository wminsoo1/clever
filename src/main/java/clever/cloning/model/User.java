package clever.cloning.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true) // 유일한 값이어야 하는 필드
    private String password;

    private String email;

    private String nickName;

    private String token; // 토큰 필드 추가

    @OneToMany
    private List<Lecture> userLecture;

    public User() {
    }

    public User(Long id, String username, String password, String email, String nickName, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
