package com.example.shoppingapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppingapp.model.DataDBModel
import com.example.shoppingapp.model.ProductDBModel

@Dao
interface ProductDao {
    @Insert
    fun insertProduct(product: ProductDBModel.Product)

    @Update
    fun updateProduct(product: ProductDBModel.Product)

    @Query("SELECT * FROM Product WHERE cat_id=:productID")
    fun getSingleCatProduct(productID: Int): LiveData<List<ProductDBModel.Product>>

    @Query("SELECT * FROM Product WHERE cat_id IN(:catIDs)")
    fun getMultipleCatProduct(catIDs: List<Int>): LiveData<List<ProductDBModel.Product>>
}