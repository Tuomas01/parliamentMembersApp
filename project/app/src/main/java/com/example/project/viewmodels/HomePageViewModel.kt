package com.example.project.viewmodels

import androidx.lifecycle.*
import androidx.lifecycle.asLiveData
import com.example.project.database.PmDatabase
import com.example.project.database.parliamentmembers.ParliamentMembers
import com.example.project.network.PmApiService
import kotlinx.coroutines.launch

/*
ViewModel for HomePage fragment
Adding the pms to the database happens here through looping the list that contains the objects gotten from the website
Execute the function that adds the pms to the database in the init block
*/
class HomePageViewModel() : ViewModel() {
    private var readAllData: LiveData<List<ParliamentMembers>>
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status
    private val pmsDao = PmDatabase.getDatabase().parliamentMembersDao()

    init {
        readAllData = pmsDao.getAll().asLiveData()
        getPmInfo()
    }

    private fun getPmInfo() {
        viewModelScope.launch {
            try {
                val listResult = PmApiService.PmApi.retrofitService.getPmInfo()
                for (i in 0..listResult.size) {
                    if (i < listResult.size) {
                        pmsDao.addPm(listResult[i])
                    }
                }
                _status.value =
                    "Success: ${listResult.size} Pms retrieved and added to the database"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}