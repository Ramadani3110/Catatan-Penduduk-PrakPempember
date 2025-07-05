package com.rams.catatanpenduduk.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rams.catatanpenduduk.data.model.LoginResponse
import com.rams.catatanpenduduk.data.repository.CatatanPendudukRepository
import kotlinx.coroutines.launch
import com.rams.catatanpenduduk.helper.Result

class LoginViewModel(private val catatanPendudukRepository: CatatanPendudukRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: MutableLiveData<String> = _errorMessage

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: MutableLiveData<LoginResponse> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            when(val result = catatanPendudukRepository.login(email, password)){
                is Result.Loading -> {
                }
                is Result.Success -> {
                    _loginResult.value = result.data
                    _isLoading.value = false
                }
                is Result.Error -> {
                    Log.e("LoginViewModel", result.message)
                    _errorMessage.value = result.message
                    _isLoading.value = false
                }
            }
        }
    }
}