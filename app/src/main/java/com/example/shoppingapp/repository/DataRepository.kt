package com.example.shoppingapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.shoppingapp.RoomViewModelKotlinSampleApplication
import com.example.shoppingapp.endpoint.ApiService
import com.example.shoppingapp.model.DataDBModel
import com.example.shoppingapp.model.DataModel
import com.example.shoppingapp.model.ProductDBModel
import com.example.shoppingapp.model.VariantDBModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class DataRepository {

    private val api: ApiService = ApiService.create()
    private val subscriptions = CompositeDisposable()

    //place all data in db. Coroutines is used to place data in db
    suspend fun putAllDataInDB(shoppingData: DataModel.Result):Boolean{

        val status =  true

        return GlobalScope.async(Dispatchers.IO) {

            val catList = shoppingData.categories
            val shoppingDao = RoomViewModelKotlinSampleApplication.database!!.shoppingDao()
            for(category in catList){ // getting single category from list and placing in db
                val categoryModel = DataDBModel.Category()
                categoryModel.category_id = category.category_id
                categoryModel.name = category.category_name
                if(!category.child_categories.isNullOrEmpty()){
                    categoryModel.childCategories = category.child_categories.toString()
                }
                shoppingDao.insertCategory(categoryModel) // placing single category in Database


                val productList=category.productCategories
                val productDao= RoomViewModelKotlinSampleApplication.database!!.productDao()

                for(product in productList){ // getting single product from category and placing in db
                    val productModel = ProductDBModel.Product()
                    productModel.product_id = product.productId
                    productModel.cat_id = category.category_id // placing category id for foreign key use
                    productModel.product_name = product.productName
                    productModel.tax_name = product.tax.name
                    productModel.tax_value = product.tax.value.toString()
                    productDao.insertProduct(productModel) // placing product in Database

                    val variantList = product.variants
                    val variantDao= RoomViewModelKotlinSampleApplication.database!!.variantDao()

                    for(variant in variantList){  //getting all variant one by one inside single category and placing all of them in db
                        val variantModel = VariantDBModel.Variant()
                        variantModel.var_product_id =  product.productId // placing product id for foreign key use
                        variantModel.variant_id = variant.variant_id
                        variantModel.color = variant.color
                        variantModel.price = variant.price.toString()
                        variantModel.size = variant.size.toString()
                        variantDao.insertVariant(variantModel) // placing variant in database
                    }
                }
            }

            status
        }.await()

    }

    //make network call and get all the data. RxJava is used to get data from network.
    fun getAllShoppingCategoryData(): MutableLiveData<DataModel.Result> {

        val categoryData: MutableLiveData<DataModel.Result> = MutableLiveData()

        subscriptions.add(
            api.getAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                            shoppingData: DataModel.Result ->
                        categoryData.value = shoppingData
                    },
                    {
                            error:Throwable -> Log.d("error","error")
                    }
                )
        )
        return categoryData
    }
}