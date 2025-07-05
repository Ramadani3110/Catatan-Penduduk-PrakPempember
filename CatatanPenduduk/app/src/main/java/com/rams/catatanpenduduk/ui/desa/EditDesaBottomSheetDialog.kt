package com.rams.catatanpenduduk.ui.desa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rams.catatanpenduduk.data.request.DesaRequest
import com.rams.catatanpenduduk.databinding.BottomSheetEditDesaBinding
import com.rams.catatanpenduduk.di.Inject
import com.rams.catatanpenduduk.di.ViewModelFactory
import com.rams.catatanpenduduk.helper.KecamatanDumy
import com.rams.catatanpenduduk.ui.kecamatan.EditKecamatanBottomSheetDialog
import com.rams.catatanpenduduk.ui.kecamatan.KecamatanViewModel

class EditDesaBottomSheetDialog : BottomSheetDialogFragment() {
    companion object {
        private const val ARG_ID = "arg_id"
        private const val ARG_NAMA = "arg_nama"
        private const val ARG_KEC_ID = "arg_kec_id"
        val TAG = EditDesaBottomSheetDialog::class.simpleName
        fun newInstance(id: Int, nama: String, kecamatanId: Int) : EditDesaBottomSheetDialog {
            val fragment = EditDesaBottomSheetDialog()
            val args = Bundle().apply {
                putInt(ARG_ID, id)
                putString(ARG_NAMA, nama)
                putInt(ARG_KEC_ID, kecamatanId)
            }
            fragment.arguments = args
            return fragment
        }
    }
    private var _binding: BottomSheetEditDesaBinding? = null
    private val binding get() = _binding!!
    private lateinit var kecamatanViewModel: KecamatanViewModel
    lateinit var desaViewModel: DesaViewModel

    private var selectedKecamatanId: Int? = null
    private var desaId: Int = -1

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
        val repository = Inject.provideRepository(requireContext())
        val factory = ViewModelFactory(repository)
        kecamatanViewModel = ViewModelProvider(this, factory)[KecamatanViewModel::class.java]

        desaId = arguments?.getInt(ARG_ID) ?: -1
        val namaDesa = arguments?.getString(ARG_NAMA).orEmpty()
        val kecamatanId = arguments?.getInt(ARG_KEC_ID)

        selectedKecamatanId = kecamatanId

        binding.edUNamaDesa.setText(namaDesa)

        setupAutoCompleteKecamatan(kecamatanId)
        setupListener()
        setupObservers()
    }

    private fun setupAutoCompleteKecamatan(preselectedId: Int?) {
        val actv = binding.actvUKecamatanId
        actv.threshold = 1

        kecamatanViewModel.kecamatanResult.observe(viewLifecycleOwner) { list ->
            val displayedList = list.sortedBy { it.namaKecamatan }
            val namaList = displayedList.map { it.namaKecamatan }
            val mapNamaToKecamatan = displayedList.associateBy { it.namaKecamatan }

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                namaList
            )
            actv.setAdapter(adapter)

            // Jika ada ID kecamatan awal, cari dan isi namanya
            preselectedId?.let { id ->
                val preselected = displayedList.find { it.id == id }
                actv.setText(preselected?.namaKecamatan, false)
            }

            actv.setOnItemClickListener { _, _, position, _ ->
                val namaDipilih = adapter.getItem(position)
                val selected = mapNamaToKecamatan[namaDipilih]
                selectedKecamatanId = selected?.id
            }

            actv.doAfterTextChanged { text ->
                val selected = mapNamaToKecamatan[text.toString()]
                selectedKecamatanId = selected?.id
            }
        }

        kecamatanViewModel.getKecamatan()
    }

    private fun setupListener() {
        binding.btnUbahDesa.setOnClickAction {
            val namaBaru = binding.edUNamaDesa.text.toString().trim()

            if (namaBaru.isEmpty()) {
                binding.edUNamaDesa.error = "Nama desa tidak boleh kosong"
                return@setOnClickAction
            }

            if (selectedKecamatanId == null) {
                binding.actvUKecamatanId.error = "Pilih kecamatan"
                return@setOnClickAction
            }

            val request = DesaRequest(namaDesa = namaBaru, kecamatanId = selectedKecamatanId!!)
            desaViewModel.updateDesa(desaId, request)
        }
    }

    private fun setupObservers() {
        desaViewModel.isLoadingOperation.observe(viewLifecycleOwner) {
            binding.btnUbahDesa.showLoading(it)
        }

        kecamatanViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.actvUKecamatanId.isEnabled = !it
            binding.edUNamaDesa.isEnabled = !it
            binding.btnUbahDesa.isEnabled = !it
            binding.pbBSEUDesa.visibility = if (it) View.VISIBLE else View.GONE
        }

        desaViewModel.operationMessage.observe(viewLifecycleOwner) { message ->
            Log.d("EditDesaBottomSheetDialog", "operationMessage: $message")
            if (!message.isNullOrBlank()) {
                Log.d("EditDesaBottomSheetDialog", "masuk")
                dismiss()
                desaViewModel.clearOperationMessage()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}