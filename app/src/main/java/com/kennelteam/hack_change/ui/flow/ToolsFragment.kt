package com.kennelteam.hack_change.ui.flow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.kennelteam.hack_change.databinding.FragmentToolsBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.MainActivity
import com.kennelteam.hack_change.Networker
import com.kennelteam.hack_change.R

class ToolsFragment : Fragment() {

    private val toolsViewModel: ToolsViewModel by activityViewModels()
    private var _binding: FragmentToolsBinding? = null

    private val binding get() = _binding!!

    private var toolsList = emptyArray<Tool>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentToolsBinding.inflate(inflater, container, false)

        val root: View = binding.root

        Networker.getAllInstruments({
            Log.i("Test!!! - success", "nice")
            it.forEach {el -> Log.i("Test!!! - success", el.name) }

            toolsList = it.map { el -> Tool(el.name, el.details) }.toTypedArray()

            val toolsAdapter = context?.let { ToolsListViewAdapter(toolsList, it) }

            binding.toolsListView.adapter = toolsAdapter

            binding.toolsListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, itemIndex, _->
                toolsViewModel.selectedInstrument = MutableLiveData(it[itemIndex].instrument_id)
                this.findNavController().navigate(R.id.action_navigation_tools_to_companiesFragment)
            }
        }, {Log.i("Test!!! - error", it.error_desc)})

//        binding.toolsListView.dividerHeight = 50

        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle("Инструменты")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
