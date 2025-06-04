package uz.pdp.planboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.planboard.entity.baseEntity.BaseEntity;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentContent extends BaseEntity {
    @ManyToOne
    private Attachment attachment;
    private byte[] file;
}
