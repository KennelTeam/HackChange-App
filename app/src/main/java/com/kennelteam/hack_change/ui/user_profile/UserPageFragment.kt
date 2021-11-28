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
import com.kennelteam.hack_change.Networker
import com.kennelteam.hack_change.OnSwipeTouchListener
import com.kennelteam.hack_change.databinding.FragmentUserPageBinding
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.ui.flow.post.PostFragment
import com.kennelteam.hack_change.ui.flow.post.PostViewModel
import java.lang.Exception

class UserPageFragment : Fragment() {
    private val prevView: PostViewModel by activityViewModels()
    private var _binding: FragmentUserPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var isSubscribed: Boolean = false
    private var name: String = "loading..."

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Networker.getProfile(prevView.data.value!!, { user, posts ->
            name = user.nickname
            binding.userNicknameLabel.setText(user.nickname)
        }, {Log.i("Test!!! - error", it.error_desc)})

        var is_subscribed = true

        val buttonSubscribe = binding.subscribeButton
        buttonSubscribe.setOnClickListener {
            if (is_subscribed) {
                is_subscribed = false
                buttonSubscribe.setText("Подписаться")
            } else {
                is_subscribed = true
                buttonSubscribe.setText("Отписаться")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}