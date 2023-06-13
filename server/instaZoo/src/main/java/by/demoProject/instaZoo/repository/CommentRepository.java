package by.demoProject.instaZoo.repository;

import by.demoProject.instaZoo.entity.Comment;
import by.demoProject.instaZoo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);
}
