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

                // ============================
                // AUTHORIZATION
                // ============================
                .authorizeHttpRequests(auth -> auth

                        // Public
                        .requestMatchers(
                                "/",
                                "/login",
                                "/error",
                                "/css/**",
                                "/images/**"
                        ).permitAll()

                        // ADMIN
                        .requestMatchers(
                                "/dashboard",
                                "/users/**"
                        ).hasRole("ADMIN")

                        // LOGIN REQUIRED
                        .requestMatchers(
                                "/categories/**",
                                "/products/**"
                        ).authenticated()

                        // Everything else
                        .anyRequest().authenticated()
                )

                // ============================
                // FORM LOGIN
                // ============================
                .formLogin(form -> form

                        .loginPage("/login")

                        .loginProcessingUrl("/login")

                        .usernameParameter("email")

                        .passwordParameter("password")

                        .defaultSuccessUrl(
                                "/dashboard",
                                true
                        )

                        .failureUrl(
                                "/login?error=true"
                        )

                        .permitAll()
                )

                // ============================
                // LOGOUT
                // ============================
                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl(
                                "/login?logout=true"
                        )

                        .invalidateHttpSession(true)

                        .deleteCookies("JSESSIONID")

                        .permitAll()
                )

                // ============================
                // ACCESS DENIED
                // ============================
                .exceptionHandling(exception ->
                        exception.accessDeniedPage(
                                "/access-denied"
                        )
                );

        return http.build();
    }
}
