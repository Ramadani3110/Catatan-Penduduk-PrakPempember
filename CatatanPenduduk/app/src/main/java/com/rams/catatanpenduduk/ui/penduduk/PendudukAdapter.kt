package com.rams.catatanpenduduk.ui.penduduk

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rams.catatanpenduduk.databinding.ItemPendudukLayoutBinding
import com.rams.catatanpenduduk.helper.PendudukDumy
import com.rams.catatanpenduduk.utils.GenericDiffUtil

class PendudukAdapter(
    private val onItemClick: (String) -> Unit
) : ListAdapter<PendudukDumy, PendudukAdapter.PendudukViewHolder>(
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
        fun bind(pendudukDumy: PendudukDumy){
            binding.apply {
                tvNamaPend.text = pendudukDumy.nama
                tvNikPend.text = "NIK : ${pendudukDumy.nik}"
                tvGenderPend.text = pendudukDumy.jenisKelamin
                tvAgePend.text = "${pendudukDumy.umur} Tahun"
                tvDesaPend.text = pendudukDumy.namaDesa
                root.setOnClickListener {
                    onItemClick(pendudukDumy.nama)
                }
            }
        }
    }


}