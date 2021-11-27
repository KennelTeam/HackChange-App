package com.example.changellenge_client.ui.post

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.changellenge_client.R
import com.example.changellenge_client.databinding.FragmentPostBinding

class PostFragment : Fragment() {

    private lateinit var postViewModel: PostViewModel
    private var _binding: FragmentPostBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.postText
        postViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
