package cart.web.controller;

import cart.domain.product.service.AdminService;
import cart.domain.product.service.dto.ProductCreationDto;
import cart.domain.product.service.dto.ProductModificationDto;
import cart.web.controller.dto.request.ProductCreationRequest;
import cart.web.controller.dto.request.ProductModificationRequest;
import cart.web.controller.dto.response.ProductCreationResponse;
import cart.web.controller.dto.response.ProductModificationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class AdminRestController {
    private final AdminService adminService;

    public AdminRestController(final AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<ProductCreationResponse> createProduct(@RequestBody final ProductCreationRequest request) {
        final ProductCreationDto productCreationDto = new ProductCreationDto(
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                request.getImageUrl()
        );

        final Long savedProductId = adminService.save(productCreationDto);

        final ProductCreationResponse productCreationResponse =
                new ProductCreationResponse(savedProductId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productCreationResponse);
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long deleteId) {
        adminService.delete(deleteId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping
    public ResponseEntity<ProductModificationResponse> updateProduct(
            @RequestBody final ProductModificationRequest request) {
        final ProductModificationDto productModificationDto = new ProductModificationDto(
                request.getId(),
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                request.getImageUrl()
        );

        adminService.update(productModificationDto);

        return ResponseEntity
                .ok(new ProductModificationResponse(request));
    }
}
