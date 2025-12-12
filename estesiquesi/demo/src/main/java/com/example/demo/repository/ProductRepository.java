package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository ya incluye los métodos CRUD básicos:
    // save(), findById(), findAll(), deleteById(), etc.

    // Métodos de búsqueda personalizados sugeridos
    List<Product> findByCategoria(String categoria);

    // Búsqueda por nombre (case insensitive)
    List<Product> findByNombreproductoContainingIgnoreCase(String nombre);
}
