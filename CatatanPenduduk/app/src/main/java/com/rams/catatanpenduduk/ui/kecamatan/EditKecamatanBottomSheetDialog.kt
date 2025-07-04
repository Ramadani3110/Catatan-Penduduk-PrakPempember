package com.rams.catatanpenduduk.ui.kecamatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rams.catatanpenduduk.databinding.BottomSheetEditKecamatanBinding

class EditKecamatanBottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: BottomSheetEditKecamatanBinding? = null
    private val binding get() = _binding!!

    companion object{
        private const val ARG_NAMA = "arg_nama"
        fun newInstance(nama: String): EditKecamatanBottomSheetDialog {
            val fragment = EditKecamatanBottomSheetDialog()
            val args = Bundle()
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
    }

    private fun setupListener(){
        binding.btnUbahKecamatan.setOnClickListener {
            val namaKecamatan = binding.edUNamaKecamatan.text.toString()
            Toast.makeText(requireContext(), "$namaKecamatan dirubah", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}