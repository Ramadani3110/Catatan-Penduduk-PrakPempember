<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Kecamatan;
use Illuminate\Http\Request;

class KecamatanController extends Controller
{
    // Tampilkan semua data kecamatan
    public function index()
    {
        $kecamatans = Kecamatan::all()->map(function ($item) {
            return [
                'id' => $item->id,
                'nama_kecamatan' => $item->nama_kecamatan,
                'created_at' => $item->created_at,
            ];
        });

        return response()->json([
            'success' => true,
            'message' => 'Data kecamatan berhasil diambil',
            'data' => $kecamatans
        ], 200);
    }

    // Simpan data kecamatan baru
    public function store(Request $request)
    {
        $request->validate([
            'nama_kecamatan' => 'required|string|max:255',
        ]);

        $kecamatan = Kecamatan::create([
            'nama_kecamatan' => $request->nama_kecamatan,
        ]);

        return response()->json([
            'message' => 'Data kecamatan berhasil disimpan.',
            'data' => $kecamatan
        ], 201);
    }

    // Tampilkan satu data kecamatan berdasarkan ID
    public function show($id)
    {
        $kecamatan = Kecamatan::find($id);

        if (!$kecamatan) {
            return response()->json(['message' => 'Kecamatan tidak ditemukan'], 404);
        }

        return response()->json([
            'success' => true,
            'message' => 'Detail kecamatan berhasil diambil',
            'data' => [
                'id' => $kecamatan->id,
                'nama_kecamatan' => $kecamatan->nama_kecamatan,
                'created_at' => $kecamatan->created_at,
                'updated_at' => $kecamatan->updated_at
            ]
        ], 200);
    }

    // Update data kecamatan
    public function update(Request $request, $id)
    {
        $request->validate([
            'nama_kecamatan' => 'required|string|max:255',
        ]);

        $kecamatan = Kecamatan::find($id);

        if (!$kecamatan) {
            return response()->json(['message' => 'Kecamatan tidak ditemukan'], 404);
        }

        $kecamatan->update([
            'nama_kecamatan' => $request->nama_kecamatan,
        ]);

        return response()->json([
            'message' => 'Data kecamatan berhasil diperbarui.',
            'data' => $kecamatan
        ]);
    }

    // Hapus data kecamatan
    public function destroy($id)
    {
        $kecamatan = Kecamatan::find($id);

        if (!$kecamatan) {
            return response()->json(['message' => 'Kecamatan tidak ditemukan'], 404);
        }

        $kecamatan->delete();

        return response()->json(['message' => 'Data kecamatan berhasil dihapus.']);
    }
}
