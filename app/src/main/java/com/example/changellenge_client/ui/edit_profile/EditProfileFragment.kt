package com.example.changellenge_client.ui.edit_profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.changellenge_client.R
import com.example.changellenge_client.databinding.FragmentEditProfileBinding

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
//        profileViewModel =
//            ViewModelProvider(this).get(EditProfileViewModel::class.java)

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val status_edit = binding.editStatus

        val edit_button: Button = binding.buttonConfirm
        edit_button.setOnClickListener {
            Log.i("test-logs", "Profile Edited")
            Navigation.findNavController(root).navigate(R.id.action_profile_edited)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}