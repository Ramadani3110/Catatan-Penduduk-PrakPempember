package com.rams.catatanpenduduk.ui.penduduk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rams.catatanpenduduk.data.model.DataPenduduk
import com.rams.catatanpenduduk.data.repository.CatatanPendudukRepository
import com.rams.catatanpenduduk.data.request.PendudukRequest
import kotlinx.coroutines.launch
import com.rams.catatanpenduduk.helper.Result

class PendudukViewModel(private val catatanPendudukRepository: CatatanPendudukRepository) : ViewModel() {

    private val _pendudukResult = MutableLiveData<List<DataPenduduk>>()
    val pendudukResult: MutableLiveData<List<DataPenduduk>> = _pendudukResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _isLoadingOperation = MutableLiveData<Boolean>()
    val isLoadingOperation: MutableLiveData<Boolean> = _isLoadingOperation

    private val _operationMessage = MutableLiveData<String?>()
    val operationMessage: MutableLiveData<String?> = _operationMessage

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> = _errorMessage

    fun clearOperationMessage() {
        _operationMessage.value = null
    }

    fun getPenduduk() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = catatanPendudukRepository.getPenduduk()) {
                is Result.Success -> {
                    _pendudukResult.value = result.data.data
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

    fun addPenduduk(request: PendudukRequest) {
        viewModelScope.launch {
            _isLoadingOperation.value = true
            when (val result = catatanPendudukRepository.addPenduduk(request)) {
                is Result.Success -> {
                    _operationMessage.value = "Penduduk berhasil ditambahkan"
                    _isLoadingOperation.value = false
                    getPenduduk()
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    _isLoadingOperation.value = false
                }
                else -> {}
            }
        }
    }

    fun updatePenduduk(id: Int, request: PendudukRequest) {
        viewModelScope.launch {
            _isLoadingOperation.value = true
            when (val result = catatanPendudukRepository.updatePenduduk(id, request)) {
                is Result.Success -> {
                    _operationMessage.value = "Penduduk berhasil diubah"
                    _isLoadingOperation.value = false
                    getPenduduk()
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    _isLoadingOperation.value = false
                }
                else -> {}
            }
        }
    }

    fun deletePenduduk(id: Int) {
        viewModelScope.launch {
            _isLoadingOperation.value = true
            when (val result = catatanPendudukRepository.deletePenduduk(id)) {
                is Result.Success -> {
                    _operationMessage.value = "Penduduk berhasil dihapus"
                    _isLoadingOperation.value = false
                    getPenduduk()
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    _isLoadingOperation.value = false
                }
                else -> {}
            }
        }
    }

}