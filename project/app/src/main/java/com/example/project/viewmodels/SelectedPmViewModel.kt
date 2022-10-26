package com.example.project.viewmodels

import android.view.View
import android.widget.Button
import androidx.lifecycle.*
import com.example.project.database.PmDatabase
import com.example.project.database.parliamentmembers.ParliamentMembers
import com.example.project.database.parliamentmembers.ParliamentMembersDao
import kotlinx.coroutines.launch

//ViewModel for SelectedPm fragment.
class SelectedPmViewModel(pmsDao: ParliamentMembersDao) : ViewModel() {
    private var readAllData: LiveData<List<ParliamentMembers>>
    private val pmsDao = PmDatabase.getDatabase().parliamentMembersDao()

    init {
        readAllData = pmsDao.getAll().asLiveData()
    }

    /*
    This function is called when the add to favourites button is clicked.
    After clicking the button, disables it so that the user can't press it again and changes the text of the button
    Makes the remove from favourites button visible, so that the user can remove the selected pm from favourites
    */
    fun addFavouriteButton(addFavButton: Button, removeFavButton: Button) {
        addFavButton.isEnabled = false
        addFavButton.text = "Added to favourites"
        removeFavButton.visibility = View.VISIBLE
    }

    //Pretty much the same functionality as addFavouriteButton function but reversed
    fun removeFavouriteButton(removeFavButton: Button, addFavButton: Button) {
        addFavButton.text = "Add to favourites"
        addFavButton.isEnabled = true
        removeFavButton.visibility = View.INVISIBLE
    }

    fun retrievePm(id: Int): LiveData<ParliamentMembers> {
        return pmsDao.getPmById(id).asLiveData()
    }

    private fun updateFavourite(pm: ParliamentMembers) {
        viewModelScope.launch {
            pmsDao.updatePm(pm)
        }
    }

    /*
    Checks if the pm doesn't have any favourites and creates a copy of the pm but changes the favourites value by + 1
    Calls the updateFavourite function which calls the DAO update function to change the favourites of the pm in the database.
    */
    fun addToFavourites(pm: ParliamentMembers) {
        if (pm.favourites == 0) {
            val updatedPm = pm.copy(favourites = pm.favourites + 1)
            updateFavourite(updatedPm)
        }
    }

    //same functionality as addToFavourites function but with removing from favourites
    fun removeFromFavourites(pm: ParliamentMembers) {
        if (pm.favourites > 0) {
            val updatedPm = pm.copy(favourites = pm.favourites - 1)
            updateFavourite(updatedPm)
        }
    }
}

//Factory for the ViewModel
class SelectedPmViewModelFactory(private val pmDao: ParliamentMembersDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectedPmViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SelectedPmViewModel(pmDao) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}