package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.project.database.PmDatabase
import com.example.project.database.parliamentmembers.ParliamentMembers
import com.example.project.databinding.FragmentSelectedPmBinding
import com.example.project.viewmodels.SelectedPmViewModel
import com.example.project.viewmodels.SelectedPmViewModelFactory

//SelectedPm fragment that shows information about the pm that was selected in list tab
class SelectedPm : Fragment() {

    companion object {
        val PMNAME = "pmName"
        val HETEKAID = "hetekaId"
    }

    //Use the factory
    private val selectedPmViewModel: SelectedPmViewModel by activityViewModels {
        SelectedPmViewModelFactory(
            PmDatabase.getDatabase().parliamentMembersDao()
        )
    }

    private var _binding: FragmentSelectedPmBinding? = null
    private val binding get() = _binding!!
    private lateinit var pmNameId: String
    var hetekaId: Int = 0
    private lateinit var pm: ParliamentMembers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            pmNameId = it.getString(PMNAME).toString()
            hetekaId = it.getInt(HETEKAID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_selected_pm, container, false)
        return binding.root
    }

    /*
    Gets the selected pm by observing the SelectedPmViewModel and calling the getPmById function from DAO.
    Everytime views are created call the bind function
    Assigns the selected pm into a variable for later use
    */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = hetekaId
        selectedPmViewModel.retrievePm(id).observe(this.viewLifecycleOwner) { selectedPm ->
            pm = selectedPm
            bind(pm)
        }

        /*Gets the add and remove favourites buttons and assigns them to variable
        Add onclick listeners to the buttons, which update the buttons etc... these can be found in the SelectedPmViewModel
        AddFavouriteButton and RemoveFavouriteButton functions
         */
        val addFavouriteButton = binding.favouriteButton
        val removeFavouriteButton = binding.removeFavourite

        binding.favouriteButton.setOnClickListener {
            selectedPmViewModel.addToFavourites(pm)
            selectedPmViewModel.addFavouriteButton(addFavouriteButton, removeFavouriteButton)
        }
        binding.removeFavourite.setOnClickListener {
            selectedPmViewModel.removeFromFavourites(pm)
            selectedPmViewModel.removeFavouriteButton(removeFavouriteButton, addFavouriteButton)
        }
    }

    override fun onPause() {
        super.onPause()
        _binding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    Applies info about the selected pm into the views,
    also if the the pm is in favourites already when opening the page, disable the button, change the text and make
    the remove button visible
    */
    private fun bind(pm: ParliamentMembers) {
        binding.apply {
            selectedPmNameView.text = pm.firstname + " " + pm.lastname
            aboutSelectedPm.text = "${
                pm.firstname + " " + pm.lastname + "'s seatnumber is " +
                        pm.seatNumber + ", they belong to " + pm.party + " party " +
                        if (pm.minister == true) "and they are a minister" else "and they are not a minister"
            }"
            favouritesCount.text = "Favourites: " + "\n" + pm.favourites
            if (pm.favourites > 0) {
                favouriteButton.isEnabled = false
                removeFavourite.visibility = View.VISIBLE
                favouriteButton.text = "Added to favourites"
            }
            selectedPmImg.load("https://avoindata.eduskunta.fi/" + pm.pictureUrl)
        }
    }
}