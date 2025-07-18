<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Desa extends Model
{
    protected $guarded = ["id"];
    protected $table = "desas";

    public function kecamatan()
    {
        return $this->belongsTo(Kecamatan::class);
    }

    public function penduduks()
    {
        return $this->hasMany(Penduduk::class);
    }
}
