package com.dgraysh.market.controllers;

import com.dgraysh.market.dto.NewProductDto;
import com.dgraysh.market.dto.ProductDto;
import com.dgraysh.market.entities.Product;
import com.dgraysh.market.exceptions.ResourceNotFoundException;
import com.dgraysh.market.services.CategoryService;
import com.dgraysh.market.services.ProductService;
import com.dgraysh.market.utils.ProductFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping(produces = "application/json")
    public Page<ProductDto> getAllProducts(@RequestParam(defaultValue = "1", name = "p") Integer page,
                                           @RequestParam Map<String, String> params
    ) {
        if (page < 1) {
            page = 1;
        }
        ProductFilter productFilter = new ProductFilter(params);
        Page<Product> content = productService.findAll(productFilter.getSpec(), page - 1, 5);
        return new PageImpl<>(content.getContent().stream().map(ProductDto::new).collect(Collectors.toList()), content.getPageable(), content.getTotalElements());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find product with id: " + id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Product createProduct(@RequestBody NewProductDto p) {
        Product product = new Product(null, p.getTitle(), p.getPrice(), categoryService.findById(p.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Unable to create new product, category with id: '%s' not found", p.getCategoryId()))));
        return productService.saveOrUpdate(product);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public Product updateProduct(@RequestBody Product p) {
        return productService.saveOrUpdate(p);
    }

    @DeleteMapping
    public void deleteAll() {
        productService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
