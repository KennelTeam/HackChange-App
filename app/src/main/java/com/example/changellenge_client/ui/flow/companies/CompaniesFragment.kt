package com.example.changellenge_client.ui.flow.companies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.changellenge_client.R
import com.example.changellenge_client.databinding.FragmentFlowBinding

class CompaniesFragment : Fragment() {

    private lateinit var companiesViewModel: CompaniesViewModel
    private var _binding: FragmentFlowBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        companiesViewModel = ViewModelProvider(this)[CompaniesViewModel::class.java]

        _binding = FragmentFlowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val companiesList = arrayOf(
            "Google 1", "Google 2", "Google 3", "Google 4", "Google 5",
        )

        val companiesAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, companiesList) }

        binding.toolsListView.adapter = companiesAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}