package com.rams.catatanpenduduk.ui.kecamatan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rams.catatanpenduduk.data.model.DataKecamatan
import com.rams.catatanpenduduk.databinding.ItemKecamatanLayoutBinding
import com.rams.catatanpenduduk.helper.KecamatanDumy
import com.rams.catatanpenduduk.utils.GenericDiffUtil

class KecamatanAdapter(
    private val onItemClick: (DataKecamatan) -> Unit
) : ListAdapter<DataKecamatan, KecamatanAdapter.KecamatanViewHolder>(
    GenericDiffUtil(
        areItemsSame = { old, new -> old.id == new.id },
        areContentsSame = { old, new -> old == new }
    )
) {
    inner class KecamatanViewHolder(private val binding: ItemKecamatanLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(dataKecamatan: DataKecamatan){
            binding.apply {
                tvNamaKec.text = dataKecamatan.namaKecamatan
                root.setOnClickListener {
                    onItemClick(dataKecamatan)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KecamatanViewHolder {
        val binding = ItemKecamatanLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KecamatanViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: KecamatanViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}