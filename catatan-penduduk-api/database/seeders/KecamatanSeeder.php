<?php

namespace Database\Seeders;

use App\Models\Kecamatan;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class KecamatanSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        Kecamatan::insert([
            ['nama_kecamatan' => 'Kecamatan Jonggol', 'created_at' => now()],
            ['nama_kecamatan' => 'Kecamatan Ponorogo', 'created_at' => now()],
        ]);
    }
}
