package simple.testcode.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import simple.testcode.common.controller.ApiResponse;
import simple.testcode.order.dto.OrderRequest;
import simple.testcode.order.dto.OrderResponse;
import simple.testcode.order.service.OrderService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {

        LocalDateTime registeredDateTime = LocalDateTime.now();

        return ApiResponse.ok(orderService.createOrder(request.toServiceVo(), registeredDateTime));
    }
}
