package com.example.shoppingapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppingapp.model.VariantDBModel

@Dao
interface VariantDao {

    @Insert
    fun insertVariant(category: VariantDBModel.Variant)

    @Update
    fun updateVariant(category: VariantDBModel.Variant)

    @Delete
    fun deleteVariant(category: VariantDBModel.Variant)

    @Query("SELECT * FROM Variant WHERE var_product_id=:varProductID")
    fun getProductVariant(varProductID: Int): LiveData<List<VariantDBModel.Variant>>

    @Query("DELETE FROM Variant")
    fun deleteAllVariants()
}