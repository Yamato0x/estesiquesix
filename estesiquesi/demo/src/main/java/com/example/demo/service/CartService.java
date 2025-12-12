package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public void agregarProducto(User usuario, Product producto, Integer cantidad) {
        Cart cart = cartRepository.findByUsuarioAndProducto(usuario, producto)
                .orElse(new Cart(usuario, producto, 0));

        cart.setCantidad(cart.getCantidad() + cantidad);
        cartRepository.save(cart);
    }

    public void desagregarProducto(User usuario, Product producto) {
        Cart cart = cartRepository.findByUsuarioAndProducto(usuario, producto)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        if (cart.getCantidad() > 1) {
            cart.setCantidad(cart.getCantidad() - 1);
            cartRepository.save(cart);
        } else {
            cartRepository.delete(cart);
        }
    }

    @Transactional
    public void eliminarCarrito(User usuario) {
        cartRepository.deleteByUsuario(usuario);
    }

    public BigDecimal totalPrecio(User usuario) {
        List<Cart> items = cartRepository.findByUsuario(usuario);
        BigDecimal total = BigDecimal.ZERO;

        for (Cart item : items) {
            BigDecimal precio = item.getProducto().getPrecioproducto();
            BigDecimal cantidad = new BigDecimal(item.getCantidad());
            total = total.add(precio.multiply(cantidad));
        }

        // Aplicar descuento del 20% si el correo termina en @duoc.cl
        if (usuario.getCorreo() != null && usuario.getCorreo().endsWith("@duoc.cl")) {
            BigDecimal descuento = total.multiply(new BigDecimal("0.20"));
            total = total.subtract(descuento);
        }

        return total;
    }

    public List<Cart> getCartItems(User usuario) {
        return cartRepository.findByUsuario(usuario);
    }
}
