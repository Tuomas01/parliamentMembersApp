package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.project.adapter.MyFavouritesAdapter
import com.example.project.database.PmDatabase
import com.example.project.databinding.FragmentMyFavouritesBinding
import com.example.project.viewmodels.PmListViewModel
import com.example.project.viewmodels.PmListViewModelFactory

//MyFavourites fragment, shows all favourited pms
class MyFavourites : Fragment() {

    private var _binding: FragmentMyFavouritesBinding? = null
    private val binding get() = _binding!!
    private val pmListViewModel: PmListViewModel by activityViewModels {
        PmListViewModelFactory(
            PmDatabase.getDatabase().parliamentMembersDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*
    Defines the adapter that the recyclerview will use
    Adds navigation between fragments when clicking the recyclerview items take pmName and hetekaId as arguments.
    hetekaId argument is used in the SelectedPm fragment to observe using the id.
    */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MyFavouritesAdapter {
            val action = MyFavouritesDirections.actionMyFavouritesToSelectedPm(
                pmName = (it.firstname + " " + it.lastname),
                hetekaId = it.hetekaId
            )
            this.findNavController().navigate(action)
        }
        binding.recyclerViewForFavourites.adapter = adapter
        //Observes PmListViewModel to get all the favourite pms and submit them to the adapter as a list
        pmListViewModel.favouritePms.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}