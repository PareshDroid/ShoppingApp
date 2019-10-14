package com.example.shoppingapp

import android.app.Application
import androidx.room.Room
import com.example.shoppingapp.database.ShoppingDatabase

class RoomViewModelKotlinSampleApplication: Application() {

    companion object {
        var database: ShoppingDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        database =  Room.databaseBuilder(applicationContext, ShoppingDatabase::class.java, "shopping_db").fallbackToDestructiveMigration().build()
    }
}