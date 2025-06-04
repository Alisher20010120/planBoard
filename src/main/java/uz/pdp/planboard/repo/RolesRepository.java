package uz.pdp.planboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.planboard.entity.RoleName;
import uz.pdp.planboard.entity.Roles;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByRoleName(RoleName roleUser);
}