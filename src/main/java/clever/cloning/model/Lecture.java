package clever.cloning.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    private String lectureName;
    private String description;
    private String url;
    
    public Lecture() {
    }

    public Lecture(String lectureName, String description, String url) {
        this.lectureName = lectureName;
        this.description = description;
        this.url = url;
    }
}
