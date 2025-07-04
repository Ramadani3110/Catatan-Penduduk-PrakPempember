package com.rams.catatanpenduduk.ui.desa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rams.catatanpenduduk.databinding.ItemDesaLayoutBinding
import com.rams.catatanpenduduk.helper.DesaDumy
import com.rams.catatanpenduduk.utils.GenericDiffUtil

class DesaAdapter(
    private val onItemClick: (DesaDumy) -> Unit
) : ListAdapter<DesaDumy, DesaAdapter.DesaViewHolder>(GenericDiffUtil(
    areItemsSame = { old, new -> old.id == new.id },
    areContentsSame = { old, new -> old == new }
)) {
    inner class DesaViewHolder(private val binding : ItemDesaLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(desaDumy: DesaDumy){
            binding.apply {
                tvNamaDesa.text = desaDumy.nama
                tvNamaKecamatan.text = desaDumy.kecamatan

                root.setOnClickListener {
                    onItemClick(desaDumy)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DesaViewHolder {
        val binding = ItemDesaLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DesaViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DesaViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

}