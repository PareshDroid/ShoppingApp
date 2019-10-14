package com.example.shoppingapp.features

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.shoppingapp.R
import com.example.shoppingapp.endpoint.ApiService
import com.example.shoppingapp.model.DataDBModel
import com.example.shoppingapp.model.DataModel
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val api: ApiService = ApiService.create()
    private val subscriptions = CompositeDisposable()

    private lateinit var categoryModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        //absorb all the data from view model
        categoryModel.getAllCategories().observe(this,Observer<DataModel.Result>{ categoryData ->

            //put all the data in database
            categoryModel.putAllDataInDatabase(categoryData).observe(this,Observer<Boolean>{ status ->

                if(status){
                    //finding the parent of all the categories from the list
                    val parentChildList = categoryModel.getParentChildListData(categoryData)

                    for(key in parentChildList.keys){
                        //getting parent data and child data from database and displaying in the mainactivity
                        categoryModel.getParentDataFromDB(key).observe(this,Observer<DataDBModel.Category>{ catgegory ->

                            val data = catgegory
                            Log.d("Category",catgegory.toString())
                        })

                        categoryModel.getChildDataFromDB(parentChildList[key]!!).observe(this, Observer<List<DataDBModel.Category>> { productList ->

                            val prodList = productList
                            Log.d("Category",prodList.toString())
                        })
                    }
                }
            })
        })
    }

}
