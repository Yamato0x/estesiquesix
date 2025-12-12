package com.example.myapplication.data

import com.example.myapplication.data.model.User

object UserSession {
    private var currentUser: User? = null

    fun setUser(user: User) {
        currentUser = user
    }

    fun getUser(): User? = currentUser

    fun clearUser() {
        currentUser = null
    }

    fun isAdmin(): Boolean {
        return currentUser?.rol?.equals("admin", ignoreCase = true) == true
    }

    fun isLoggedIn(): Boolean = currentUser != null
}
