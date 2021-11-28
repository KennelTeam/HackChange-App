package com.kennelteam.hack_change.ui.flow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kennelteam.hack_change.databinding.FragmentFlowBinding
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.Networker
import com.kennelteam.hack_change.R

class FlowFragment : Fragment() {

    private val flowViewModel: FlowViewModel by activityViewModels()
    private var _binding: FragmentFlowBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //flowViewModel = ViewModelProvider(this)[FlowViewModel::class.java]

        _binding = FragmentFlowBinding.inflate(inflater, container, false)

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
                flowViewModel.selectedInstrument = MutableLiveData(it[itemIndex].instrument_id)
                this.findNavController().navigate(R.id.action_navigation_flow_to_companiesFragment)
            }
        }, {Log.i("Test!!! - error", it.error_desc)})



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
