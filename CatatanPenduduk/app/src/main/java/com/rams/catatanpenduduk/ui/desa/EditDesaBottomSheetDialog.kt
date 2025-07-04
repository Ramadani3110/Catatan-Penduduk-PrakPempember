package com.rams.catatanpenduduk.ui.desa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rams.catatanpenduduk.databinding.BottomSheetEditDesaBinding
import com.rams.catatanpenduduk.helper.KecamatanDumy

class EditDesaBottomSheetDialog : BottomSheetDialogFragment() {
    companion object {
        val TAG = EditDesaBottomSheetDialog::class.simpleName
        fun newInstance() = EditDesaBottomSheetDialog()
    }
    private val dumyKecamatan = listOf<KecamatanDumy>(
        KecamatanDumy(1,"Kecamatan Tegal"),
        KecamatanDumy(2,"Kecamatan Jonggol"),
        KecamatanDumy(3,"Kecamatan Hongkon"),
        KecamatanDumy(4,"Kecamatan Surabaya"),
        KecamatanDumy(5,"Kecamatan Ponorogo"),
    )
    private var _binding: BottomSheetEditDesaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetEditDesaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}