package com.example.frontendandroid.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontendandroid.data.model.User
import com.example.frontendandroid.data.network.RetrofitClient
import kotlinx.coroutines.launch

class AdminUsersViewModel : ViewModel() {

    private val _users = mutableStateOf<List<User>>(emptyList())
    val users: State<List<User>> = _users

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _snackbarMessage = mutableStateOf<String?>(null)
    val snackbarMessage: State<String?> = _snackbarMessage

    // Dialog state
    private val _showEditDialog = mutableStateOf(false)
    val showEditDialog: State<Boolean> = _showEditDialog

    private val _userToEdit = mutableStateOf<User?>(null)
    val userToEdit: State<User?> = _userToEdit

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _users.value = RetrofitClient.instance.getAllUsers()
            } catch (e: Exception) {
                _snackbarMessage.value = "Error al cargar usuarios: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun startEditUser(user: User) {
        _userToEdit.value = user
        _showEditDialog.value = true
    }

    fun cancelEdit() {
        _showEditDialog.value = false
        _userToEdit.value = null
    }

    fun saveUserChanges(updatedUser: User) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                RetrofitClient.instance.updateUser(updatedUser)
                _snackbarMessage.value = "Usuario actualizado correctamente"
                fetchUsers() // Refresh list
                cancelEdit()
            } catch (e: Exception) {
                _snackbarMessage.value = "Error al actualizar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }
}
