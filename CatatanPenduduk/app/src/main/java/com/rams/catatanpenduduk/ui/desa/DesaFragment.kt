package com.rams.catatanpenduduk.ui.desa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rams.catatanpenduduk.databinding.FragmentDesaBinding
import com.rams.catatanpenduduk.helper.DesaDumy
import com.rams.catatanpenduduk.utils.FabHandler

class DesaFragment : Fragment(), FabHandler {
    private val dummyData = listOf(
        DesaDumy(1, "Desa A", "Kecamatan A"),
        DesaDumy(2, "Desa B", "Kecamatan B"),
        DesaDumy(3, "Desa C", "Kecamatan C"),
        DesaDumy(4, "Desa D", "Kecamatan D"),
        DesaDumy(5, "Desa E", "Kecamatan E"),
        DesaDumy(6, "Desa F", "Kecamatan F"),
        DesaDumy(7, "Desa G", "Kecamatan G"),
    )
    private var _binding: FragmentDesaBinding? = null
    private val binding get() = _binding!!

    private lateinit var desaAdapter: DesaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDesaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
    }

    private fun setupAdapter(){
        desaAdapter = DesaAdapter{desa ->
            Toast.makeText(requireContext(), desa.nama, Toast.LENGTH_SHORT).show()
        }

        binding.apply {
            rvDesa.apply {
                adapter = desaAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        desaAdapter.submitList(
            dummyData
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onFabClick() {
        AddDesaBottomSheetDialog.newInstance()
            .show(parentFragmentManager, AddDesaBottomSheetDialog.TAG)
    }

    override fun onShow(): Boolean {
        return true
    }
}