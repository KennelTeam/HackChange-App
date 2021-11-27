package com.kennelteam.hack_change.ui.flow.companies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.databinding.FragmentCompaniesBinding

class CompaniesFragment : Fragment() {

    private lateinit var companiesViewModel: CompaniesViewModel
    private var _binding: FragmentCompaniesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        companiesViewModel = ViewModelProvider(this)[CompaniesViewModel::class.java]

        _binding = FragmentCompaniesBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val companiesList = arrayOf(
            "Google 1", "Google 1", "Google 1", "Google 1", "Google 1", "Google 1"
        )

        val attractionsAdapter = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, companiesList) }

        binding.companiesListView.adapter = attractionsAdapter

        binding.companiesListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, itemIndex, _->
            this.findNavController().navigate(R.id.action_companiesFragment_to_postFlowFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
