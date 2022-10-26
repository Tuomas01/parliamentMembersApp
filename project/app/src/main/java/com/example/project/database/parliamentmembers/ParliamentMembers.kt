package com.example.project.database.parliamentmembers

import androidx.annotation.NonNull
import androidx.room.*

//Defines the class as a database entity and gives it a name "pm_database".
@Entity(tableName = "pm_database")

/*Creates the database columns.
Fields are not meant to be null, so @NonNull is called before defining the columns.
*/
data class ParliamentMembers(
    @PrimaryKey(autoGenerate = true) val hetekaId: Int,
    @NonNull @ColumnInfo(name = "seat_number") val seatNumber: Int,
    @NonNull @ColumnInfo(name = "last_name") val lastname: String,
    @NonNull @ColumnInfo(name = "first_name") val firstname: String,
    @NonNull @ColumnInfo(name = "party") val party: String,
    @NonNull @ColumnInfo(name = "minister") val minister: Boolean,
    @NonNull @ColumnInfo(name = "picture_url") val pictureUrl: String,
    @NonNull @ColumnInfo(name = "favourites_count") val favourites: Int = 0
)