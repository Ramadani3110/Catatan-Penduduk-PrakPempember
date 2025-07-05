package com.rams.catatanpenduduk.ui.desa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rams.catatanpenduduk.data.model.DataDesa
import com.rams.catatanpenduduk.databinding.ItemDesaLayoutBinding
import com.rams.catatanpenduduk.utils.GenericDiffUtil

class DesaAdapter(
    private val onItemClick: (DataDesa) -> Unit
) : ListAdapter<DataDesa, DesaAdapter.DesaViewHolder>(GenericDiffUtil(
    areItemsSame = { old, new -> old.id == new.id },
    areContentsSame = { old, new -> old == new }
)) {
    inner class DesaViewHolder(private val binding : ItemDesaLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataDesa: DataDesa){
            binding.apply {
                tvNamaDesa.text = dataDesa.namaDesa
                tvNamaKecamatan.text = dataDesa.namaKecamatan

                root.setOnClickListener {
                    onItemClick(dataDesa)
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