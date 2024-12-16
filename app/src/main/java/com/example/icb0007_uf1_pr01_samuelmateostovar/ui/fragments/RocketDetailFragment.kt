package com.example.icb0007_uf1_pr01_samuelmateostovar.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.icb0007_uf1_pr01_samuelmateostovar.R
import com.example.icb0007_uf1_pr01_samuelmateostovar.models.RocketUi

class RocketDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rocket_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val rocket = arguments?.let { RocketDetailFragmentArgs.fromBundle(requireArguments()).selectedRocket }
        showingData(rocket)
        setupWikiLink(rocket)
        setupMapLink(rocket)
    }

    private fun showingData(rocket: RocketUi?) {

        if (rocket == null) {
            Toast.makeText(requireContext(), "¡UPS! Parece que este cohete anda en el taller", Toast.LENGTH_LONG).show()
        }

        view?.findViewById<EditText>(R.id.et_rocketName)?.hint = rocket?.name
        view?.findViewById<EditText>(R.id.et_rocketType)?.hint = rocket?.type
        view?.findViewById<EditText>(R.id.et_rocketActive)?.hint = rocket?.active.toString()
        view?.findViewById<EditText>(R.id.et_rocketCountry)?.hint = rocket?.country
        view?.findViewById<EditText>(R.id.et_rocketCompany)?.hint = rocket?.company
        view?.findViewById<EditText>(R.id.et_rocketWikipedia)?.hint = rocket?.wikipedia
        view?.findViewById<EditText>(R.id.et_rocketCostPerLaunch)?.hint = rocket?.costPerLaunch.toString()
        view?.findViewById<EditText>(R.id.et_rocketSuccessRate)?.hint = rocket?.successRatePct.toString()
        view?.findViewById<EditText>(R.id.et_rocketHeight)?.hint = "Height: " + rocket?.height.toString()
        view?.findViewById<EditText>(R.id.et_rocketDiameter)?.hint = "Diameter: " + rocket?.diameter.toString()
        view?.findViewById<EditText>(R.id.et_rocketDescription)?.hint = rocket?.description

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun setupWikiLink(rocket: RocketUi?) {
        val tvLinkWiki = view?.findViewById<TextView>(R.id.et_rocketWikipedia)

        tvLinkWiki?.setOnClickListener {

            val url = rocket?.wikipedia

            if (url.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "El enlace no está disponible", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse(url)
            }

            try {
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("RocketDetailFragment", "Error al abrir el enlace", e)
                Toast.makeText(requireContext(), "No existe ningún navegador disponible en este dispositivo", Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun setupMapLink(rocket: RocketUi?) {
        val tvMapWiki = view?.findViewById<TextView>(R.id.et_rocketCountry)

        tvMapWiki?.setOnClickListener {

            val country = rocket?.country

            if (country.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "La ubicación no está disponible", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val location = "geo:0,0?q=${country}"

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse(location)
            }

            try {
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("RocketDetailFragment", "Error al abrir el enlace", e)
                Toast.makeText(requireContext(), "No existe ningún navegador disponible en este dispositivo", Toast.LENGTH_LONG).show()
            }
        }
    }
}