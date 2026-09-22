package vn.iotstar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // PUBLIC
                        // =========================
                        .requestMatchers(
                                "/",
                                "/login",
                                "/error",
                                "/access-denied",
                                "/css/**",
                                "/images/**"
                        ).permitAll()

                        // =========================
                        // ADMIN
                        // =========================
                        .requestMatchers(
                                "/admin/**",
                                "/dashboard",
                                "/users/**"
                        ).hasRole("ADMIN")

                        // =========================
                        // USER
                        // =========================
                        .requestMatchers(
                                "/user/**"
                        ).hasRole("USER")

                        // =========================
                        // OTHER AUTHENTICATED
                        // =========================
                        .requestMatchers(
                                "/categories/**",
                                "/products/**"
                        ).authenticated()

                        .anyRequest().authenticated()
                )

                // =========================
                // LOGIN
                // =========================
                .formLogin(form -> form

                        .loginPage("/login")

                        .loginProcessingUrl("/login")

                        // Tên input trong HTML
                        .usernameParameter("identifier")

                        .passwordParameter("password")

                        // Sau login:
                        // USER -> /user
                        // ADMIN -> /admin
                        .successHandler((request, response, authentication) -> {

                            boolean isAdmin =
                                    authentication.getAuthorities()
                                            .stream()
                                            .anyMatch(
                                                    a -> a.getAuthority()
                                                            .equals("ROLE_ADMIN")
                                            );

                            if (isAdmin) {
                                response.sendRedirect("/admin");
                            } else {
                                response.sendRedirect("/user");
                            }
                        })

                        .failureUrl("/login?error=true")

                        .permitAll()
                )

                // =========================
                // LOGOUT
                // =========================
                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl(
                                "/login?logout=true"
                        )

                        .invalidateHttpSession(true)

                        .deleteCookies("JSESSIONID")

                        .permitAll()
                )

                // =========================
                // ACCESS DENIED
                // =========================
                .exceptionHandling(exception ->
                        exception.accessDeniedPage(
                                "/access-denied"
                        )
                );

        return http.build();
    }
}
