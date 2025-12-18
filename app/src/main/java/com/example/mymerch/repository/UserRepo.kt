package com.example.mymerch.repository

interface UserRepo {
    suspend fun signUp(email: String, password: String): Result<Unit>
}