package com.kennelteam.hack_change.ui.create_post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.Variables
import com.kennelteam.hack_change.databinding.FragmentCreatePostBinding
import com.kennelteam.hack_change.ui.profile.ProfileViewModel

class CreatePostFragment : Fragment() {

    private var _binding: FragmentCreatePostBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val themeChoice = binding.spinnerThemeChoice
        val textPost = binding.editTextPost

        val completeButton: Button = binding.buttonCompletePost
        completeButton.setOnClickListener {
            Log.i("test-logs", "Post created about: ${themeChoice.selectedItem.toString()} with text: '${textPost.text.toString()}'")
            Navigation.findNavController(root).navigate(R.id.action_end_create_post)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}