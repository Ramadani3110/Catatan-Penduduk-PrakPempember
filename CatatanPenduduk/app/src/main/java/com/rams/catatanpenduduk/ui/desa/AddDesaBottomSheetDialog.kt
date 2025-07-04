package com.rams.catatanpenduduk.ui.desa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rams.catatanpenduduk.databinding.BottomSheetAddDesaBinding
import com.rams.catatanpenduduk.helper.KecamatanDumy

class AddDesaBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {
        val TAG = AddDesaBottomSheetDialog::class.simpleName
        fun newInstance() = AddDesaBottomSheetDialog()
    }
    private val dumyKecamatan = listOf<KecamatanDumy>(
        KecamatanDumy(1,"Kecamatan Tegal"),
        KecamatanDumy(2,"Kecamatan Jonggol"),
        KecamatanDumy(3,"Kecamatan Hongkon"),
        KecamatanDumy(4,"Kecamatan Surabaya"),
        KecamatanDumy(5,"Kecamatan Ponorogo"),
    )
    private var _binding: BottomSheetAddDesaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetAddDesaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAutoCompleteKecamatan()
    }

    private fun setupAutoCompleteKecamatan(){
        val autoCompleteKecamatan = binding.actvKecamatanId

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, dumyKecamatan)
        autoCompleteKecamatan.setAdapter(adapter)

        autoCompleteKecamatan.setOnItemClickListener {parent, _, position, _ ->
            val selectedKecamatan = parent.getItemAtPosition(position) as KecamatanDumy
            Toast.makeText(requireContext(), "Dipilih ${selectedKecamatan.nama} - ${selectedKecamatan.id}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}