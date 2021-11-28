package com.kennelteam.hack_change.ui.edit_profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kennelteam.hack_change.Networker
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.Variables
import com.kennelteam.hack_change.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {

    private lateinit var profileViewModel: EditProfileViewModel
    private var _binding: FragmentEditProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(EditProfileViewModel::class.java)

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val nickname_edit = binding.editNickname
        val status_edit = binding.editStatus

        val edit_button: Button = binding.buttonConfirm
        edit_button.setOnClickListener {
            Log.i("test-logs", "Profile Edited")
            //Variables.nickname = nickname_edit.text.toString()
            //Variables.status = status_edit.text.toString()
            Networker.setMyInfo(nickname = nickname_edit.text.toString(),
                onFail = {Log.i("Test!!! - error", it.error_desc)})
            Navigation.findNavController(root).navigate(R.id.action_profile_edited)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}