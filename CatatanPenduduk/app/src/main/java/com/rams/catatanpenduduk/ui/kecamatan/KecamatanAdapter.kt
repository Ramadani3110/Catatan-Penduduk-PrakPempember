package com.rams.catatanpenduduk.ui.kecamatan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rams.catatanpenduduk.databinding.ItemKecamatanLayoutBinding
import com.rams.catatanpenduduk.helper.KecamatanDumy
import com.rams.catatanpenduduk.utils.GenericDiffUtil

class KecamatanAdapter(
    private val onItemClick: (KecamatanDumy) -> Unit
) : ListAdapter<KecamatanDumy, KecamatanAdapter.KecamatanViewHolder>(
    GenericDiffUtil(
        areItemsSame = { old, new -> old.id == new.id },
        areContentsSame = { old, new -> old == new }
    )
) {
    inner class KecamatanViewHolder(private val binding: ItemKecamatanLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(kecamatanDumy: KecamatanDumy){
            binding.apply {
                tvNamaKec.text = kecamatanDumy.nama
                root.setOnClickListener {
                    onItemClick(kecamatanDumy)
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