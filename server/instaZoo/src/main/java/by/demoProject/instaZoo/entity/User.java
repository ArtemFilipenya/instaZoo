package by.demoProject.instaZoo.entity;

import by.demoProject.instaZoo.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity(name = "users")
public class User implements UserDetails {
    private static final String USER_NAME = "user_name";
    private static final String LAST_NAME = "last_name";
    private static final String USER_ROLE = "user_role";
    private static final String CREATED_DATE = "created_date";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = USER_NAME, updatable = false)
    private String userName;
    @Column(name = LAST_NAME, nullable = false)
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "text")
    private String biography;
    @Column(length = 3000)
    private String password;
    @Column(name = CREATED_DATE, updatable = false)
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @Transient
    private Collection<? extends GrantedAuthority> authorities;
    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = USER_ROLE, joinColumns = @JoinColumn(name = "user_id"))
    private Set<ERole> role = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public User(Long id, String userName, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public User() {

    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
