package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<Product> crearProducto(@RequestBody Product product, @RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Product nuevoProducto = productService.crearProducto(user, product);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> actualizarProducto(@RequestBody Product product, @RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Product productoActualizado = productService.actualizarProducto(user, product);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id, @RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        productService.eliminarProducto(user, id);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }

    @GetMapping
    public ResponseEntity<List<Product>> listarProductos() {
        List<Product> productos = productService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> buscarPorNombre(@RequestParam String nombre) {
        List<Product> productos = productService.buscarPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }
}
