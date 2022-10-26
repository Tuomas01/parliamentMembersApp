package com.example.project.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.project.database.parliamentmembers.ParliamentMembers
import com.example.project.database.parliamentmembers.ParliamentMembersDao

/*
ViewModel for PmList fragment
Nothing much happens here, PmList fragment just uses this viewModel to observe all the pms in the database
*/
class PmListViewModel(private val pmDao: ParliamentMembersDao) : ViewModel() {
    val allPms: LiveData<List<ParliamentMembers>> = pmDao.getAll().asLiveData()
    val favouritePms: LiveData<List<ParliamentMembers>> = pmDao.getFavourites().asLiveData()
}

class PmListViewModelFactory(private val pmDao: ParliamentMembersDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PmListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PmListViewModel(pmDao) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}