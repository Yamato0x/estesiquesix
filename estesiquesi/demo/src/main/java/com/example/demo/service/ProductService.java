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
}
