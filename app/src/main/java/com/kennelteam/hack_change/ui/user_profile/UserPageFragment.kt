package com.kennelteam.hack_change.ui.user_profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.*
import com.kennelteam.hack_change.databinding.FragmentUserPageBinding
import com.kennelteam.hack_change.ui.flow.companies.PrePostFlowViewModel
import com.kennelteam.hack_change.ui.flow.post.PostFragment
import com.kennelteam.hack_change.ui.flow.post.PostViewModel
import java.lang.Exception

class UserPageFragment : Fragment() {
    private val prevView: PostViewModel by activityViewModels()
    private val prePostViewModel: PrePostFlowViewModel by activityViewModels()
    private var _binding: FragmentUserPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var isSubscribed: Boolean = false
    private lateinit var info: UserInfo

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        updateData()

        binding.subscribeButton.setOnClickListener {
            if (isSubscribed) {
                Networker.unsubscribe(info.user_id, {isSubscribed = false; updateData()
                                                    binding.subscribeButton.setText("Подписаться")},
                    {Log.i("Test!!! - error", it.error_desc)})
            } else {
                Networker.subscribe(info.user_id, {isSubscribed = true; updateData()
                    binding.subscribeButton.setText("Отписаться")},
                    {Log.i("Test!!! - error", it.error_desc)})
            }
        }

        root.setOnTouchListener(object: OnSwipeTouchListener(context) {
            override fun onSwipeTop() {
                try {
                Navigation.findNavController(root)
                    .navigate(R.id.action_userPageFragment_to_navigation_user_posts)
                } catch(e: Exception) {
                    Log.i("TEST!!!", "$e")
                }
            }
        })

        return root
    }

    private fun updateData() {
        Networker.getProfile(prevView.data.value!!, { profile: ProfileInfo ->
            info = profile.info
            binding.mySubscribers.text = profile.subscribers_count.toString()
            binding.userNicknameLabel.setText(profile.info.nickname)
            isSubscribed = profile.am_i_subscribed
            prePostViewModel.postsToShow.value!!.clear()
            profile.posts.forEach { el ->
                Networker.getPost(el,
                    {prePostViewModel.postsToShow.value!!.add(it)},
                    {Log.i("Test!!! - error", it.error_desc)}
                )
            }
        }, {Log.i("Test!!! - error", it.error_desc)})
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}