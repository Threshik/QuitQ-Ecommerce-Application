package com.hexaware.quitQ_backend_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.service.interf.CategoryService;
import com.hexaware.quitQ_backend_1.service.interf.OrderService;
import com.hexaware.quitQ_backend_1.service.interf.ProductService;
import com.hexaware.quitQ_backend_1.service.interf.UserService;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/userCount")
    public ResponseEntity<Response> getUserCount() {
        Response response = new Response();
        response.setStatus(200);
        response.setMessage("Total users fetched");
        response.setCount(userService.getUserCount().intValue());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/productCount")
    public ResponseEntity<Response> getProductCount() {
        Response response = new Response();
        response.setStatus(200);
        response.setMessage("Total products fetched");
        response.setCount(productService.getProductCount().intValue());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categoryCount")
    public ResponseEntity<Response> getCategoryCount() {
        Response response = new Response();
        response.setStatus(200);
        response.setMessage("Total categories fetched");
        response.setCount(categoryService.getCategoryCount().intValue());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orderCount")
    public ResponseEntity<Response> getOrderCount() {
        Response response = new Response();
        response.setStatus(200);
        response.setMessage("Total orders fetched");
        response.setCount(orderService.getOrderCount().intValue());
        return ResponseEntity.ok(response);
    }
}
