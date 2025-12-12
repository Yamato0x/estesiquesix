package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproducto")
    @JsonProperty("idproducto")
    private Long idproducto;

    @Column(name = "nombreproducto", nullable = false, length = 100)
    @JsonProperty("nombreproducto")
    private String nombreproducto;

    @Column(name = "precioproducto", nullable = false, precision = 10, scale = 2)
    @JsonProperty("precioproducto")
    private BigDecimal precioproducto;

    @Column(name = "categoria", length = 50)
    @JsonProperty("categoria")
    private String categoria;

    @Column(name = "imagen_url", length = 255)
    @JsonProperty("imagen_url")
    private String imagen_url;

    @Column(name = "precio_puntos")
    @JsonProperty("precio_puntos")
    private Integer precio_puntos;

    public Product() {
    }

    public Product(String nombreproducto, BigDecimal precioproducto, String categoria, String imagen_url,
            Integer precio_puntos) {
        this.nombreproducto = nombreproducto;
        this.precioproducto = precioproducto;
        this.categoria = categoria;
        this.imagen_url = imagen_url;
        this.precio_puntos = precio_puntos;
    }

    public Long getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Long idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public BigDecimal getPrecioproducto() {
        return precioproducto;
    }

    public void setPrecioproducto(BigDecimal precioproducto) {
        this.precioproducto = precioproducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen_url() {
        return imagen_url;
    }

    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }

    public Integer getPrecio_puntos() {
        return precio_puntos;
    }

    public void setPrecio_puntos(Integer precio_puntos) {
        this.precio_puntos = precio_puntos;
    }
}
