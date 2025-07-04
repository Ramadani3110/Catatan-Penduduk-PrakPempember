package com.rams.catatanpenduduk.helper

data class KecamatanDumy(
    val id: Int,
    val nama: String,
){
    override fun toString(): String {
        return nama
    }
}