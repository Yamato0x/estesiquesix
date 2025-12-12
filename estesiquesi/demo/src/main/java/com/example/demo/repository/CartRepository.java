package com.example.demo.repository;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    List<Cart> findByUsuario(User usuario);
    
    Optional<Cart> findByUsuarioAndProducto(User usuario, Product producto);
    
    void deleteByUsuario(User usuario);
}
