package uz.pdp.planboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.planboard.entity.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
  Optional<Users> findByPhoneNumber(String phoneNumber);

}