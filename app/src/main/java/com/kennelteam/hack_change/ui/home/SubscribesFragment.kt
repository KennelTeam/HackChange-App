package com.kennelteam.hack_change.ui.home

import androidx.lifecycle.ViewModelProvider
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
import com.kennelteam.hack_change.MainActivity
import com.kennelteam.hack_change.Networker
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.databinding.SubscribesFragmentBinding
import com.kennelteam.hack_change.ui.flow.Post
import com.kennelteam.hack_change.ui.flow.companies.CompaniesViewModel
import com.kennelteam.hack_change.ui.flow.post_flow.PostListViewAdapter

class SubscribesFragment : Fragment() {


    private val prevView: CompaniesViewModel by activityViewModels()
    private val postFlowViewModel: SubscribesViewModel by activityViewModels()
    private var _binding: SubscribesFragmentBinding? = null

    private var postList = ArrayList<Post>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadPosts()

        _binding = SubscribesFragmentBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle("Подписки")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPosts() {

        postList.clear()
        Networker.getSubscriptionsPosts( {
            Log.i("test it", "$postList")


            postList.addAll(it.map { el -> Post(el.post_id, el.topic.title, el.author.nickname, el.text) })


            val postListViewAdapter = context?.let { PostListViewAdapter(postList, it) }

            binding.postListView.adapter = postListViewAdapter

//            binding.postListView.dividerHeight = 50

            binding.postListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, itemIndex, _->
//                this.postFlowViewModel.selectedPost = MutableLiveData(postList[itemIndex].id)
                this.findNavController().navigate(R.id.action_postFlowFragment_to_postFragment)
            }

        }, {Log.i("Test!!! - error", it.error_desc)})

    }


}