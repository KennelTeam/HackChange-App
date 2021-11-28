package com.kennelteam.hack_change.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kennelteam.hack_change.*
import com.kennelteam.hack_change.databinding.LoginFragmentBinding
import javax.xml.parsers.FactoryConfigurationError

class Login : Fragment() {

    companion object {
        fun newInstance() = Login()
    }
    private var _binding: LoginFragmentBinding? = null
    private lateinit var viewModel: LoginViewModel

    private val binding get() = _binding!!

    private var nickname = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

        binding.nickname.addTextChangedListener {
            nickname = it.toString()
        }

        binding.password.addTextChangedListener {
            password = it.toString()
        }


        binding.loginButton.setOnClickListener {
            if (validateInput()) {
                Networker.login(nickname, password,
                    { id: Int, token: String -> success(LoginInfo(id, token)) }, { fail(it) })
            }
        }

        binding.registerButton.setOnClickListener {
            if(validateInput()) {
                Networker.register(nickname, password,
                    { id: Int, token: String -> success(LoginInfo(id, token)) }, { fail(it) })
            }
        }

        return binding.root
    }

    private fun validateInput(): Boolean {
        if (!checkLogin()) {
            fail(Error(-3, "Fill login field"))
            return false
        }
        if (!checkPassword()) {
            fail(Error(-3, "Fill password field"))
            return false
        }
        return true
    }

    private fun checkLogin(): Boolean {
        return nickname.length > 0
    }

    private fun checkPassword(): Boolean {
        return password.length > 0
    }

    private fun success(info: LoginInfo) {
        val snackbar = Snackbar.make(binding.root, "Successful login", Snackbar.LENGTH_LONG)

        AccessTokenManager.reset(info.access_token, info.user_id)
        Variables.user_id = info.user_id

        snackbar.show()

        this.findNavController().navigate(R.id.login_to_flow)
    }

    private fun fail(error: Error) {
        val snackbar = Snackbar.make(binding.root, error.error_desc, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}