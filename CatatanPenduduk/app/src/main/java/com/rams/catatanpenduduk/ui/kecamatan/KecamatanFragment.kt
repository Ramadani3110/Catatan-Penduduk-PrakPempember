package com.rams.catatanpenduduk.ui.kecamatan

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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rams.catatanpenduduk.R
import com.rams.catatanpenduduk.databinding.FragmentKecamatanBinding
import com.rams.catatanpenduduk.helper.KecamatanDumy
import com.rams.catatanpenduduk.utils.FabHandler

class KecamatanFragment : Fragment(), FabHandler {

    private val dumyKecamatan = listOf<KecamatanDumy>(
        KecamatanDumy(1,"Kecamatan Tegal"),
        KecamatanDumy(2,"Kecamatan Jonggol"),
        KecamatanDumy(3,"Kecamatan Hongkon"),
        KecamatanDumy(4,"Kecamatan Surabaya"),
        KecamatanDumy(5,"Kecamatan Ponorogo"),
    )

    private var _binding: FragmentKecamatanBinding? = null
    private val binding get() = _binding!!
    private lateinit var kecamatanAdapter: KecamatanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKecamatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupSwipeToDeleteWithConfirmation()
    }

    private fun setupAdapter(){
        kecamatanAdapter = KecamatanAdapter{kecamatan ->
            EditKecamatanBottomSheetDialog.newInstance(kecamatan.nama)
                .show(parentFragmentManager, EditKecamatanBottomSheetDialog.TAG)
        }

        binding.apply {
            rvKecamatan.apply {
                adapter = kecamatanAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        kecamatanAdapter.submitList(dumyKecamatan)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onFabClick() {
        AddKecamatanBottomSheetDialog.newInstance()
            .show(parentFragmentManager, AddKecamatanBottomSheetDialog.TAG)
    }

    override fun onShow(): Boolean {
        return true
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
                val itemToDelete = kecamatanAdapter.currentList[position]

                AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Kecamatan")
                    .setMessage("Yakin ingin menghapus '${itemToDelete.nama}'?")
                    .setPositiveButton("Ya") { _, _ ->
                        val newList = kecamatanAdapter.currentList.toMutableList()
                        newList.removeAt(position)
                        kecamatanAdapter.submitList(newList)
                        Toast.makeText(requireContext(), "Dihapus: ${itemToDelete.nama}", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Batal") { _, _ ->
                        kecamatanAdapter.notifyItemChanged(position)
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
        itemTouchHelper.attachToRecyclerView(binding.rvKecamatan)
    }


}