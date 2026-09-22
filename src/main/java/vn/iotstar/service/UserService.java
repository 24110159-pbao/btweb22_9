package vn.iotstar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.entity.User;
import vn.iotstar.mapper.UserMapper;
import vn.iotstar.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserDTO> findAll(
            String keyword,
            Pageable pageable
    ) {

        Page<User> users;

        if (keyword == null || keyword.isBlank()) {

            users = userRepository.findAll(pageable);

        } else {

            users =
                    userRepository
                            .findByEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(
                                    keyword,
                                    keyword,
                                    pageable
                            );
        }

        return users.map(userMapper::toDto);
    }

    public UserDTO findById(Long id) {

        User user = findEntityById(id);

        return userMapper.toDto(user);
    }

    public User findEntityById(Long id) {

        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Không tìm thấy user."
                        )
                );
    }

    public User save(User user) {

        return userRepository.save(user);
    }

    public void deleteById(Long id) {

        if (!userRepository.existsById(id)) {

            throw new IllegalArgumentException(
                    "Không tìm thấy user."
            );
        }

        userRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {

        return userRepository
                .existsByEmailIgnoreCase(email);
    }
}
