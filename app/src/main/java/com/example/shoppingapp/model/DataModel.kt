package com.example.shoppingapp.model

import com.google.gson.annotations.SerializedName

object DataModel {

    data class Result (

        @SerializedName("categories") val categories : List<Categories>,
        @SerializedName("rankings") val rankings : List<Rankings>
    )

    data class Categories (

        @SerializedName("id") val category_id : Int,
        @SerializedName("name") val category_name : String,
        @SerializedName("products") val productCategories : List<ProductCategories>,
        @SerializedName("child_categories") val child_categories : List<Int>
    )

    data class Rankings (

        @SerializedName("ranking") val ranking : String,
        @SerializedName("products") val products : List<Products>
    )

    data class Products (

        @SerializedName("id") val id : Int,
        @SerializedName("view_count") val view_count : Int
    )

    data class ProductCategories(
        @SerializedName("id") val productId : Int,
        @SerializedName("name") val productName : String,
        @SerializedName("date_added") val date_added : String,
        @SerializedName("variants") val variants : List<Variants>,
        @SerializedName("tax") val tax : Tax
    )

    data class Tax (

        @SerializedName("name") val name : String,
        @SerializedName("value") val value : Double
    )

    data class Variants (

        @SerializedName("id") val variant_id : Int,
        @SerializedName("color") val color : String,
        @SerializedName("size") val size : Int,
        @SerializedName("price") val price : Int
    )
}