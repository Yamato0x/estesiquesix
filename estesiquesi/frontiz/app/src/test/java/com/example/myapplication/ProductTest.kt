package com.example.myapplication

import com.example.myapplication.data.model.Product
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class ProductTest {

    @Test
    fun testProductCreation() {
        val product = Product(
            idproducto = 1,
            nombreproducto = "Test Product",
            precioproducto = BigDecimal("9990.0"),
            categoria = "Test Category",
            imagen_url = "http://image.com",
            precio_puntos = 100
        )

        assertEquals("Test Product", product.nombreproducto)
        assertEquals(BigDecimal("9990.0"), product.precioproducto)
        assertEquals(100, product.precio_puntos)
    }

    @Test
    fun testProductDefaults() {
        val product = Product(
            nombreproducto = "Default Product",
            precioproducto = BigDecimal("1000")
        )
        
        assertNull(product.idproducto)
        assertNull(product.categoria)
        assertNull(product.imagen_url)
        assertNull(product.precio_puntos)
    }
    
    @Test
    fun testProductCopy() {
         val product = Product(
            nombreproducto = "Original",
            precioproducto = BigDecimal("1000")
        )
        val copy = product.copy(nombreproducto = "Copy")
        
        assertEquals("Copy", copy.nombreproducto)
        assertEquals(product.precioproducto, copy.precioproducto)
    }
}
