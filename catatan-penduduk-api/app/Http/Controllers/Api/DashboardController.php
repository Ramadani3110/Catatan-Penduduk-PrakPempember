<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Desa;
use App\Models\Kecamatan;
use App\Models\Penduduk;

class DashboardController extends Controller
{
    public function index()
    {
        $totalKecamatan = Kecamatan::count();
        $totalDesa = Desa::count();
        $totalPenduduk = Penduduk::count();

        return response()->json([
            'success' => true,
            'message' => 'Data dashboard berhasil diambil',
            'data' => [
                'total_kecamatan' => $totalKecamatan,
                'total_desa' => $totalDesa,
                'total_penduduk' => $totalPenduduk,
            ]
        ]);
    }
}
