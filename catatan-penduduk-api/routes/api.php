<?php

use App\Http\Controllers\Api\DashboardController;
use App\Http\Controllers\Api\DesaController;
use App\Http\Controllers\Api\KecamatanController;
use App\Http\Controllers\Api\PendudukController;
use App\Http\Controllers\Api\UserController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

Route::post('/register', [UserController::class, 'register']);
Route::post('/login', [UserController::class, 'login']);

Route::middleware('auth:sanctum')->group(function () {
    Route::post('/logout', [UserController::class, 'logout']);
    Route::get('/user', function (Request $request) {
        return $request->user();
    });
    // Route Dashboard
    Route::get("/dashboard",[DashboardController::class, 'index']);
    // Route Kecamatan
    Route::get('/kecamatans', [KecamatanController::class, 'index']);        
    Route::post('/kecamatans', [KecamatanController::class, 'store']);       
    Route::get('/kecamatans/{id}', [KecamatanController::class, 'show']);   
    Route::post('/kecamatans/{id}', [KecamatanController::class, 'update']);  
    Route::delete('/kecamatans/{id}', [KecamatanController::class, 'destroy']); 
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
});

