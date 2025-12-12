package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    @JsonProperty("userid")
    private Long userid;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    @JsonProperty("nombre_completo")
    private String nombreCompleto;

    @Column(name = "rut", nullable = false, unique = true, length = 20)
    @JsonProperty("rut")
    private String rut;

    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    @JsonProperty("nombre_usuario")
    private String nombreUsuario;

    @Column(name = "contrasena", nullable = false, length = 255)
    @JsonProperty("contrasena")
    private String contrasena;

    @Column(name = "correo", nullable = false, unique = true, length = 100)
    @JsonProperty("correo")
    private String correo;

    @Column(name = "rol", nullable = false, length = 50)
    @JsonProperty("rol")
    private String rol;

    @Column(name = "puntos")
    @JsonProperty("puntos")
    private Integer puntos;

    @Column(name = "foto_perfil", columnDefinition = "TEXT")
    @JsonProperty("foto_perfil")
    private String fotoPerfil;

    public User() {
    }

    public User(String nombreCompleto, String rut, String nombreUsuario, String contrasena, String correo, String rol,
            Integer puntos) {
        this.nombreCompleto = nombreCompleto;
        this.rut = rut;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.rol = rol;
        this.puntos = puntos;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
