package uz.pdp.planboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.planboard.entity.Columns;

public interface ColumnRepository extends JpaRepository<Columns, Integer> {
}