package com.example.icb0007_uf1_pr01_samuelmateostovar.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.icb0007_uf1_pr01_samuelmateostovar.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO ELIMINAR
        deleteDatabase("rockets_db")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }
}