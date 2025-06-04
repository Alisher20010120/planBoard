package uz.pdp.planboard.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.planboard.entity.RoleName;
import uz.pdp.planboard.entity.Roles;
import uz.pdp.planboard.entity.Users;
import uz.pdp.planboard.repo.RolesRepository;
import uz.pdp.planboard.repo.UsersRepository;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    public DataLoader(RolesRepository rolesRepository, PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }



    @Override
    public void run(String... args) throws Exception {
        if (rolesRepository.count() == 0) {
           Roles role = new Roles(1,RoleName.ROLE_ADMIN);
           Roles role2 = new Roles(2,RoleName.ROLE_USER);
           rolesRepository.save(role);
           rolesRepository.save(role2);
        }


        if (usersRepository.count() == 0) {
            Roles roleUser = rolesRepository.findByRoleName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            Users user1 = new Users();
            user1.setFirstName("Alisher");
            user1.setLastName("Nayimov");
            user1.setPhoneNumber("123");
            user1.setPassword(passwordEncoder.encode("123"));
            user1.setRoles(List.of(roleUser));

            Users user2 = new Users();
            user2.setFirstName("Behruz");
            user2.setLastName("Mahmudjonov");
            user2.setPhoneNumber("12");
            user2.setPassword(passwordEncoder.encode("12"));
            user2.setRoles(List.of(roleUser));

            Users user3 = new Users();
            user3.setFirstName("Qalandar");
            user3.setLastName("Qalandarov");
            user3.setPhoneNumber("21");
            user3.setPassword(passwordEncoder.encode("21"));
            user3.setRoles(List.of(roleUser));

            usersRepository.saveAll(List.of(user1, user2, user3));
        }
    }
}
