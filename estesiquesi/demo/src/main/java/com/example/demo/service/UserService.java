package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User crearUsuario(User user, String codigoAdmin) {
        if ("ABC123".equals(codigoAdmin)) {
            user.setRol("ADMIN");
        }
        return userRepository.save(user);
    }

    public User modificarUsuario(User user) {
        if (user.getUserid() == null || !userRepository.existsById(user.getUserid())) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return userRepository.save(user);
    }

    public User consultarUsuario(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public User login(String username, String password) {
        User user = userRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!user.getContrasena().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return user;
    }
}
