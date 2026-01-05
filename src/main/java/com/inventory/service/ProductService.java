package com.inventory.service;

import com.inventory.entity.Product;
import com.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getLowStockProducts(Integer threshold) {
        return productRepository.findByQuantityLessThan(threshold);
    }

    public Product createProduct(Product product) {
        // Check if SKU already exists
        if (product.getSku() != null && !product.getSku().isEmpty()) {
            Optional<Product> existingProduct = productRepository.findBySku(product.getSku());
            if (existingProduct.isPresent()) {
                throw new IllegalArgumentException("Product with SKU " + product.getSku() + " already exists");
            }
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // Check if SKU is being changed and if it conflicts with another product
        if (productDetails.getSku() != null && !productDetails.getSku().isEmpty()) {
            Optional<Product> existingProduct = productRepository.findBySku(productDetails.getSku());
            if (existingProduct.isPresent() && !existingProduct.get().getId().equals(id)) {
                throw new IllegalArgumentException("Product with SKU " + productDetails.getSku() + " already exists");
            }
        }

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setSku(productDetails.getSku());
        product.setCategory(productDetails.getCategory());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public Product updateProductQuantity(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        product.setQuantity(quantity);
        return productRepository.save(product);
    }
}

