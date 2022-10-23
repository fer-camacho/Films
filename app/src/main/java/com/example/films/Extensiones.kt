package com.example.films

import android.content.Context
import android.widget.Toast

fun Context.mensajeCorto(texto : String) {
    Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
}