package com.example.contactmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactmanagerapp.databinding.ActivityMainBinding
import com.example.contactmanagerapp.room.User
import com.example.contactmanagerapp.room.UserDatabase
import com.example.contactmanagerapp.room.UserRepository
import com.example.contactmanagerapp.viewModel.UserViewModel
import com.example.contactmanagerapp.viewModel.UserViewModelFactory
import com.example.contactmanagerapp.viewUI.MyRecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Room Database
        val dao = UserDatabase.getInstance(applicationContext).userDAO
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)

        userViewModel = ViewModelProvider(this, factory).get(UserViewModel :: class.java)

        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView() {
       binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayUsersList()
    }

    private fun displayUsersList() {
        userViewModel.users.observe(this, Observer {
            binding.recyclerView.adapter = MyRecyclerViewAdapter(it,
                {selectedItem : User -> listItemClicked(selectedItem)})
        })
    }

    private fun listItemClicked(selectedItem: User) {
        Toast.makeText(this,
        "This item is clicked ${selectedItem.name}", Toast.LENGTH_SHORT).show()

        userViewModel.initUpdateAndDelete(selectedItem)
    }
}