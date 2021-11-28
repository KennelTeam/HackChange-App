package com.kennelteam.hack_change.ui.flow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kennelteam.hack_change.databinding.FragmentToolsBinding
import android.widget.ArrayAdapter
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //flowViewModel = ViewModelProvider(this)[FlowViewModel::class.java]

        _binding = FragmentToolsBinding.inflate(inflater, container, false)

        val root: View = binding.root

        var toolsList = emptyArray<String>(
//            "Акции", "Облигации", "Валюта", "Деривативы", "Драгоценные металлы", "Фонды"
        )

        Networker.getAllInstruments({
            Log.i("Test!!! - success", "nice")
            it.forEach {el -> Log.i("Test!!! - success", el.name) }

            toolsList = it.map { el -> el.name }.toTypedArray()

            val toolsAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, toolsList) }

            binding.toolsListView.adapter = toolsAdapter

            binding.toolsListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, itemIndex, _->
                Log.i("aaa", it[itemIndex].name)
                toolsViewModel.selectedInstrument = MutableLiveData(it[itemIndex].instrument_id)
                this.findNavController().navigate(R.id.action_navigation_flow_to_companiesFragment)
            }
        }, {Log.i("Test!!! - error", it.error_desc)})



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
