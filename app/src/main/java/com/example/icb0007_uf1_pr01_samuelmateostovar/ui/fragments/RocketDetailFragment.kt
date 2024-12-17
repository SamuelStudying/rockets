package com.example.icb0007_uf1_pr01_samuelmateostovar.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.icb0007_uf1_pr01_samuelmateostovar.R
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.RocketRepository
import com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote.Dimension
import com.example.icb0007_uf1_pr01_samuelmateostovar.models.RocketUi
import com.example.icb0007_uf1_pr01_samuelmateostovar.viewmodel.MainViewModel
import com.example.icb0007_uf1_pr01_samuelmateostovar.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RocketDetailFragment : Fragment() {

    private val repository by lazy { RocketRepository(requireContext()) }
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(repository) }

    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rocket_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rocket = arguments?.let { RocketDetailFragmentArgs.fromBundle(requireArguments()).selectedRocket }

        val etLayoutContainer = view.findViewById<ViewGroup>(R.id.ly_etContainer)
        val btnMod = view.findViewById<Button>(R.id.btn_editRocket)
        val btnDel = view.findViewById<Button>(R.id.btn_deleteRocket)

        showingData(rocket)
        setupWikiLink(rocket)
        setupMapLink(rocket)

        configureButtons(rocket, btnMod, etLayoutContainer, btnDel)
    }

    private fun showingData(rocket: RocketUi?) {

        if (rocket == null) {
            Toast.makeText(requireContext(), "¡UPS! Parece que este cohete anda en el taller", Toast.LENGTH_LONG).show()
            return
        }

       setHintToEditText(R.id.et_rocketName, rocket.name)
       setHintToEditText(R.id.et_rocketType, rocket.type)
       setHintToEditText(R.id.et_rocketActive, rocket.active.toString())
       setHintToEditText(R.id.et_rocketCountry, rocket.country)
       setHintToEditText(R.id.et_rocketCompany, rocket.company)
       setHintToEditText(R.id.et_rocketWikipedia, rocket.wikipedia)
       setHintToEditText(R.id.et_rocketCostPerLaunch, rocket.costPerLaunch.toString())
       setHintToEditText(R.id.et_rocketSuccessRate, rocket.successRatePct.toString())
       setHintToEditText(R.id.tv_rocketHeight, rocket.height.toString())
       setHintToEditText(R.id.tv_rocketDiameter, rocket.diameter.toString())
       setHintToEditText(R.id.et_rocketDescription, rocket.description)

    }

    private fun setHintToEditText(id: Int, hint: String) {
        view?.findViewById<EditText>(id)?.hint = hint
    }

    private fun configureButtons(rocket: RocketUi?, btnMod: Button, etLayoutContainer: ViewGroup, btnDel: Button) {
        if (rocket?.isLocal == true) {
            btnMod.visibility = View.VISIBLE
            btnDel.visibility = View.VISIBLE

            setupButtonModify(etLayoutContainer, btnMod, rocket)

        } else {
            btnMod.visibility = View.GONE
            btnDel.visibility = View.GONE
        }
        setupButtonDelete(rocket, btnDel)
    }

    private fun setupButtonModify(editTexts: ViewGroup, btnMod: Button, rocket: RocketUi?) {
        btnMod.setOnClickListener {
            isEditing = !isEditing

            toggleEditText(editTexts, isEditing)

            if (!isEditing) {
                val udpatedRocket = rocket?.copy(

                )
            }

            btnMod.text = if (isEditing) "Guardar" else "Editar"
        }
    }

    private fun RocketUi.copyFromView(view: View): RocketUi {
        return this.copy(
            name = view.findViewById<EditText>(R.id.et_rocketName)?.text?.takeIf { it.isNotBlank() }?.toString() ?: this.name,
            type = view.findViewById<EditText>(R.id.et_rocketType)?.text?.takeIf { it.isNotBlank() }?.toString() ?: this.type,
            active = view.findViewById<EditText>(R.id.et_rocketActive)?.text?.toString()?.toBooleanStrictOrNull() ?: this.active,
            costPerLaunch = view.findViewById<EditText>(R.id.et_rocketCostPerLaunch)?.text?.toString()?.toLongOrNull() ?: this.costPerLaunch,
            successRatePct = view.findViewById<EditText>(R.id.et_rocketSuccessRate)?.text?.toString()?.toIntOrNull() ?: this.successRatePct,
            country = view.findViewById<EditText>(R.id.et_rocketCountry)?.text?.takeIf { it.isNotBlank() }?.toString() ?: this.country,
            company = view.findViewById<EditText>(R.id.et_rocketCompany)?.text?.takeIf { it.isNotBlank() }?.toString() ?: this.company,
            wikipedia = view.findViewById<EditText>(R.id.et_rocketWikipedia)?.text?.takeIf { it.isNotBlank() }?.toString() ?: this.wikipedia,
            description = view.findViewById<EditText>(R.id.et_rocketDescription)?.text?.takeIf { it.isNotBlank() }?.toString() ?: this.description,
            height = Dimension(
                meters = view.findViewById<EditText>(R.id.et_heightMeters)?.text?.toString()?.toDoubleOrNull() ?: this.height.meters,
                feet = view.findViewById<EditText>(R.id.et_heightFeet)?.text?.toString()?.toDoubleOrNull() ?: this.height.feet
            ),
            diameter = Dimension(
                meters = view.findViewById<EditText>(R.id.et_diameterMeters)?.text?.toString()?.toDoubleOrNull() ?: this.diameter.meters,
                feet = view.findViewById<EditText>(R.id.et_diameterFeet)?.text?.toString()?.toDoubleOrNull() ?: this.diameter.feet
            )
        )
    }

    private fun setupButtonDelete(rocket: RocketUi?, btnDel: Button) {
        btnDel.setOnClickListener {
            rocket?.id?.let { id ->
                viewModel.deleteRocket(id)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.isRocketDeleted.collect { isDeleted ->
                    if (isDeleted) {
                        Toast.makeText(requireContext(), "¡Se ha desguazado el cohete con éxito!", Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                        viewModel.resetRocketDeletedState()
                    } else {
                        Toast.makeText(requireContext(), "Este cohete no se puede desguazar.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun toggleEditText(editTexts: ViewGroup, isEditing: Boolean) {
        for (i in 0 until editTexts.childCount) {
            val child = editTexts.getChildAt(i)

            if (child is EditText) {
                child.isEnabled = isEditing
            }
        }
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