package com.example.shoppingapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.shoppingapp.model.ProductDBModel

object VariantDBModel {

    @Entity(
        tableName = "Variant",
        foreignKeys = [ForeignKey(
            entity = ProductDBModel.Product::class,
            parentColumns = ["product_id"],
            childColumns = ["var_product_id"],
            onDelete = ForeignKey.CASCADE
        )])
    data class Variant(
        @PrimaryKey
        var variant_id:Int? = 0,
        var var_product_id:Int? = 0,
        var color:String? = "",
        var size:String? = "",
        var price:String? = ""
    )
}