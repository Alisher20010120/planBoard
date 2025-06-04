package uz.pdp.planboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.planboard.entity.AttachmentContent;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Integer> {
    @Override
    Optional<AttachmentContent> findById(Integer integer);

    Optional<AttachmentContent> findByAttachment_Id(Integer id);
}