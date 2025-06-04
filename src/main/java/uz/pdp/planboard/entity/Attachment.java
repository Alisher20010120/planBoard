package uz.pdp.planboard.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.planboard.entity.baseEntity.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attachment extends BaseEntity {
  private  String fileName;
}
