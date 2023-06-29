package com.example.contactmanagerapp.room

class UserRepository(private  val userDAO: UserDAO) {

    val users = userDAO.getAllUsersInDB()

    suspend fun insertUser(user: User) : Long {
        return userDAO.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        return userDAO.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        return userDAO.deleteUser(user)
    }

    suspend fun deleteAll(){
        return userDAO.deleteAllUser()
    }
}