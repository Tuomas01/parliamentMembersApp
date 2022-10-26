package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project.adapter.PmListAdapter
import com.example.project.database.PmDatabase
import com.example.project.databinding.FragmentPmListBinding
import com.example.project.viewmodels.PmListViewModel
import com.example.project.viewmodels.PmListViewModelFactory

/*
PmList fragment
Pretty much identical to MyFavourites fragment
*/

class PmListFragment : Fragment() {

    private var _binding: FragmentPmListBinding? = null
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPmListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PmListAdapter {
            val action =
                PmListFragmentDirections.actionPmListFragmentToSelectedPm(
                    pmName = (it.firstname + " " + it.lastname),
                    hetekaId = it.hetekaId
                )
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        pmListViewModel.allPms.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }
        val gridLayout = GridLayoutManager(context, 3)
        binding.recyclerView.layoutManager = gridLayout
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}