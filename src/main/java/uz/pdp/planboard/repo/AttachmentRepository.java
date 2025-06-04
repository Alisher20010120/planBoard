package uz.pdp.planboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.planboard.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}