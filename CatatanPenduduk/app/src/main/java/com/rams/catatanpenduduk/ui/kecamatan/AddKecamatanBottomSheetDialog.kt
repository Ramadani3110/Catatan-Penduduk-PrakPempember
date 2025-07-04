package com.rams.catatanpenduduk.ui.kecamatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rams.catatanpenduduk.databinding.BottomSheetAddKecamatanBinding

class AddKecamatanBottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: BottomSheetAddKecamatanBinding? = null
    private val binding get() = _binding!!

    companion object{
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
    }

    private fun setupListener(){
        binding.btnAddKecamatan.setOnClickListener {
            val namaKecamatan = binding.edNamaKecamatan.text.toString()
            Toast.makeText(requireContext(), "$namaKecamatan disimpan", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}