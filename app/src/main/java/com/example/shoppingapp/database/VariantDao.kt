package com.example.shoppingapp.database

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
    fun findVariant(varProductID: Int): List<VariantDBModel.Variant>
}