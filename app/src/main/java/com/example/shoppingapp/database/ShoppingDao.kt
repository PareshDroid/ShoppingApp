package com.example.shoppingapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppingapp.model.DataDBModel

@Dao
interface ShoppingDao {

    @Insert
    fun insertCategory(category: DataDBModel.Category)

    @Update
    fun updateCategory(category: DataDBModel.Category)

    @Delete
    fun deleteCategory(category: DataDBModel.Category)

    @Query("SELECT * FROM Category WHERE category_id=:catID")
    fun getSingleCategory(catID: Int): LiveData<DataDBModel.Category>

    @Query("SELECT * FROM Category WHERE category_id IN(:catIDs)")
    fun getMultipleCategory(catIDs: List<Int>): LiveData<List<DataDBModel.Category>>
}