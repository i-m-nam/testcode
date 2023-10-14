package simple.testcode.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import simple.testcode.common.dto.ApiResponse;
import simple.testcode.product.dto.ProductRequest;
import simple.testcode.product.dto.ProductResponse;
import simple.testcode.product.service.ProductService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping("/api/v1/products/new")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ApiResponse.ok(productService.createProduct(request.toServiceVo()));
    }
}
