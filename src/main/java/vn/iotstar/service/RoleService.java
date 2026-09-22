package vn.iotstar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Role;
import vn.iotstar.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> findAll() {

        return roleRepository.findAll();
    }

    public Role findById(Long id) {

        return roleRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Không tìm thấy role."
                        )
                );
    }

    public Role findByName(String name) {

        return roleRepository
                .findByNameIgnoreCase(name)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Không tìm thấy role."
                        )
                );
    }

    public Role save(Role role) {

        return roleRepository.save(role);
    }
}
