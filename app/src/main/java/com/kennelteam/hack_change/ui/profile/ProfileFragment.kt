package com.kennelteam.hack_change.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.AccessTokenManager
import com.kennelteam.hack_change.Networker
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.Variables
import com.kennelteam.hack_change.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val nickname: TextView = binding.textNickname
        nickname.setText("loading....")

        val profileImage: ImageView = binding.imageView

        val status_text = binding.textStatus
        status_text.setText(Variables.status)

        val edit_button: Button = binding.buttonEditProfile
        edit_button.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_edit_profile)
        }

        binding.logout.setOnClickListener {
            AccessTokenManager.clear()
            this.findNavController().navigate(R.id.action_profile_logout)
        }

        Networker.getProfile(AccessTokenManager.get_id(),
            {user, postIds -> binding.textNickname.setText(user.nickname) },
            { Log.i("Test!!! - error", it.error_desc)})

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}