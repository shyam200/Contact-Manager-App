package com.example.contactmanagerapp.room

import androidx.lifecycle.LiveData
import androidx.room.*

//NOTE:- We have choice to either annotate the fun with keyword like @Insert this will insert the data and either to create custom query to do the same
// suspend fun are those function that are used to run long running operation in the background thread

@Dao
interface UserDAO {

    @Insert
    suspend fun insertUser(user: User) : Long

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user")
    suspend fun  deleteAllUser()

    //we didn't use here suspend because this will not time consuming operation and made this to return LiveData since this needs to be observable type to listen it
    @Query("SELECT * FROM user")
    fun getAllUsersInDB() : LiveData<List<User>>
}