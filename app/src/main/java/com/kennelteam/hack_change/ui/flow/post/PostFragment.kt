package com.kennelteam.hack_change.ui.flow.post

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.MainActivity
import com.kennelteam.hack_change.Networker
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.databinding.FragmentPostBinding
import com.kennelteam.hack_change.ui.flow.Comment
import com.kennelteam.hack_change.ui.flow.comment.CommentListViewAdapter
import com.kennelteam.hack_change.ui.flow.post_flow.PostFlowViewModel

class PostFragment : Fragment() {
    private val prevView: PostFlowViewModel by activityViewModels()
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
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val commentsListViewAdapter = context?.let { CommentListViewAdapter(commentList, it) }

        binding.commentsListView.adapter = commentsListViewAdapter

        binding.commentsListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, itemIndex, _->
            this.findNavController().navigate(R.id.action_postFlowFragment_to_postFragment)
        }


        binding.sendCommentButton.setOnClickListener {
            Log.i("test", "send comment: ${binding.commentText.text}")

            Networker.addComment(prevView.selectedPost.value!!, binding.commentText.text.toString(),
                { Log.i("Test!!! - error", it.error_desc)})

            binding.commentText.text.clear()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle("Пост")
    }

    private fun loadComments() {
        Networker.getCommentsByPost(prevView.selectedPost.value!!, {
            commentList.addAll(it.map { el -> Comment(el.comment_id, el.commenter.nickname, el.text) })
        }, { Log.i("Test!!! - error", it.error_desc) })
    }
}
