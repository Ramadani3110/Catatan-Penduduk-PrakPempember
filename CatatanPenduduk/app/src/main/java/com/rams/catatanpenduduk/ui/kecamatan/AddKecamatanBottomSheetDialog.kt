package com.rams.catatanpenduduk.ui.kecamatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rams.catatanpenduduk.data.request.KecamatanRequest
import com.rams.catatanpenduduk.databinding.BottomSheetAddKecamatanBinding

class AddKecamatanBottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: BottomSheetAddKecamatanBinding? = null
    private val binding get() = _binding!!

    lateinit var kecamatanViewModel: KecamatanViewModel

    companion object {
        fun newInstance() = AddKecamatanBottomSheetDialog()
        val TAG = AddKecamatanBottomSheetDialog::class.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetAddKecamatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupObservers()
    }

    private fun setupObservers() {
        kecamatanViewModel.isLoadingOperation.observe(viewLifecycleOwner) {
            binding.btnAddKecamatan.showLoading(it)
        }
        kecamatanViewModel.operationMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrBlank()) {
                dismiss()
                kecamatanViewModel.clearOperationMessage()
            }
        }
    }

    private fun setupListener() {
        binding.btnAddKecamatan.setOnClickAction {
            val namaKecamatan = binding.edNamaKecamatan.text.toString().trim()

            if (namaKecamatan.isEmpty()) {
                binding.edNamaKecamatan.error = "Nama kecamatan tidak boleh kosong"
                return@setOnClickAction
            }

            val request = KecamatanRequest(namaKecamatan)
            kecamatanViewModel.addKecamatan(request)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}