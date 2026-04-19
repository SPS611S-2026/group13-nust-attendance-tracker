package com.nust.attendance.config;

import com.nust.attendance.model.Role;
import com.nust.attendance.model.User;
import com.nust.attendance.repository.RoleRepository;
import com.nust.attendance.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.logging.Logger;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = Logger.getLogger(DataInitializer.class.getName());

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        for (String roleName : List.of("STUDENT", "LECTURER", "ADMIN")) {
            if (roleRepo.findByRoleName(roleName).isEmpty()) {
                roleRepo.save(Role.builder().roleName(roleName).build());
                log.info("Created role: " + roleName);
            }
        }

        if (!userRepo.existsByStudentNumber("10000001")) {
            Role adminRole = roleRepo.findByRoleName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

            User admin = User.builder()
                    .studentNumber("10000001")
                    .fullName("System Administrator")
                    .email("admin@nust.na")
                    .passwordHash(passwordEncoder.encode("password123"))
                    .role(adminRole)
                    .active(true)
                    .build();

            userRepo.save(admin);
            log.info("Default admin created — number: 10000001, password: password123");
        }
    }
}
