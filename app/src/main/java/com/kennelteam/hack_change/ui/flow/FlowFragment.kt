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
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.R

class FlowFragment : Fragment() {

    private lateinit var flowViewModel: FlowViewModel
    private var _binding: FragmentFlowBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        flowViewModel = ViewModelProvider(this)[FlowViewModel::class.java]

        _binding = FragmentFlowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val toolsList = arrayOf(
            "Акции", "Облигации", "Валюта", "Деривативы", "Драгоценные металлы", "Фонды"
        )

        val toolsAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, toolsList) }

        binding.toolsListView.adapter = toolsAdapter

        binding.toolsListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, itemIndex, _->
            Log.i("aaa", toolsList[itemIndex])
            this.findNavController().navigate(R.id.action_navigation_flow_to_companiesFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
