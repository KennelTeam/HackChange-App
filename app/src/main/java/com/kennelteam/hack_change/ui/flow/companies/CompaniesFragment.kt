package com.kennelteam.hack_change.ui.flow.companies

import android.media.metrics.LogSessionId
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.*
import com.kennelteam.hack_change.databinding.FragmentCompaniesBinding
import com.kennelteam.hack_change.ui.flow.ToolsViewModel
import com.kennelteam.hack_change.ui.flow.Post

class CompaniesFragment : Fragment() {

    private val prevViewModel: ToolsViewModel by activityViewModels()
    private val companiesViewModel: CompaniesViewModel by activityViewModels()
    private var _binding: FragmentCompaniesBinding? = null

    private val binding get() = _binding!!
    private var selectedInstrument: Int = 0

    private lateinit var topics: List<PostTopic>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //companiesViewModel = ViewModelProvider(this)[CompaniesViewModel::class.java]

        _binding = FragmentCompaniesBinding.inflate(inflater, container, false)

        selectedInstrument = prevViewModel.selectedInstrument.value!!
        val root: View = binding.root

        val companiesList = emptyArray<String>(
//            "Google 1", "Google 1", "Google 1", "Google 1", "Google 1", "Google 1"
        )

        Log.i("Test!!! - info", selectedInstrument.toString())

        Networker.getTopicsByInstrument(selectedInstrument, {
            topics = it
            val companiesList = it.map { el -> el.title }
            val attractionsAdapter = context?.let {
                    el -> ArrayAdapter<String>(el, android.R.layout.simple_list_item_1, companiesList)
            }

            binding.companiesListView.adapter = attractionsAdapter

            binding.companiesListView.onItemClickListener = AdapterView.OnItemClickListener {
                    adapterView, view, itemIndex, _->

                this.companiesViewModel.selectedTopic = MutableLiveData(topics[itemIndex].topic_id)
                this.findNavController().navigate(R.id.action_companiesFragment_to_postFlowFragment)
            }
        }, { Log.i("Test!!! - error", it.error_desc)})

        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle("Компании")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
