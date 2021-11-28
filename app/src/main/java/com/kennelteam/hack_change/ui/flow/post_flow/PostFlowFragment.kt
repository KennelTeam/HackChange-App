package com.kennelteam.hack_change.ui.flow.post_flow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.Networker
import com.kennelteam.hack_change.databinding.FragmentPostFlowBinding
import com.kennelteam.hack_change.ui.flow.Post
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.ui.flow.companies.PrePostFlowViewModel
import com.kennelteam.hack_change.MainActivity

class PostFlowFragment : Fragment() {

    private val prevView: PrePostFlowViewModel by activityViewModels()
    private val postFlowViewModel: PostFlowViewModel by activityViewModels()
    private var _binding: FragmentPostFlowBinding? = null

    private var postList = ArrayList<Post>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        
        _binding = FragmentPostFlowBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.addPostButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_navigation_flow_to_create)
        }
        loadPosts()
        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle("Обсуждение")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPosts() {
        postList.clear()
        /*val selectedTopic = prevView.selectedTopic.value!!
        Networker.postsByTopic(selectedTopic, {
            postList.addAll(it.map { el -> Post(el.post_id, el.topic.title, el.author.nickname, el.text) })


        }, {Log.i("Test!!! - error", it.error_desc)})*/
        Log.i("Test!!! - qqqqq", prevView.postsToShow.value.toString())
        postList.addAll(prevView.postsToShow.value!!.map { Post(it.post_id, it.topic.title, it.author.nickname, it.text) })
        val postListViewAdapter = context?.let { PostListViewAdapter(postList, it) }
        Log.i("Test!!! - qqqqqq", postListViewAdapter.toString())
        binding.postsListView.adapter = postListViewAdapter
        Log.i("Test!!! - qqqqqq", "adapter")
        binding.postsListView.dividerHeight = 50

        Log.i("Test!!! - qqqqqq", "setting listeners")
        binding.postsListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, itemIndex, _->
            this.postFlowViewModel.selectedPost = MutableLiveData(postList[itemIndex].id)
            this.findNavController().navigate(R.id.action_postFlowFragment_to_postFragment)
        }
        Log.i("Test!!! - qqqqqq", "finishing")
    }

}
