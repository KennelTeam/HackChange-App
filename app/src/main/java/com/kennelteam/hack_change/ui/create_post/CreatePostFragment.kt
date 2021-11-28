package com.kennelteam.hack_change.ui.create_post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kennelteam.hack_change.databinding.FragmentCreatePostBinding
import com.kennelteam.hack_change.ui.flow.companies.CompaniesViewModel
import com.kennelteam.hack_change.*


class CreatePostFragment : Fragment() {

    private val prevView: CompaniesViewModel by activityViewModels()
    private var _binding: FragmentCreatePostBinding? = null

    private val binding get() = _binding!!

    private val topics: ArrayList<PostTopic> = arrayListOf()
    private val items: ArrayList<String> = arrayListOf()

    private var notLoadedToolsCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Networker.getAllInstruments({
            notLoadedToolsCount = it.size
            it.forEach { el -> Networker.getTopicsByInstrument(el.instrument_id, { tts ->
                topics.addAll(tts)
                items.addAll(tts.map { topic -> topic.title })
                notLoadedToolsCount -= 1
                if (notLoadedToolsCount == 0) {
                    setOptions()
                }
            }, {Log.i("Test!!! - error", it.error_desc)}) }
        }, {Log.i("Test!!! - error", it.error_desc)})

        val themeChoice = binding.spinnerThemeChoice

        val textPost = binding.editTextPost

        val completeButton: Button = binding.buttonCompletePost
        completeButton.setOnClickListener {
            var topicId = -1
            topics.forEach {
                if (it.title == themeChoice.selectedItem.toString()) {
                    topicId = it.topic_id
                }
            }
            if (topicId != -1) {
                Log.i("test-logs", "Post created about: ${themeChoice.selectedItem.toString()} with text: '${textPost.text.toString()}'")
                Networker.addPost(topicId, textPost.text.toString(),
                    {Log.i("Test!!! - error", it.error_desc)})

                Navigation.findNavController(root).navigate(R.id.action_end_create_post)
            }
        }

        return root
    }

    private fun setOptions() {
        Log.i("Test!!! - t", topics.toString())

        items.addAll(topics.map { topic -> topic.title })
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireActivity(),
            android.R.layout.simple_spinner_dropdown_item, items)
        Log.i("Test!!! - test", items.toString())
        binding.spinnerThemeChoice.adapter = spinnerArrayAdapter

        var indexOfSelection = 0
        for (i in 0..topics.size) {
            if (topics[i].topic_id == prevView.selectedTopic.value) {
                indexOfSelection = i
                break
            }
        }

        binding.spinnerThemeChoice.setSelection(indexOfSelection)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}