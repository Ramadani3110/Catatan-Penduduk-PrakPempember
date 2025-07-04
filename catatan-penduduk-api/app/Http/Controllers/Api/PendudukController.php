<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Penduduk;
use Illuminate\Http\Request;

class PendudukController extends Controller
{
    public function index()
    {
        $penduduks = Penduduk::with('desa')->get();

        $data = $penduduks->map(function ($p) {
            return [
                'id' => $p->id,
                'nik' => $p->nik,
                'nama' => $p->nama,
                'jenis_kelamin' => $p->jenis_kelamin,
                'umur' => $p->umur,
                'desa_id' => $p->desa_id,
                'nama_desa' => $p->desa->nama_desa ?? null,
                'created_at' => $p->created_at,
                'updated_at' => $p->updated_at,
            ];
        });

        return response()->json([
            'success' => true,
            'message' => 'Data penduduk berhasil diambil',
            'data' => $data
        ]);
    }

    public function store(Request $request)
    {
        $request->validate([
            'nik' => 'required|string|max:16|unique:penduduks',
            'nama' => 'required|string|max:255',
            'jenis_kelamin' => 'required|in:L,P',
            'umur' => 'required|integer|min:0',
            'desa_id' => 'required|exists:desas,id',
        ]);

        $penduduk = Penduduk::create($request->only([
            'nik', 'nama', 'jenis_kelamin', 'umur', 'desa_id'
        ]));

        return response()->json([
            'success' => true,
            'message' => 'Data penduduk berhasil ditambahkan',
            'data' => $penduduk
        ], 201);
    }

    public function show($id)
    {
        $penduduk = Penduduk::with('desa')->find($id);

        if (!$penduduk) {
            return response()->json(['success' => false, 'message' => 'Penduduk tidak ditemukan'], 404);
        }

        return response()->json([
            'success' => true,
            'message' => 'Data penduduk ditemukan',
            'data' => [
                'id' => $penduduk->id,
                'nik' => $penduduk->nik,
                'nama' => $penduduk->nama,
                'jenis_kelamin' => $penduduk->jenis_kelamin,
                'umur' => $penduduk->umur,
                'desa_id' => $penduduk->desa_id,
                'nama_desa' => $penduduk->desa->nama_desa ?? null,
                'created_at' => $penduduk->created_at,
                'updated_at' => $penduduk->updated_at,
            ]
        ]);
    }

    public function update(Request $request, $id)
    {
        $penduduk = Penduduk::find($id);

        if (!$penduduk) {
            return response()->json(['success' => false, 'message' => 'Penduduk tidak ditemukan'], 404);
        }

        $request->validate([
            'nik' => 'required|string|max:16|unique:penduduks,nik,' . $id,
            'nama' => 'required|string|max:255',
            'jenis_kelamin' => 'required|in:L,P',
            'umur' => 'required|integer|min:0',
            'desa_id' => 'required|exists:desas,id',
        ]);

        $penduduk->update($request->only([
            'nik', 'nama', 'jenis_kelamin', 'umur', 'desa_id'
        ]));

        return response()->json([
            'success' => true,
            'message' => 'Data penduduk berhasil diperbarui',
            'data' => $penduduk
        ]);
    }

    public function destroy($id)
    {
        $penduduk = Penduduk::find($id);

        if (!$penduduk) {
            return response()->json(['success' => false, 'message' => 'Penduduk tidak ditemukan'], 404);
        }

        $penduduk->delete();

        return response()->json([
            'success' => true,
            'message' => 'Data penduduk berhasil dihapus'
        ]);
    }
}
