package com.rams.catatanpenduduk.ui.penduduk

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rams.catatanpenduduk.data.model.DataPenduduk
import com.rams.catatanpenduduk.databinding.ItemPendudukLayoutBinding
import com.rams.catatanpenduduk.utils.GenericDiffUtil

class PendudukAdapter(
    private val onItemClick: (DataPenduduk) -> Unit
) : ListAdapter<DataPenduduk, PendudukAdapter.PendudukViewHolder>(
    GenericDiffUtil(
        areItemsSame = { old, new -> old.id == new.id },
        areContentsSame = { old, new -> old == new }
    )
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendudukViewHolder {
        val binding = ItemPendudukLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendudukViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PendudukViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class PendudukViewHolder(private val binding: ItemPendudukLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(dataPenduduk: DataPenduduk){
            binding.apply {
                tvNamaPend.text = dataPenduduk.nama
                tvNikPend.text = "NIK : ${dataPenduduk.nik}"
                if (dataPenduduk.jenisKelamin == "L") {
                    tvGenderPend.text = "Laki-laki"
                } else {
                    tvGenderPend.text = "Perempuan"
                }
                tvAgePend.text = "${dataPenduduk.umur} Tahun"
                tvDesaPend.text = dataPenduduk.namaDesa
                root.setOnClickListener {
                    onItemClick(dataPenduduk)
                }
            }
        }
    }


}