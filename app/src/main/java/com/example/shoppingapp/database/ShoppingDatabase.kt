package com.example.shoppingapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppingapp.model.DataDBModel
import com.example.shoppingapp.model.ProductDBModel
import com.example.shoppingapp.model.VariantDBModel

@Database(entities = [(DataDBModel.Category::class),(ProductDBModel.Product::class),(VariantDBModel.Variant::class)], version = 1,exportSchema = false)
abstract class ShoppingDatabase : RoomDatabase(){

    abstract fun shoppingDao() : ShoppingDao
    abstract fun productDao() : ProductDao
    abstract fun variantDao() : VariantDao
}

