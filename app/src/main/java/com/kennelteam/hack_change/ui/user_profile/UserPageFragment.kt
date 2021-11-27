package com.kennelteam.hack_change.ui.user_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kennelteam.hack_change.databinding.FragmentUserPageBinding
import com.kennelteam.hack_change.R

class UserPageFragment : Fragment() {

    private var _binding: FragmentUserPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}