package vn.iotstar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.iotstar.entity.User;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUsernameIgnoreCase(String username);

    Page<User> findByEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(
            String email,
            String fullName,
            Pageable pageable
    );

    @Query("""
        SELECT u
        FROM User u
        JOIN FETCH u.role
        WHERE LOWER(u.email) = LOWER(:email)
    """)
    Optional<User> findByEmailWithRole(
            @Param("email") String email
    );

    @Query("""
        SELECT u
        FROM User u
        JOIN FETCH u.role
        WHERE LOWER(u.username) = LOWER(:identifier)
           OR LOWER(u.email) = LOWER(:identifier)
    """)
    Optional<User> findByUsernameOrEmailWithRole(
            @Param("identifier") String identifier
    );
}
