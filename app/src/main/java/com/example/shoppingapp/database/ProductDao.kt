package com.example.shoppingapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppingapp.model.ProductDBModel

@Dao
interface ProductDao {
    @Insert
    fun insertProduct(product: ProductDBModel.Product)

    @Update
    fun updateProduct(product: ProductDBModel.Product)

    @Query("SELECT * FROM Product WHERE cat_id=:productID")
    fun findProduct(productID: Int): List<ProductDBModel.Product>

}