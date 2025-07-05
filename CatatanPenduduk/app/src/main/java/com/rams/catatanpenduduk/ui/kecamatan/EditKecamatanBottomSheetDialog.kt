package com.rams.catatanpenduduk.ui.kecamatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rams.catatanpenduduk.data.request.KecamatanRequest
import com.rams.catatanpenduduk.databinding.BottomSheetEditKecamatanBinding

class EditKecamatanBottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: BottomSheetEditKecamatanBinding? = null
    private val binding get() = _binding!!
    lateinit var kecamatanViewModel: KecamatanViewModel

    companion object {
        private const val ARG_ID = "arg_id"
        private const val ARG_NAMA = "arg_nama"
        fun newInstance(id: Int, nama: String): EditKecamatanBottomSheetDialog {
            val fragment = EditKecamatanBottomSheetDialog()
            val args = Bundle()
            args.putInt(ARG_ID, id)
            args.putString(ARG_NAMA, nama)
            fragment.arguments = args
            return fragment
        }

        val TAG = EditKecamatanBottomSheetDialog::class.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetEditKecamatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nama = arguments?.getString(ARG_NAMA)
        binding.edUNamaKecamatan.setText(nama)
        setupListener()
        setupObservers()
    }

    private fun setupObservers() {
        kecamatanViewModel.isLoadingOperation.observe(viewLifecycleOwner) {
            binding.btnUbahKecamatan.showLoading(it)
        }

        kecamatanViewModel.operationMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrBlank()) {
                dismiss()
                kecamatanViewModel.clearOperationMessage()
            }
        }
    }

    private fun setupListener() {
        val id = arguments?.getInt(ARG_ID) ?: return
        binding.btnUbahKecamatan.setOnClickAction {
            val namaKecamatan = binding.edUNamaKecamatan.text.toString().trim()

            if (namaKecamatan.isEmpty()) {
                binding.edUNamaKecamatan.error = "Nama tidak boleh kosong"
                return@setOnClickAction
            }

            val request = KecamatanRequest(namaKecamatan)
            kecamatanViewModel.updateKecamatan(id, request)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}