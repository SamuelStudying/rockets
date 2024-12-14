package com.example.icb0007_uf1_pr01_samuelmateostovar.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.icb0007_uf1_pr01_samuelmateostovar.R
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote.Rocket
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
        setupLink(rocket)
    }

    private fun showingData(rocket: RocketUi?) {

        if (rocket == null) {
            Toast.makeText(requireContext(), "¡UPS! Parece que este cohete anda en el taller", Toast.LENGTH_LONG).show()
        }

        view?.findViewById<TextView>(R.id.tv_rocketName)?.text = rocket?.name
        view?.findViewById<TextView>(R.id.tv_rocketType)?.text = rocket?.type
        view?.findViewById<TextView>(R.id.tv_rocketActive)?.text = rocket?.active.toString()
        view?.findViewById<TextView>(R.id.tv_rocketCountry)?.text = rocket?.country
        view?.findViewById<TextView>(R.id.tv_rocketCompany)?.text = rocket?.company
        view?.findViewById<TextView>(R.id.tv_rocketWikipedia)?.text = rocket?.wikipedia
        view?.findViewById<TextView>(R.id.tv_rocketCostPerLaunch)?.text = rocket?.costPerLaunch.toString()
        view?.findViewById<TextView>(R.id.tv_rocketSuccessRate)?.text = rocket?.successRatePct.toString()
        view?.findViewById<TextView>(R.id.tv_rocketHeight)?.text = "Height: " + rocket?.height.toString()
        view?.findViewById<TextView>(R.id.tv_rocketDiameter)?.text = "Diameter: " + rocket?.diameter.toString()
        view?.findViewById<TextView>(R.id.tv_rocketDescription)?.text = rocket?.description

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun setupLink(rocket: RocketUi?) {
        val linkWiki = view?.findViewById<TextView>(R.id.tv_rocketWikipedia)

        linkWiki?.setOnClickListener {
            if (rocket?.wikipedia.isNullOrEmpty()) return@setOnClickListener

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse(rocket?.wikipedia.toString())
            }

            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "No existe ningún navegador disponible en este dispositivo", Toast.LENGTH_LONG).show()
            }
        }
    }
}