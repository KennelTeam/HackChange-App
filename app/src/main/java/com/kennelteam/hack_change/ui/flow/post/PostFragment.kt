package com.kennelteam.hack_change.ui.flow.post

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.databinding.FragmentPostBinding
import com.kennelteam.hack_change.ui.flow.Comment
import com.kennelteam.hack_change.ui.flow.comment.CommentListViewAdapter

class PostFragment : Fragment() {

    private lateinit var postViewModel: PostViewModel
    private var _binding: FragmentPostBinding? = null

    private var commentList = ArrayList<Comment>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadComments()

        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        _binding = FragmentPostBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val commentsListViewAdapter = context?.let { CommentListViewAdapter(commentList, it) }

        binding.commentsListView.adapter = commentsListViewAdapter

        binding.commentsListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, itemIndex, _->
            this.findNavController().navigate(R.id.action_postFlowFragment_to_postFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadComments() {
        for (i in 0..10) {
            commentList.add(
                Comment(
                i,
                "UserName $i",
                "comment text $i comment text $i comment text $i comment text $i comment text $i comment text $i comment text $i comment text $i comment text $i " +
                        "comment text $i comment text $i comment text $i comment text $i comment text $i comment text $i comment text $i comment text $i comment text $i "
            ))
        }
    }
}
