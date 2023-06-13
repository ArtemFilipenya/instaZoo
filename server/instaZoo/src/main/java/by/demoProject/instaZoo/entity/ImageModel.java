package by.demoProject.instaZoo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ImageModel {
    private static final String USER_ID = "user_id";
    private static final String POST_ID = "post_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageBytes;
    @Column(name = USER_ID)
    @JsonIgnore
    private Long userId;
    @Column(name = POST_ID)
    @JsonIgnore
    private Long postId;
}
