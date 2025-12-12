package com.example.frontendandroid.data.network

import com.example.frontendandroid.data.model.LoginRequest
import com.example.frontendandroid.data.model.Product
import com.example.frontendandroid.data.model.User
import com.example.frontendandroid.data.model.Cart
import retrofit2.http.*
import java.math.BigDecimal

interface ApiService {

    // Cart Endpoints
    @POST("api/cart/add")
    suspend fun addToCart(
        @Query("userId") userId: Long,
        @Query("productId") productId: Long,
        @Query("quantity") quantity: Int
    ): okhttp3.ResponseBody

    @POST("api/cart/decrement")
    suspend fun decrementCart(
        @Query("userId") userId: Long,
        @Query("productId") productId: Long
    ): okhttp3.ResponseBody

    @DELETE("api/cart/clear/{userId}")
    suspend fun clearCart(@Path("userId") userId: Long): okhttp3.ResponseBody

    @GET("api/cart/total/{userId}")
    suspend fun getCartTotal(@Path("userId") userId: Long): BigDecimal

    @GET("api/cart/items/{userId}")
    suspend fun getCartItems(@Path("userId") userId: Long): List<Cart>

    @POST("api/cart/checkout/{userId}")
    suspend fun checkout(@Path("userId") userId: Long): Int

    // Product Endpoints
    @POST("api/products/create")
    suspend fun createProduct(
        @Body product: Product,
        @Query("userId") userId: Long
    ): Product

    @PUT("api/products/update")
    suspend fun updateProduct(
        @Body product: Product,
        @Query("userId") userId: Long
    ): Product

    @DELETE("api/products/delete/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Long,
        @Query("userId") userId: Long
    ): okhttp3.ResponseBody

    @GET("api/products")
    suspend fun getProducts(): List<Product>

    @GET("api/products/search")
    suspend fun searchProducts(@Query("nombre") nombre: String): List<Product>

    @POST("api/products/redeem")
    suspend fun redeemProduct(
        @Query("userId") userId: Long,
        @Query("productId") productId: Long
    ): User

    // User Endpoints
    @POST("api/users/create")
    suspend fun createUser(
        @Body user: User,
        @Query("codigoAdmin") codigoAdmin: String?
    ): User

    @PUT("api/users/update")
    suspend fun updateUser(@Body user: User): User

    @GET("api/users/{id}")
    suspend fun getUser(@Path("id") id: Long): User

    @POST("api/users/login")
    suspend fun login(@Body loginRequest: LoginRequest): User
}
