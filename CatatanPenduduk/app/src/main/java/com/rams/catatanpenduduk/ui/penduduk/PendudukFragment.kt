package com.rams.catatanpenduduk.ui.penduduk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rams.catatanpenduduk.databinding.FragmentPendudukBinding
import com.rams.catatanpenduduk.helper.PendudukDumy
import com.rams.catatanpenduduk.utils.FabHandler

class PendudukFragment : Fragment(), FabHandler {

    private val dumyPenduduk = listOf<PendudukDumy>(
        PendudukDumy(1,"123123","Rams","Laki-Laki",19,"Desa Konoha"),
        PendudukDumy(2,"124215","Nusi K","Perempuan",20,"Desa Ponorogo"),
        PendudukDumy(3,"531341","Juju P","Perempuan",19,"Desa Jonggol"),
        PendudukDumy(4,"512341","Budi","Laki-Laki",19,"Desa Budiman"),
        PendudukDumy(5,"324125","Anti","Laki-Laki",19,"Desa Jeruk"),
        PendudukDumy(6,"212341","Rere","Perempuan",19,"Desa Hitam"),
    )
    private var _binding: FragmentPendudukBinding? = null
    private val binding get() = _binding!!
    private lateinit var pendudukAdapter: PendudukAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPendudukBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter(){
        pendudukAdapter = PendudukAdapter{penduduk ->
            Toast.makeText(requireContext(), penduduk, Toast.LENGTH_SHORT).show()
        }

        binding.apply {
            rvPenduduk.apply {
                adapter = pendudukAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        pendudukAdapter.submitList(dumyPenduduk)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onFabClick() {
        Toast.makeText(requireContext(), "Fab di click dari penduduk", Toast.LENGTH_SHORT).show()
    }

    override fun onShow(): Boolean {
        return true
    }

}