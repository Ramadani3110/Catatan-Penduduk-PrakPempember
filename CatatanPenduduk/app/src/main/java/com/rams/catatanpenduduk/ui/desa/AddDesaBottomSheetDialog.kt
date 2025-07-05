package com.rams.catatanpenduduk.ui.desa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rams.catatanpenduduk.data.request.DesaRequest
import com.rams.catatanpenduduk.databinding.BottomSheetAddDesaBinding
import com.rams.catatanpenduduk.di.Inject
import com.rams.catatanpenduduk.di.ViewModelFactory
import com.rams.catatanpenduduk.ui.kecamatan.KecamatanViewModel

class AddDesaBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {
        val TAG = AddDesaBottomSheetDialog::class.simpleName
        fun newInstance() = AddDesaBottomSheetDialog()
    }

    private var _binding: BottomSheetAddDesaBinding? = null
    private val binding get() = _binding!!
    private lateinit var kecamatanViewModel: KecamatanViewModel
    lateinit var desaViewModel: DesaViewModel
    private var selectedKecamatanId: Int? = null

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
        val repository = Inject.provideRepository(requireContext())
        val factory = ViewModelFactory(repository)
        kecamatanViewModel = ViewModelProvider(this, factory)[KecamatanViewModel::class.java]
        setupAutoCompleteKecamatan()
        setupListener()
        setupObservers()
    }

    private fun setupObservers() {
        desaViewModel.isLoadingOperation.observe(viewLifecycleOwner) {
            binding.btnTambahDesa.showLoading(it)
        }

        desaViewModel.operationMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrBlank()) {
                dismiss()
                desaViewModel.clearOperationMessage()
            }
        }
    }

    private fun setupListener() {
        binding.btnTambahDesa.setOnClickAction {
            val namaDesa = binding.edNamaDesa.text.toString().trim()

            if (namaDesa.isEmpty()) {
                binding.edNamaDesa.error = "Nama desa tidak boleh kosong"
                return@setOnClickAction
            }

            if (selectedKecamatanId == null) {
                binding.actvKecamatanId.error = "Pilih kecamatan terlebih dahulu"
                return@setOnClickAction
            }

            val request = DesaRequest(
                namaDesa = namaDesa,
                kecamatanId = selectedKecamatanId!!
            )

            desaViewModel.addDesa(request)
        }
    }

    private fun setupAutoCompleteKecamatan() {
        val autoCompleteKecamatan = binding.actvKecamatanId
        autoCompleteKecamatan.threshold = 1

        kecamatanViewModel.kecamatanResult.observe(viewLifecycleOwner) { list ->
            val displayedList = list.sortedBy { it.namaKecamatan }
            val namaList = displayedList.map { it.namaKecamatan }
            val namaToKecamatanMap = displayedList.associateBy { it.namaKecamatan }

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                namaList
            )

            autoCompleteKecamatan.setAdapter(adapter)

            // Handle klik pada item dari dropdown
            autoCompleteKecamatan.setOnItemClickListener { _, _, position, _ ->
                val nama = adapter.getItem(position)
                val selected = namaToKecamatanMap[nama]
                selectedKecamatanId = selected?.id
            }

            // Validasi ketika user ketik manual id nya dipilih kembali
            autoCompleteKecamatan.doAfterTextChanged { text ->
                val selected = namaToKecamatanMap[text.toString()]
                selectedKecamatanId = selected?.id
            }
        }

        kecamatanViewModel.getKecamatan()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}