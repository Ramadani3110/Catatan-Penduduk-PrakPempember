package com.rams.catatanpenduduk.ui.desa

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rams.catatanpenduduk.data.model.DataDesa
import com.rams.catatanpenduduk.data.repository.CatatanPendudukRepository
import com.rams.catatanpenduduk.data.request.DesaRequest
import kotlinx.coroutines.launch
import com.rams.catatanpenduduk.helper.Result

class DesaViewModel(private val catatanPendudukRepository: CatatanPendudukRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _isLoadingOperation = MutableLiveData<Boolean>()
    val isLoadingOperation: MutableLiveData<Boolean> = _isLoadingOperation

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: MutableLiveData<String> = _errorMessage

    private val _operationMessage = MutableLiveData<String?>()
    val operationMessage: MutableLiveData<String?> = _operationMessage

    private val _desaResult = MutableLiveData<List<DataDesa>>()
    val desaResult: MutableLiveData<List<DataDesa>> = _desaResult

    fun getDesa() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = catatanPendudukRepository.getDesa()) {
                is Result.Success -> {
                    _desaResult.value = result.data.data
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    _isLoading.value = false
                }
                else -> {}
            }
        }
    }

    fun addDesa(request: DesaRequest) {
        viewModelScope.launch {
            _isLoadingOperation.value = true
            when (val result = catatanPendudukRepository.addDesa(request)) {
                is Result.Success -> {
                    _operationMessage.value = "Desa berhasil ditambahkan"
                    _isLoadingOperation.value = false
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    _isLoadingOperation.value = false
                }
                else -> {}
            }
        }
    }

    fun updateDesa(id: Int, request: DesaRequest) {
        viewModelScope.launch {
            _isLoadingOperation.value = true
            when (val result = catatanPendudukRepository.updateDesa(id, request)) {
                is Result.Success -> {
                    _operationMessage.value = "Desa berhasil diubah"
                    _isLoadingOperation.value = false
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    _isLoadingOperation.value = false
                }
                else -> {}
            }
        }
    }

    fun deleteDesa(id: Int) {
        viewModelScope.launch {
            _isLoadingOperation.value = true
            when (val result = catatanPendudukRepository.deleteDesa(id)) {
                is Result.Success -> {
                    _operationMessage.value = "Desa berhasil dihapus"
                    _isLoadingOperation.value = false
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    _isLoadingOperation.value = false
                }
                else -> {}
            }
        }
    }

    fun clearOperationMessage() {
        _operationMessage.value = null
    }

}