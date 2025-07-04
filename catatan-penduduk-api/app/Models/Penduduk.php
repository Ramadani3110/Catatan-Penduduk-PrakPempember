<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Penduduk extends Model
{
    protected $guarded = ["id"];
    protected $table = "penduduks";

    public function desa()
    {
        return $this->belongsTo(Desa::class);
    }
}
