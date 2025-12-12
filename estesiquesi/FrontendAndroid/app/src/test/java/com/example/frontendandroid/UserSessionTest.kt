package com.example.frontendandroid

import com.example.frontendandroid.data.UserSession
import com.example.frontendandroid.data.model.User
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserSessionTest {

    @Before
    fun setUp() {
        UserSession.clearUser()
    }

    @Test
    fun testSetAndGetUser() {
        val user = User(
            userid = 1,
            nombreCompleto = "Test User",
            rut = "12345678-9",
            nombreUsuario = "testuser",
            correo = "test@example.com",
            contrasena = "password",
            rol = "user",
            puntos = 100,
            foto_perfil = null
        )
        UserSession.setUser(user)
        assertEquals(user, UserSession.getUser())
        assertTrue(UserSession.isLoggedIn())
    }

    @Test
    fun testClearUser() {
        val user = User(
            userid = 1, 
            nombreCompleto = "Test", 
            rut = "1-9",
            rol = "user", 
            correo ="a@a.com", 
            contrasena = "123", 
            nombreUsuario = "test", 
            puntos = 0, 
            foto_perfil = null
        )
        UserSession.setUser(user)
        assertTrue(UserSession.isLoggedIn())
        
        UserSession.clearUser()
        assertNull(UserSession.getUser())
        assertFalse(UserSession.isLoggedIn())
    }

    @Test
    fun testIsAdmin() {
        val adminUser = User(
            userid = 1, 
            nombreCompleto = "Admin", 
            rut = "1-9",
            rol = "ADMIN", 
            correo ="a@a.com", 
            contrasena = "123", 
            nombreUsuario = "admin", 
            puntos = 0, 
            foto_perfil = null
        )
        UserSession.setUser(adminUser)
        assertTrue(UserSession.isAdmin())

        val normalUser = User(
            userid = 2, 
            nombreCompleto = "User", 
            rut = "2-9",
            rol = "user", 
            correo ="b@b.com", 
            contrasena = "123", 
            nombreUsuario = "user", 
            puntos = 0, 
            foto_perfil = null
        )
        UserSession.setUser(normalUser)
        assertFalse(UserSession.isAdmin())
    }
}
