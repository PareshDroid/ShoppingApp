package com.example.shoppingapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

object DataDBModel {

    @Entity(tableName = "Category")
    data class Category(
        @PrimaryKey
        var category_id:Int? = 0,
        var name : String? = "",
        var childCategories:String? = ""
    )
}