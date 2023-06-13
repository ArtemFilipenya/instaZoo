package by.demoProject.instaZoo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Comment {
    private static final String USER_NAME = "user_name";
    private static final String USER_ID = "user_id";
    private static final String CREATED_DATE = "created_date";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    @Column(name = USER_NAME, nullable = false)
    private String userName;
    @Column(name = USER_ID, nullable = false)
    private Long userId;
    @Column(columnDefinition = "text", nullable = false)
    private String message;
    @Column(name = CREATED_DATE, updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreated() {
        this.createdDate = LocalDateTime.now();
    }
}
