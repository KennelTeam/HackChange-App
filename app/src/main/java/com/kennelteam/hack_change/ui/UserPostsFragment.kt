package com.kennelteam.hack_change.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.kennelteam.hack_change.OnSwipeTouchListener
import com.kennelteam.hack_change.R
import java.lang.Exception


class UserPostsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_user_posts, container, false)
        root.setOnTouchListener(object: OnSwipeTouchListener(context) {
            override fun onSwipeBottom() {
                Log.i("TEST!!!", "olala")
                try {
                    val controller = Navigation.findNavController(root)
                    controller.popBackStack()
                } catch(e: Exception) {
                    Log.i("TEST!!!", "$e")
                }
            }
        })

        return root
    }
}