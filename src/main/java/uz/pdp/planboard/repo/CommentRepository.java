package uz.pdp.planboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.planboard.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}