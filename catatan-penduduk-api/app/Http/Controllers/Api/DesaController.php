<?php

namespace App\Http\Controllers\Api;


use App\Http\Controllers\Controller;
use App\Models\Desa;
use Illuminate\Http\Request;

class DesaController extends Controller
{
        public function index()
    {
        $desas = Desa::with('kecamatan')->get();

        $data = $desas->map(function ($desa) {
            return [
                'id' => $desa->id,
                'nama_desa' => $desa->nama_desa,
                'kecamatan_id' => $desa->kecamatan_id,
                'nama_kecamatan' => $desa->kecamatan->nama_kecamatan ?? null,
                'created_at' => $desa->created_at,
                'updated_at' => $desa->updated_at,
            ];
        });

        return response()->json([
            'success' => true,
            'message' => 'Data desa berhasil diambil',
            'data' => $data,
        ]);
    }

    // POST /desas → Menyimpan desa baru
    public function store(Request $request)
    {
        $request->validate([
            'nama_desa' => 'required|string|max:255',
            'kecamatan_id' => 'required|exists:kecamatans,id',
        ]);

        $desa = Desa::create([
            'nama_desa' => $request->nama_desa,
            'kecamatan_id' => $request->kecamatan_id,
        ]);

        return response()->json([
            'success' => true,
            'message' => 'Data desa berhasil disimpan',
            'data' => $desa
        ], 201);
    }

    // GET /desas/{id} → Menampilkan 1 desa dengan nama kecamatan
    public function show($id)
    {
        $desa = Desa::with('kecamatan')->find($id);

        if (!$desa) {
            return response()->json(['success' => false, 'message' => 'Desa tidak ditemukan'], 404);
        }

        return response()->json([
            'success' => true,
            'message' => 'Data desa ditemukan',
            'data' => [
                'id' => $desa->id,
                'nama_desa' => $desa->nama_desa,
                'kecamatan_id' => $desa->kecamatan_id,
                'nama_kecamatan' => $desa->kecamatan->nama_kecamatan ?? null,
                'created_at' => $desa->created_at,
                'updated_at' => $desa->updated_at,
            ]
        ]);
    }

    // PUT /desas/{id} → Update data desa
    public function update(Request $request, $id)
    {
        $request->validate([
            'nama_desa' => 'required|string|max:255',
            'kecamatan_id' => 'required|exists:kecamatans,id',
        ]);

        $desa = Desa::find($id);

        if (!$desa) {
            return response()->json(['success' => false, 'message' => 'Desa tidak ditemukan'], 404);
        }

        $desa->update([
            'nama_desa' => $request->nama_desa,
            'kecamatan_id' => $request->kecamatan_id,
        ]);

        return response()->json([
            'success' => true,
            'message' => 'Data desa berhasil diperbarui',
            'data' => $desa
        ]);
    }

    // DELETE /desas/{id} → Menghapus desa
    public function destroy($id)
    {
        $desa = Desa::find($id);

        if (!$desa) {
            return response()->json(['success' => false, 'message' => 'Desa tidak ditemukan'], 404);
        }

        $desa->delete();

        return response()->json([
            'success' => true,
            'message' => 'Data desa berhasil dihapus'
        ]);
    }
}
