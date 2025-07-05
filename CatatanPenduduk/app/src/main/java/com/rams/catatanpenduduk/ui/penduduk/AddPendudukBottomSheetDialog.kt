package com.rams.catatanpenduduk.ui.penduduk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rams.catatanpenduduk.data.request.PendudukRequest
import com.rams.catatanpenduduk.databinding.BottomSheetAddPendudukBinding
import com.rams.catatanpenduduk.di.Inject
import com.rams.catatanpenduduk.di.ViewModelFactory
import com.rams.catatanpenduduk.ui.desa.DesaViewModel
import kotlin.collections.get

class AddPendudukBottomSheetDialog : BottomSheetDialogFragment(){
    private var _binding: BottomSheetAddPendudukBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = AddPendudukBottomSheetDialog()
        val TAG = AddPendudukBottomSheetDialog::class.simpleName
    }

    lateinit var pendudukViewModel: PendudukViewModel
    private lateinit var desaViewModel: DesaViewModel
    private var selectedDesaId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetAddPendudukBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = Inject.provideRepository(requireContext())
        val factory = ViewModelFactory(repository)
        desaViewModel = ViewModelProvider(requireParentFragment(), factory)[DesaViewModel::class.java]

        setupAutoCompleteDesa()
        setupObservers()
        setupListener()
    }

    private fun setupObservers(){
        pendudukViewModel.isLoadingOperation.observe(viewLifecycleOwner){
            binding.btnTambahPenduduk.showLoading(it)
        }
        pendudukViewModel.operationMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrBlank()) {
                dismiss()
                pendudukViewModel.clearOperationMessage()
            }
        }
    }

    private fun setupListener(){
        binding.btnTambahPenduduk.setOnClickAction {
            val nik = binding.edNikPenduduk.text.toString().trim()
            val nama = binding.edNamaPenduduk.text.toString().trim()
            val umur = binding.edUmurPenduduk.text.toString().trim()
            val jk = when(binding.rgJkPenduduk.checkedRadioButtonId){
                binding.rbLaki.id -> "L"
                binding.rbPerempuan.id -> "P"
                else -> ""
            }

            if(nik.isEmpty()){
                binding.edNikPenduduk.error = "NIK tidak boleh kosong"
                return@setOnClickAction
            }

            if(nama.isEmpty()){
                binding.edNamaPenduduk.error = "Nama tidak boleh kosong"
                return@setOnClickAction
            }

            if(umur.isEmpty()){
                binding.edUmurPenduduk.error = "Umur tidak boleh kosong"
                return@setOnClickAction
            }

            if(selectedDesaId == null){
                binding.actvDesaId.error = "Pilih desa terlebih dahulu"
                return@setOnClickAction
            }

            val request = PendudukRequest(
                nik = nik,
                nama = nama,
                umur = umur.toInt(),
                jenisKelamin = jk,
                desaId = selectedDesaId!!
            )

            pendudukViewModel.addPenduduk(request)
        }
    }

    private fun setupAutoCompleteDesa() {
        val autoCompleteKecamatan = binding.actvDesaId
        autoCompleteKecamatan.threshold = 1

        desaViewModel.desaResult.observe(viewLifecycleOwner) { list ->
            val displayedList = list.sortedBy { it.namaDesa }
            val namaList = displayedList.map { it.namaDesa }
            val namaToKecamatanMap = displayedList.associateBy { it.namaDesa }

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
                selectedDesaId = selected?.id
            }

            // Validasi ketika user ketik manual id nya dipilih kembali
            autoCompleteKecamatan.doAfterTextChanged { text ->
                val selected = namaToKecamatanMap[text.toString()]
                selectedDesaId = selected?.id
            }
        }

        desaViewModel.getDesa()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}