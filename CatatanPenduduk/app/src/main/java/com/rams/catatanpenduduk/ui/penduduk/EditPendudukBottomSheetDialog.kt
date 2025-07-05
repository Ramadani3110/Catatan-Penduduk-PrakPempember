package com.rams.catatanpenduduk.ui.penduduk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rams.catatanpenduduk.R
import com.rams.catatanpenduduk.data.request.PendudukRequest
import com.rams.catatanpenduduk.databinding.BottomSheetEditPendudukBinding
import com.rams.catatanpenduduk.di.Inject
import com.rams.catatanpenduduk.di.ViewModelFactory
import com.rams.catatanpenduduk.ui.desa.DesaViewModel
import kotlin.collections.get

class EditPendudukBottomSheetDialog : BottomSheetDialogFragment(){
    private var _binding: BottomSheetEditPendudukBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_ID = "arg_id"
        private const val ARG_NIK = "arg_nik"
        private const val ARG_NAMA = "arg_nama"
        private const val ARG_UMUR = "arg_umur"
        private const val ARG_JK = "arg_jk"
        private const val ARG_ID_DESA = "arg_id_desa"
        fun newInstance(id: Int, nik: String, nama: String, umur: Int, jk: String,idDesa: Int) : EditPendudukBottomSheetDialog{
            val fragment = EditPendudukBottomSheetDialog()
            val args = Bundle().apply {
                putInt(ARG_ID, id)
                putString(ARG_NIK, nik)
                putString(ARG_NAMA, nama)
                putInt(ARG_UMUR, umur)
                putString(ARG_JK, jk)
                putInt(ARG_ID_DESA, idDesa)
            }
            fragment.arguments = args
            return fragment
        }
        val TAG = EditPendudukBottomSheetDialog::class.simpleName
    }

    private var selectedDesaId: Int? = null
    private var selectedJenisKelamin: String? = null
    private lateinit var desaViewModel: DesaViewModel
    lateinit var pendudukViewModel: PendudukViewModel
    private var pendudukId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetEditPendudukBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = Inject.provideRepository(requireContext())
        val factory = ViewModelFactory(repository)
        desaViewModel = ViewModelProvider(requireParentFragment(), factory)[DesaViewModel::class.java]

        pendudukId = arguments?.getInt(ARG_ID) ?: -1
        val nik = arguments?.getString(ARG_NIK).orEmpty()
        val nama = arguments?.getString(ARG_NAMA).orEmpty()
        val umur = arguments?.getInt(ARG_UMUR) ?: 0
        val jk = arguments?.getString(ARG_JK).orEmpty()
        val desaId = arguments?.getInt(ARG_ID_DESA)

        selectedDesaId = desaId
        selectedJenisKelamin = jk

        binding.apply {
            edUNikPenduduk.setText(nik)
            edUNamaPenduduk.setText(nama)
            edUpUmurPenduduk.setText(umur.toString())

            if (jk == "L") rUbLaki.isChecked = true
            else rbUPerempuan.isChecked = true

            rgUJkPenduduk.setOnCheckedChangeListener { _, checkedId ->
                selectedJenisKelamin = if (checkedId == R.id.rUbLaki) "L" else "P"
            }

        }
        setupAutoCompleteDesa(desaId)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        desaViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.pbUPenduduk.visibility = if (it) View.VISIBLE else View.GONE
            binding.groupFormPenduduk.visibility = if (it) View.GONE else View.VISIBLE
        }
        pendudukViewModel.isLoadingOperation.observe(viewLifecycleOwner) {
            binding.btnUbahDesa.showLoading(it)
        }
        pendudukViewModel.operationMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrBlank()) {
                dismiss()
                pendudukViewModel.clearOperationMessage()
            }
        }
    }

    private fun setupListeners(){
        binding.btnUbahDesa.setOnClickAction {
            val nik = binding.edUNikPenduduk.text.toString().trim()
            val nama = binding.edUNamaPenduduk.text.toString().trim()
            val umurText = binding.edUpUmurPenduduk.text.toString().trim()
            val desaId = selectedDesaId
            val jenisKelamin = selectedJenisKelamin

            var valid = true

            if (nik.isEmpty()) {
                binding.edUNikPenduduk.error = "NIK tidak boleh kosong"
                valid = false
            }

            if (nama.isEmpty()) {
                binding.edUNamaPenduduk.error = "Nama tidak boleh kosong"
                valid = false
            }

            if (umurText.isEmpty()) {
                binding.edUpUmurPenduduk.error = "Umur tidak boleh kosong"
                valid = false
            }

            if (jenisKelamin.isNullOrBlank()) {
                Toast.makeText(requireContext(), "Pilih jenis kelamin", Toast.LENGTH_SHORT).show()
                valid = false
            }

            if (desaId == null) {
                binding.actvUDesaId.error = "Pilih desa"
                valid = false
            }

            if (!valid) return@setOnClickAction

            val umur = umurText.toIntOrNull() ?: 0
            val request = PendudukRequest(
                nik = nik,
                nama = nama,
                umur = umur,
                jenisKelamin = jenisKelamin!!,
                desaId = desaId!!
            )

            val id = arguments?.getInt(ARG_ID) ?: return@setOnClickAction
            pendudukViewModel.updatePenduduk(id, request)
        }
    }

    private fun setupAutoCompleteDesa(preselectedId: Int?) {
        val actv = binding.actvUDesaId
        actv.threshold = 1

        desaViewModel.desaResult.observe(viewLifecycleOwner) { list ->
            val displayedList = list.sortedBy { it.namaDesa }
            val namaList = displayedList.map { it.namaDesa }
            val mapNamaToKecamatan = displayedList.associateBy { it.namaDesa }

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                namaList
            )
            actv.setAdapter(adapter)

            // Jika ada ID kecamatan awal, cari dan isi namanya
            preselectedId?.let { id ->
                val preselected = displayedList.find { it.id == id }
                actv.setText(preselected?.namaDesa, false)
            }

            actv.setOnItemClickListener { _, _, position, _ ->
                val namaDipilih = adapter.getItem(position)
                val selected = mapNamaToKecamatan[namaDipilih]
                selectedDesaId = selected?.id
            }

            actv.doAfterTextChanged { text ->
                val selected = mapNamaToKecamatan[text.toString()]
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