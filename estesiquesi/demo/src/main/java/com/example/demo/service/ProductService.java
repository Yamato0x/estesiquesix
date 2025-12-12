package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product crearProducto(User usuario, Product producto) {
        if (!"ADMIN".equals(usuario.getRol())) {
            throw new IllegalArgumentException("No tienes permisos para crear productos");
        }
        return productRepository.save(producto);
    }

    public void eliminarProducto(User usuario, Long id) {
        if (!"ADMIN".equals(usuario.getRol())) {
            throw new IllegalArgumentException("No tienes permisos para eliminar productos");
        }
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }

    public Product actualizarProducto(User usuario, Product producto) {
        if (!"ADMIN".equals(usuario.getRol())) {
            throw new IllegalArgumentException("No tienes permisos para actualizar productos");
        }
        if (producto.getIdproducto() == null || !productRepository.existsById(producto.getIdproducto())) {
            throw new RuntimeException("Producto no encontrado");
        }
        return productRepository.save(producto);
    }

    public List<Product> listarProductos() {
        return productRepository.findAll();
    }

    public List<Product> buscarPorNombre(String nombre) {
        return productRepository.findByNombreproductoContainingIgnoreCase(nombre);
    }

    @Autowired
    private com.example.demo.repository.UserRepository userRepository;

    public User canjearProducto(User usuario, Long productoId) {
        Product producto = productRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Integer puntosRequeridos = producto.getPrecio_puntos();
        if (puntosRequeridos == null || puntosRequeridos <= 0) {
            throw new RuntimeException("Este producto no se puede canjear con puntos");
        }

        Integer puntosUsuario = usuario.getPuntos();
        if (puntosUsuario == null) {
            puntosUsuario = 0;
        }

        if (puntosUsuario < puntosRequeridos) {
            throw new RuntimeException("No tienes suficientes puntos para canjear este producto");
        }

        usuario.setPuntos(puntosUsuario - puntosRequeridos);
        return userRepository.save(usuario);
    }
}
