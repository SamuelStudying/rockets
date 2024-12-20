package com.example.icb0007_uf1_pr01_samuelmateostovar.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.icb0007_uf1_pr01_samuelmateostovar.R
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.RocketRepository
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote.Dimension
import com.example.icb0007_uf1_pr01_samuelmateostovar.models.RocketUi
import com.example.icb0007_uf1_pr01_samuelmateostovar.viewmodel.MainViewModel
import com.example.icb0007_uf1_pr01_samuelmateostovar.viewmodel.MainViewModelFactory
import java.util.UUID

class CreateRocketFragment : Fragment() {

    private val repository by lazy { RocketRepository(requireContext()) }
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_rocket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSave = view.findViewById<Button>(R.id.btn_createRocket)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)

        btnSave.setOnClickListener {
            val name = view.findViewById<EditText>(R.id.et_rocketName).text.toString()
            val company = view.findViewById<EditText>(R.id.et_rocketCompany).text.toString()
            val country = view.findViewById<EditText>(R.id.et_rocketCountry).text.toString()

            if (name.isBlank() || company.isBlank() || country.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Los campos Nombre, Empresa y País son obligatorios.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val rocket = RocketUi(
                id = UUID.randomUUID().toString(),
                name = name,
                type = view.findViewById<EditText>(R.id.et_rocketType).text.toString(),
                active = view.findViewById<EditText>(R.id.et_rocketActive).text.toString().toBooleanStrictOrNull() ?: false,
                costPerLaunch = view.findViewById<EditText>(R.id.et_rocketCostPerLaunch).text.toString().toLongOrNull() ?: 0,
                successRatePct = view.findViewById<EditText>(R.id.et_rocketSuccessRate).text.toString().toIntOrNull() ?: 0,
                country = country,
                company = company,
                wikipedia = view.findViewById<EditText>(R.id.et_rocketWikipedia).text.toString(),
                description = view.findViewById<EditText>(R.id.et_rocketDescription).text.toString(),
                height = Dimension(
                    meters = view.findViewById<EditText>(R.id.et_heightMeters).text.toString().toDoubleOrNull() ?: 0.0,
                    feet = view.findViewById<EditText>(R.id.et_heightFeet).text.toString().toDoubleOrNull() ?: 0.0
                ),
                diameter = Dimension(
                    meters = view.findViewById<EditText>(R.id.et_diameterMeters).text.toString().toDoubleOrNull() ?: 0.0,
                    feet = view.findViewById<EditText>(R.id.et_diameterFeet).text.toString().toDoubleOrNull() ?: 0.0
                ),
                isLocal = true
            )

            viewModel.addRocket(rocket)
            findNavController().popBackStack()
        }

        btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}