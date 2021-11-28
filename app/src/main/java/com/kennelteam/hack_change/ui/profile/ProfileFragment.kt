package com.kennelteam.hack_change.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.*
import com.kennelteam.hack_change.databinding.FragmentProfileBinding
import com.kennelteam.hack_change.ui.flow.companies.PrePostFlowViewModel
import java.lang.Exception

class ProfileFragment : Fragment() {
    private val prePostsView: PrePostFlowViewModel by activityViewModels()
    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val nickname: TextView = binding.textNickname
        nickname.setText("loading....")

        val profileImage: ImageView = binding.myAvatar

        val edit_button: Button = binding.buttonEditProfile
        edit_button.setOnClickListener {
            Navigation.findNavController(root).navigate(R.id.action_edit_profile)
        }

        binding.logout.setOnClickListener {
            AccessTokenManager.clear()
            this.findNavController().navigate(R.id.action_profile_logout)
        }

        Networker.getProfile(AccessTokenManager.get_id(),
            {profile: ProfileInfo -> binding.textNickname.setText(profile.info.nickname)
                prePostsView.postsToShow.value = mutableListOf<PostExtended>()
                binding.mySubscribers.setText(profile.subscribers_count.toString())
                Log.i("Test!!! - logs", profile.posts.size.toString())
                profile.posts.forEach { el ->
                    Networker.getPost(el,
                        {post -> prePostsView.postsToShow.value!!.add(post); Log.i("Test!!! - logs", post.text)},
                        {Log.i("Test!!! - error", it.error_desc)}
                    )
                }
            },
            { Log.i("Test!!! - error", it.error_desc)})

        root.setOnTouchListener(object: OnSwipeTouchListener(context) {
            override fun onSwipeTop() {
                try {
                    Navigation.findNavController(root)
                        .navigate(R.id.action_profile_posts)
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