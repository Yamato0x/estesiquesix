package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> crearUsuario(@RequestBody User user,
            @RequestParam(required = false) String codigoAdmin) {
        User nuevoUsuario = userService.crearUsuario(user, codigoAdmin);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @PutMapping("/update")
    public ResponseEntity<User> modificarUsuario(@RequestBody User user) {
        User usuarioActualizado = userService.modificarUsuario(user);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> consultarUsuario(@PathVariable Long id) {
        User usuario = userService.consultarUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest) {
        User user = userService.login(loginRequest.getNombreUsuario(), loginRequest.getContrasena());
        return ResponseEntity.ok(user);
    }
}
