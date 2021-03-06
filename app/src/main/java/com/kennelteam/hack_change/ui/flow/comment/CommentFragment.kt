package com.kennelteam.hack_change.ui.flow.comment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kennelteam.hack_change.databinding.FragmentCommentBinding

class CommentFragment : Fragment() {

    private lateinit var commentViewModel: CommentViewModel
    private var _binding: FragmentCommentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        commentViewModel = ViewModelProvider(this)[CommentViewModel::class.java]

        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}