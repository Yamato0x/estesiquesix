package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public ResponseEntity<String> agregarProducto(@RequestParam Long userId, @RequestParam Long productId,
            @RequestParam Integer quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        cartService.agregarProducto(user, product, quantity);
        return ResponseEntity.ok("Producto agregado al carrito");
    }

    @PostMapping("/decrement")
    public ResponseEntity<String> desagregarProducto(@RequestParam Long userId, @RequestParam Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        cartService.desagregarProducto(user, product);
        return ResponseEntity.ok("Cantidad decrementada o producto eliminado");
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> eliminarCarrito(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        cartService.eliminarCarrito(user);
        return ResponseEntity.ok("Carrito vaciado");
    }

    @GetMapping("/total/{userId}")
    public ResponseEntity<BigDecimal> totalPrecio(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        BigDecimal total = cartService.totalPrecio(user);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/items/{userId}")
    public ResponseEntity<List<Cart>> getCartItems(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Cart> items = cartService.getCartItems(user);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<Integer> checkout(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Integer puntosGanados = cartService.checkout(user);
        return ResponseEntity.ok(puntosGanados);
    }
}
