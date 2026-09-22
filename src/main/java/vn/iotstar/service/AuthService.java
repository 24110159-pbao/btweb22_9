package vn.iotstar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {

        return userRepository
                .findByEmailWithRole(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Không tìm thấy tài khoản."
                        )
                );
    }

    public boolean existsByEmail(String email) {

        return userRepository
                .existsByEmailIgnoreCase(email);
    }
}
