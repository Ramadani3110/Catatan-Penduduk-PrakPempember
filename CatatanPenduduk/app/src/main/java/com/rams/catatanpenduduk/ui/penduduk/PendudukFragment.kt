package com.rams.catatanpenduduk.ui.penduduk

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rams.catatanpenduduk.R
import com.rams.catatanpenduduk.databinding.FragmentPendudukBinding
import com.rams.catatanpenduduk.di.Inject
import com.rams.catatanpenduduk.di.ViewModelFactory
import com.rams.catatanpenduduk.utils.FabHandler

class PendudukFragment : Fragment(), FabHandler {

    private var _binding: FragmentPendudukBinding? = null
    private val binding get() = _binding!!
    private lateinit var pendudukAdapter: PendudukAdapter
    private lateinit var pendudukViewModel: PendudukViewModel

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
        val repository = Inject.provideRepository(requireContext())
        val factory = ViewModelFactory(repository)
        pendudukViewModel = ViewModelProvider(this, factory)[PendudukViewModel::class.java]

        setupAdapter()
        setupObservers()
        setupSwipeToDeleteWithConfirmation()
        if (pendudukViewModel.pendudukResult.value.isNullOrEmpty()) {
            pendudukViewModel.getPenduduk()
        }
    }

    private fun setupAdapter(){
        pendudukAdapter = PendudukAdapter{penduduk ->
            EditPendudukBottomSheetDialog.newInstance(
                penduduk.id,
                penduduk.nik,
                penduduk.nama,
                penduduk.umur,
                penduduk.jenisKelamin,
                penduduk.desaId
            ).apply {
                pendudukViewModel = this@PendudukFragment.pendudukViewModel
            }.show(parentFragmentManager, EditPendudukBottomSheetDialog.TAG)
        }

        binding.apply {
            rvPenduduk.apply {
                adapter = pendudukAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setupObservers(){
        pendudukViewModel.pendudukResult.observe(viewLifecycleOwner){
            pendudukAdapter.submitList(it)
        }
        pendudukViewModel.isLoading.observe(viewLifecycleOwner){
            binding.pbLPenduduk.visibility = if(it) View.VISIBLE else View.GONE
            binding.rvPenduduk.visibility = if(it) View.GONE else View.VISIBLE
        }
        pendudukViewModel.errorMessage.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        pendudukViewModel.operationMessage.observe(viewLifecycleOwner){
            if(!it.isNullOrBlank()){
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("UseKtx")
    private fun setupSwipeToDeleteWithConfirmation() {
        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.outline_delete_24)!!
        icon.setTint(Color.WHITE)
        val background = ColorDrawable(Color.RED)
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemToDelete = pendudukAdapter.currentList[position]

                AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Kecamatan")
                    .setMessage("Yakin ingin menghapus '${itemToDelete.nama}'?")
                    .setPositiveButton("Ya") { _, _ ->
                        pendudukViewModel.deletePenduduk(itemToDelete.id)
                    }
                    .setNegativeButton("Batal") { _, _ ->
                        pendudukAdapter.notifyItemChanged(position)
                    }
                    .setCancelable(false)
                    .show()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - icon.intrinsicHeight) / 2

                if (dX > 0) { // Swipe ke kanan
                    background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
                    icon.setBounds(
                        itemView.left + iconMargin,
                        itemView.top + iconMargin,
                        itemView.left + iconMargin + icon.intrinsicWidth,
                        itemView.bottom - iconMargin
                    )
                } else if (dX < 0) { // Swipe ke kiri
                    background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    icon.setBounds(
                        itemView.right - iconMargin - icon.intrinsicWidth,
                        itemView.top + iconMargin,
                        itemView.right - iconMargin,
                        itemView.bottom - iconMargin
                    )
                } else {
                    background.setBounds(0, 0, 0, 0)
                    icon.setBounds(0, 0, 0, 0)
                }

                background.draw(c)
                icon.draw(c)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvPenduduk)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onFabClick() {
        val dialog = AddPendudukBottomSheetDialog.newInstance().apply {
            pendudukViewModel = this@PendudukFragment.pendudukViewModel
        }
        dialog.show(parentFragmentManager, AddPendudukBottomSheetDialog.TAG)
    }

    override fun onShow(): Boolean {
        return true
    }

}