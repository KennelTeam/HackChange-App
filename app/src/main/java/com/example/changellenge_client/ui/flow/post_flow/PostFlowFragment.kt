package com.example.changellenge_client.ui.flow.post_flow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.changellenge_client.R
import com.example.changellenge_client.databinding.FragmentPostBinding
import com.example.changellenge_client.databinding.FragmentPostFlowBinding
import com.example.changellenge_client.ui.flow.Post
import com.example.changellenge_client.ui.flow.post.PostViewModel

class PostFlowFragment : Fragment() {

    private lateinit var postFlowViewModel: PostFlowViewModel
    private var _binding: FragmentPostFlowBinding? = null

    private lateinit var postList: ArrayList<Post>

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postFlowViewModel = ViewModelProvider(this)[PostFlowViewModel::class.java]

        _binding = FragmentPostFlowBinding.inflate(inflater, container, false)

        val root: View = binding.root

        loadPosts()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPosts() {
        for (i in 0..15) {
            postList.add(Post(0, "", ""))
        }
    }

}
