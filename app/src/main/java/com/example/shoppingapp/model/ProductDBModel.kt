package com.example.shoppingapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.shoppingapp.model.DataDBModel

object ProductDBModel {

    @Entity(
        tableName = "Product",
        foreignKeys = [ForeignKey(
            entity = DataDBModel.Category::class,
            parentColumns = ["category_id"],
            childColumns = ["cat_id"],
            onDelete = CASCADE)])
    data class Product(
        @PrimaryKey
        var product_id:Int? = 0,
        var cat_id:Int? = 0,
        var product_name:String? = "",
        var tax_name:String? = "",
        var tax_value:String? = ""
    )
}