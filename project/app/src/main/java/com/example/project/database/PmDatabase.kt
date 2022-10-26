package com.example.project.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project.MyApp
import com.example.project.database.parliamentmembers.ParliamentMembers
import com.example.project.database.parliamentmembers.ParliamentMembersDao

//Class where the database is created
@Database(entities = [ParliamentMembers::class], version = 2, exportSchema = false)
abstract class PmDatabase: RoomDatabase() {
    //Gets the DAO
    abstract fun parliamentMembersDao() : ParliamentMembersDao
    companion object {
        @Volatile
        private var INSTANCE: PmDatabase? = null
        //Builds the database
        fun getDatabase(): PmDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(MyApp.appContext,
                    PmDatabase::class.java, "pm_database")
                            .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}