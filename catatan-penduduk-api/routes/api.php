<?php

use App\Http\Controllers\Api\DesaController;
use App\Http\Controllers\Api\KecamatanController;
use App\Http\Controllers\Api\PendudukController;
use App\Http\Controllers\Api\UserController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;


// Route untuk mendapatkan data user (jika menggunakan Sanctum)
Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('/register', [UserController::class, 'register']);
Route::post('/login', [UserController::class, 'login']);

Route::middleware('auth:sanctum')->group(function () {
    Route::post('/logout', [UserController::class, 'logout']);
    Route::get('/user', function (Request $request) {
        return $request->user();
    });
});


// Route manual tanpa resource
Route::get('/kecamatans', [KecamatanController::class, 'index']);        // GET semua kecamatan
Route::post('/kecamatans', [KecamatanController::class, 'store']);       // POST buat kecamatan
Route::get('/kecamatans/{id}', [KecamatanController::class, 'show']);    // GET 1 kecamatan by ID
Route::put('/kecamatans/{id}', [KecamatanController::class, 'update']);  // PUT update kecamatan
Route::delete('/kecamatans/{id}', [KecamatanController::class, 'destroy']); // DELETE kecamatan
// Route Desa
Route::get('/desas', [DesaController::class, 'index']);
Route::post('/desas', [DesaController::class, 'store']);
Route::get('/desas/{id}', [DesaController::class, 'show']);
Route::post('/desas/{id}', [DesaController::class, 'update']);
Route::delete('/desas/{id}', [DesaController::class, 'destroy']);
//Penduduk
Route::get('/penduduks', [PendudukController::class, 'index']);
Route::post('/penduduks', [PendudukController::class, 'store']);
Route::get('/penduduks/{id}', [PendudukController::class, 'show']);
Route::post('/penduduks/{id}', [PendudukController::class, 'update']);
Route::delete('/penduduks/{id}', [PendudukController::class, 'destroy']);