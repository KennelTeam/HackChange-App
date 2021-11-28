package com.kennelteam.hack_change.ui.setup

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.AccessTokenManager
import com.kennelteam.hack_change.Networker
import com.kennelteam.hack_change.R

class SetupFragment : Fragment() {

    companion object {
        fun newInstance() = SetupFragment()
    }

    private lateinit var viewModel: SetupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        AccessTokenManager.setup(requireActivity())
        Networker.setup(requireContext())

        if (AccessTokenManager.hasToken()) {
            this.findNavController().navigate(R.id.setup_to_flow)
        } else {
            this.findNavController().navigate(R.id.setup_to_login)
        }

        return inflater.inflate(R.layout.setup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetupViewModel::class.java)
        // TODO: Use the ViewModel
    }

}