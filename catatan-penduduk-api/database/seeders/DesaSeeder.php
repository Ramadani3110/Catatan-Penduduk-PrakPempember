<?php

namespace Database\Seeders;

use App\Models\Desa;
use App\Models\Kecamatan;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class DesaSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
       $kecamatans = Kecamatan::all();

        Desa::insert([
            ['nama_desa' => 'Desa Jeruk', 'kecamatan_id' => $kecamatans[0]->id, 'created_at' => now()],
            ['nama_desa' => 'Desa Kali', 'kecamatan_id' => $kecamatans[0]->id, 'created_at' => now()],
            ['nama_desa' => 'Desa Apel', 'kecamatan_id' => $kecamatans[1]->id, 'created_at' => now()],
            ['nama_desa' => 'Desa Anggur', 'kecamatan_id' => $kecamatans[1]->id, 'created_at' => now()],
        ]);
    }
}
