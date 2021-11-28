package com.kennelteam.hack_change.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kennelteam.hack_change.*
import com.kennelteam.hack_change.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {

    private val postFlowViewModel: RegisterViewModel by activityViewModels()
    private var _binding: RegisterFragmentBinding? = null

    private val binding get() = _binding!!

    private var nickname = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = RegisterFragmentBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.registerNickname.addTextChangedListener {
            nickname = it.toString()
        }

        binding.registerPassword.addTextChangedListener {
            password = it.toString()
        }

        binding.registerButton.setOnClickListener {
            if(validateInput()) {
                Networker.register(nickname, password,
                    { id: Int, token: String -> success(LoginInfo(id, token)) }, { fail(it) })
            }
        }

        binding.switchToLoginButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_registerFragment_to_login)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle("Регистрация")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun validateInput(): Boolean {
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

    fun success(info: LoginInfo) {
        val snackbar = Snackbar.make(binding.root, "Successful login", Snackbar.LENGTH_LONG)

        AccessTokenManager.reset(info.access_token, info.user_id)
        Variables.user_id = info.user_id

        snackbar.show()

        this.findNavController().navigate(R.id.reg_to_flow)
    }

    private fun fail(error: Error) {
        val snackbar = Snackbar.make(binding.root, error.error_desc, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        postFlowViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
