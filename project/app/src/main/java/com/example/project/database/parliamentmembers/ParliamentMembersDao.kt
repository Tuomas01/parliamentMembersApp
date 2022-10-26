package com.example.project.database.parliamentmembers

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/*
Data Access Object for the database.
Functions for interacting with the database will be called from here.
*/
@Dao
interface ParliamentMembersDao {

    /*Gets every Parliament member from the database with Flow to sync it better.
    Flow is converted into livedata (.asLiveData()) later when this function is called
    */
    @Query("SELECT * FROM pm_database")
    fun getAll(): Flow<List<ParliamentMembers>>

    //Gets a pm with a specified id
    @Query("SELECT * FROM pm_database WHERE hetekaId = :id")
    fun getPmById(id: Int): Flow<ParliamentMembers>

    //Gets all the pms that have been added to favourites
    @Query("SELECT * FROM pm_database WHERE favourites_count = 1")
    fun getFavourites(): Flow<List<ParliamentMembers>>

    //Updates some of the fields for the specified pm
    @Update
    suspend fun updatePm(pm: ParliamentMembers)

    //Adds a new pm into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPm(pm: ParliamentMembers)

    //Delete pm from the database (not used in this project currently, except for testing)
    @Delete
    suspend fun deletePm(pm: ParliamentMembers)
}