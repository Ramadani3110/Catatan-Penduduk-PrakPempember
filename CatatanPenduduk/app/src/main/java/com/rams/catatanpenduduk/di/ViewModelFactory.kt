package com.rams.catatanpenduduk.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rams.catatanpenduduk.data.repository.CatatanPendudukRepository
import com.rams.catatanpenduduk.ui.auth.LoginViewModel
import com.rams.catatanpenduduk.ui.desa.DesaViewModel
import com.rams.catatanpenduduk.ui.kecamatan.KecamatanViewModel
import com.rams.catatanpenduduk.ui.penduduk.PendudukViewModel

class ViewModelFactory(private val catatanPendudukRepository: CatatanPendudukRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST", "CAST_NEVER_SUCCEEDS")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(catatanPendudukRepository) as T
            }
            modelClass.isAssignableFrom(DesaViewModel::class.java) -> {
                DesaViewModel(catatanPendudukRepository) as T
            }
            modelClass.isAssignableFrom(KecamatanViewModel::class.java) -> {
                KecamatanViewModel(catatanPendudukRepository) as T
            }
            modelClass.isAssignableFrom(PendudukViewModel::class.java) -> {
                PendudukViewModel(catatanPendudukRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Inject.provideRepository(context))
            }.also { instance = it }
    }

}