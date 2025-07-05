package com.rams.catatanpenduduk.ui.kecamatan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rams.catatanpenduduk.data.model.DataKecamatan
import com.rams.catatanpenduduk.data.repository.CatatanPendudukRepository
import com.rams.catatanpenduduk.data.request.KecamatanRequest
import com.rams.catatanpenduduk.helper.Result
import kotlinx.coroutines.launch

class KecamatanViewModel(private val catatanPendudukRepository: CatatanPendudukRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: MutableLiveData<String> = _errorMessage

    private val _kecamatanResult = MutableLiveData<List<DataKecamatan>>()
    val kecamatanResult: MutableLiveData<List<DataKecamatan>> = _kecamatanResult

    private val _operationMessage = MutableLiveData<String?>()
    val operationMessage: MutableLiveData<String?> = _operationMessage

    private val _isLoadingOperation = MutableLiveData<Boolean>()
    val isLoadingOperation: MutableLiveData<Boolean> = _isLoadingOperation

    private val _selectedKecamatan = MutableLiveData<DataKecamatan>()
    val selectedKecamatan: MutableLiveData<DataKecamatan> = _selectedKecamatan

    fun clearOperationMessage() {
        _operationMessage.value = null
    }

    fun getKecamatan() {
        viewModelScope.launch {
            _isLoading.value = true
            when(val result = catatanPendudukRepository.getKecamatan()){
                is Result.Loading -> {
                }
                is Result.Success -> {
                    _kecamatanResult.value = result.data.data
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    _isLoading.value = false
                }
            }
        }
    }

    fun addKecamatan(request: KecamatanRequest) {
        viewModelScope.launch {
            _isLoadingOperation.value = true
            when (val result = catatanPendudukRepository.addKecamatan(request)) {
                is Result.Success -> {
                    _operationMessage.value = result.data.message
                    _isLoadingOperation.value = false
                    getKecamatan() // refresh
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    _isLoadingOperation.value = false
                }
                else -> Unit
            }
        }
    }

    fun updateKecamatan(id: Int, request: KecamatanRequest) {
        viewModelScope.launch {
            _isLoadingOperation.value = true
            when (val result = catatanPendudukRepository.updateKecamatan(id, request)) {
                is Result.Success -> {
                    _operationMessage.value = result.data.message
                    _isLoadingOperation.value = false
                    getKecamatan() // refresh
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                    _isLoadingOperation.value = false
                }
                else -> Unit
            }
        }
    }

    fun deleteKecamatan(id: Int) {
        viewModelScope.launch {
            when (val result = catatanPendudukRepository.deleteKecamatan(id)) {
                is Result.Success -> {
                    _operationMessage.value = result.data.message
                    getKecamatan() // refresh
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                }
                else -> Unit
            }
        }
    }

}