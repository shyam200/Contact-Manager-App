package com.example.contactmanagerapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//NOTE:- It is not necessary to provide table name and column name, in that case room will auto pick
// name as the class name as table name and property name as column name
@Entity(tableName = "user")
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Int,
    @ColumnInfo(name = "user_name")
    var name: String,
    @ColumnInfo(name = "user_email")
    var email: String
)
