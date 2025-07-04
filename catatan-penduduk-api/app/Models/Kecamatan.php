<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Kecamatan extends Model
{
    protected $guarded = ["id"];
    protected $table = "kecamatans";

    public function desas()
    {
        return $this->hasMany(Desa::class);
    }
}
