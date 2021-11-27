package com.kennelteam.hack_change.ui.flow.companies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.kennelteam.hack_change.R
import com.kennelteam.hack_change.databinding.FragmentCompaniesBinding
import com.kennelteam.hack_change.ui.flow.Attraction
import com.kennelteam.hack_change.ui.flow.CustomListViewAdapter

class CompaniesFragment : Fragment() {

    private lateinit var companiesViewModel: CompaniesViewModel
    private var _binding: FragmentCompaniesBinding? = null

    private var attractionsByCity = ArrayList<Attraction>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        companiesViewModel = ViewModelProvider(this)[CompaniesViewModel::class.java]

        _binding = FragmentCompaniesBinding.inflate(inflater, container, false)

        val root: View = binding.root

        loadAttractionsData()

        val attractionsAdapter = context?.let { CustomListViewAdapter(attractionsByCity, it) }

        binding.companiesListView.adapter = attractionsAdapter

        binding.companiesListView.dividerHeight = 10

        binding.companiesListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, itemIndex, _->
//            Log.i("aaa", attractionsByCity[itemIndex])
            this.findNavController().navigate(R.id.action_companiesFragment_to_postFlowFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getAttractionsByPlace(place: String): ArrayList<Attraction> {
        val attractions = attractionsByCity
        if (attractions != null) return attractions else return ArrayList<Attraction>()
    }

    fun loadAttractionsData() {
        addSeattleAttractions()
    }

    fun addSeattleAttractions() {
        val attractionsLst = ArrayList<Attraction>()

        var attraction = Attraction()
        attraction.title = "Seattle Space Needle"
        attraction.description = "Travel to the top of the Space Needle's 520 ft. " +
                "observation deck for unparalleled views of downtown Seattle, " +
                "Mt. Rainier, and the Puget Sound,"
        attraction.hours = "10AM - 8PM"
//        attraction.image = R.drawable.space_needle

        attractionsLst.add(attraction)

        attraction = Attraction()
        attraction.title = "Pike Place Market"
        attraction.description = "Pike Place Market is a public market, " +
                "opened August 17, 1907, and one of the oldest continuously " +
                "operated public farmers' markets in the United States."
        attraction.hours = "9AM - 4PM"
//        attraction.image = R.drawable.pike_place_market

        attractionsLst.add(attraction)

        attraction = Attraction()
        attraction.title = "Kerry Park"
        attraction.description = "Kerry Park is a 1.26-acre park on the " +
                "south slope of Queen Anne Hill in Seattle, Washington, " +
                "located at the corner of Second Avenue West and West Highland Drive"
        attraction.hours = "4AM - 11PM"
//        attraction.image = R.drawable.kerry_park

        attractionsLst.add(attraction)

        attraction = Attraction()
        attraction.title = "Mt. Rainier National Park"
        attraction.description = "Mount Rainier is the highest mountain of the Cascade" +
                " Range of the Pacific Northwest, and the highest mountain" +
                " in the U.S. state of Washington."
        attraction.hours = "10AM - 5PM"
//        attraction.image = R.drawable.mountrainier

        attractionsLst.add(attraction)

        attraction = Attraction()
        attraction.title = "The Museum of Flight"
        attraction.description = "The Museum of Flight is a private non-profit air " +
                "and space museum in the northwest United States. " +
                "It is located just south of Seattle."
        attraction.hours = "10AM - 5PM"
//        attraction.image = R.drawable.musium_of_flights

        attractionsLst.add(attraction)

        attraction = Attraction()
        attraction.title = "Snoqualmie Falls"
        attraction.description = "Snoqualmie Falls is one of Washington state's" +
                " most popular scenic attractions. More than 1.5 million" +
                " visitors come to the Falls every year."
        attraction.hours = "4AM - 11PM"
//        attraction.image = R.drawable.snoqualmie_falls

        attractionsLst.add(attraction)

        attractionsByCity = attractionsLst
    }

}

class Attraction {
    var title: String? = null
    var description: String? = null
    var hours: String? = null
    var image: Int = 0
}