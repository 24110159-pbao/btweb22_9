package vn.iotstar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Product;
import vn.iotstar.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {

        return productRepository.findAll();
    }

    public Product findById(Long id) {

        return productRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Không tìm thấy product."
                        )
                );
    }

    public Product save(Product product) {

        return productRepository.save(product);
    }

    public void deleteById(Long id) {

        if (!productRepository.existsById(id)) {

            throw new IllegalArgumentException(
                    "Không tìm thấy product."
            );
        }

        productRepository.deleteById(id);
    }
}
