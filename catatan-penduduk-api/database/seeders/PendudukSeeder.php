<?php

namespace Database\Seeders;

use App\Models\Desa;
use App\Models\Penduduk;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class PendudukSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
       $desas = Desa::all();

        Penduduk::insert([
            [
                'nik' => '1234567890123456',
                'nama' => 'Andi',
                'jenis_kelamin' => 'L',
                'umur' => 30,
                'desa_id' => $desas[0]->id,
                'created_at' => now()
            ],
            [
                'nik' => '1234567890123457',
                'nama' => 'Budi',
                'jenis_kelamin' => 'L',
                'umur' => 25,
                'desa_id' => $desas[1]->id,
                'created_at' => now()
            ],
            [
                'nik' => '1234567890123458',
                'nama' => 'Citra',
                'jenis_kelamin' => 'P',
                'umur' => 28,
                'desa_id' => $desas[2]->id,
                'created_at' => now()
            ],
        ]);
    }
}
