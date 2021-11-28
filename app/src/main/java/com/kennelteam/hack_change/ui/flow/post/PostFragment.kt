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

import java.util.Timer
import kotlin.concurrent.schedule


class PostFragment : Fragment() {
    private val prevView: PostFlowViewModel by activityViewModels()
    private val postView: PostViewModel by activityViewModels()
    private lateinit var postViewModel: PostViewModel
    private var _binding: FragmentPostBinding? = null

    private var commentList = ArrayList<Comment>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setMainText()
        loadComments()
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val user_name = binding.userId
        user_name.setOnClickListener {
            Networker.getPost(prevView.selectedPost.value!!, {
                postView.data.value = it.author.user_id
                this.findNavController().navigate(
                    R.id.action_navigation_post_to_user
                )
            }, { Log.i("Test!!! - error", it.error_desc) })
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setMainText() {
        Networker.getPost(prevView.selectedPost.value!!, {
            binding.text.setText(it.text)
            binding.userId.setText(it.author.nickname)
            binding.topicPost.setText(it.topic.title)
        }, { Log.i("Test!!! - error", it.error_desc) })
    }
    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle("Пост")
    }

    private fun loadComments() {
        commentList.clear()
        Networker.getCommentsByPost(prevView.selectedPost.value!!, {
            commentList.addAll(it.map { el -> Comment(el.comment_id, el.commenter.nickname, el.text) })

            val commentsListViewAdapter = context?.let { CommentListViewAdapter(commentList, it) }

            binding.commentsListView.adapter = commentsListViewAdapter

            binding.commentsListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, itemIndex, _->
                this.findNavController().navigate(R.id.action_navigation_post_to_user)
            }

            binding.sendCommentButton.setOnClickListener {
                Log.i("test", "send comment: ${binding.commentText.text}")

                Networker.addComment(prevView.selectedPost.value!!, binding.commentText.text.toString(),
                    { Log.i("Test!!! - error", it.error_desc)})

                binding.commentText.text.clear()
                Timer("update_comments", false).schedule(1000) {
                    loadComments()
                }

            }
       }, { Log.i("Test!!! - error", it.error_desc) })
    }
}
