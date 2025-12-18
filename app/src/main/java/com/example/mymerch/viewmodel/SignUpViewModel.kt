package com.example.mymerch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymerch.repository.UserRepo
import com.example.mymerch.repository.UserRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepo: UserRepo = UserRepoImpl()) : ViewModel() {

    private val _signUpStatus = MutableStateFlow<Result<Unit>?>(null)
    val signUpStatus: StateFlow<Result<Unit>?> = _signUpStatus

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _signUpStatus.value = userRepo.signUp(email, password)
        }
    }
}