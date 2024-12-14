package com.example.icb0007_uf1_pr01_samuelmateostovar.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.icb0007_uf1_pr01_samuelmateostovar.R

class LoginFragment : Fragment() {
    private var toast : Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLoginButton()
    }

    private fun setupLoginButton() {
        val btnLogin = view?.findViewById<Button>(R.id.bt_login)

        btnLogin?.setOnClickListener {
            validateCredentials()
        }
    }

    private fun validateCredentials() {
        val etUserName = view?.findViewById<EditText>(R.id.et_userName)
        val etPassword = view?.findViewById<EditText>(R.id.et_password)

        val userName = etUserName?.text.toString()
        val password = etPassword?.text.toString()

        toast?.cancel()
        toast = Toast.makeText(context, "", Toast.LENGTH_LONG)

        if (userName == "admin" && password == "admin1234") {
            toast?.setText("Bienvenido '$userName' ¡Cargando motores!")

            val loginAction = LoginFragmentDirections.actionLoginFragmentToRocketListFragment(userName)
            findNavController().navigate(loginAction)
        } else {
            etUserName?.text?.clear()
            etPassword?.text?.clear()
            toast?.setText("Vaya.. Parece que aún no tienes licencia\n" +
                    "Vuelve a intentarlo")
        }

        toast?.show()
    }
}