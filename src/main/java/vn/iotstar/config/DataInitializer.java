package vn.iotstar.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${ADMIN_EMAIL:admin@gmail.com}")
            String adminEmail,
            @Value("${ADMIN_PASSWORD:123456}")
            String adminPassword
    ) {

        return args -> {

            // ============================
            // ROLE USER
            // ============================

            Role userRole =
                    roleRepository
                            .findByNameIgnoreCase("USER")
                            .orElseGet(() ->
                                    roleRepository.save(
                                            new Role("USER")
                                    )
                            );

            // ============================
            // ROLE ADMIN
            // ============================

            Role adminRole =
                    roleRepository
                            .findByNameIgnoreCase("ADMIN")
                            .orElseGet(() ->
                                    roleRepository.save(
                                            new Role("ADMIN")
                                    )
                            );

            // ============================
            // ADMIN
            // ============================

            if (!userRepository
                    .existsByEmailIgnoreCase(adminEmail)) {

                User admin = new User();

                admin.setUsername("admin");

                admin.setEmail(
                        adminEmail.trim().toLowerCase()
                );

                admin.setPassword(
                        passwordEncoder.encode(
                                adminPassword
                        )
                );

                admin.setFullName(
                        "System Administrator"
                );

                admin.setEnabled(true);

                admin.setRole(adminRole);

                userRepository.save(admin);
            }

            // ============================
            // USER
            // ============================

            String userEmail = "user@gmail.com";
            String userPassword = "123456";

            if (!userRepository
                    .existsByEmailIgnoreCase(userEmail)) {

                User user = new User();

                user.setUsername("user");

                user.setEmail(userEmail);

                user.setPassword(
                        passwordEncoder.encode(
                                userPassword
                        )
                );

                user.setFullName("Normal User");

                user.setEnabled(true);

                user.setRole(userRole);

                userRepository.save(user);
            }
        };
    }
}
