package vn.iotstar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Category;
import vn.iotstar.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {

        return categoryRepository.findAll();
    }

    public Category findById(Long id) {

        return categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Không tìm thấy category."
                        )
                );
    }

    public Category save(Category category) {

        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {

        if (!categoryRepository.existsById(id)) {

            throw new IllegalArgumentException(
                    "Không tìm thấy category."
            );
        }

        categoryRepository.deleteById(id);
    }
}
