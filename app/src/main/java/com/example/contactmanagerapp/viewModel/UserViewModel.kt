package com.example.contactmanagerapp.viewModel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactmanagerapp.room.User
import com.example.contactmanagerapp.room.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository : UserRepository ) :ViewModel(), Observable {

    val users = repository.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete : User

    @Bindable
    val inputName = MutableLiveData<String?>()
    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateBtnText = MutableLiveData<String>()

    @Bindable
    val  deleteOrClearAllBtnText = MutableLiveData<String>()


    init {
        saveOrUpdateBtnText.value = "Save"
        deleteOrClearAllBtnText.value = "Clear All"
    }


    fun saveOrUpdate(){
        //check if flag is true means update
        if(isUpdateOrDelete){
            //setting edit text values to local var
            userToUpdateOrDelete.name = inputName.value!!
            userToUpdateOrDelete.email = inputEmail.value!!
            update(userToUpdateOrDelete)

        }else{
            val name = inputName.value !!
            val email = inputEmail.value!!
            insert(User(0, name, email))
            inputName.value = null
            inputEmail.value = null
        }

    }



    fun insert(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }

    fun clearAllOrDelete(){
        //check if below flag is true means btn is delete
        if (isUpdateOrDelete){
            delete(userToUpdateOrDelete)
        }else{
            clearAll()
        }

    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun update(user: User) = viewModelScope.launch {
        repository.updateUser(user)

        //resetting the values
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateBtnText.value = "Save"
        deleteOrClearAllBtnText.value = "Clear All"
    }

    fun delete(user: User) = viewModelScope.launch {
        repository.deleteUser(user)

        //resetting the values
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateBtnText.value = "Save"
        deleteOrClearAllBtnText.value = "Clear All"
    }

    fun initUpdateAndDelete(seletedItem : User) {

        inputName.value = seletedItem.name
        inputEmail.value = seletedItem.email
        userToUpdateOrDelete = seletedItem
        isUpdateOrDelete = true
        saveOrUpdateBtnText.value = "Update"
        deleteOrClearAllBtnText.value = "Delete"
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}