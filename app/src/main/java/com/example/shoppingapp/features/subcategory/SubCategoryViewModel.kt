package com.example.shoppingapp.features.subcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.RoomViewModelKotlinSampleApplication
import com.example.shoppingapp.model.DataDBModel
import com.example.shoppingapp.model.ProductDBModel
import com.example.shoppingapp.repository.DataRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SubCategoryViewModel(): ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var job: Job

    var dataRepository: DataRepository

    init{
        job = Job() //creating the job
        dataRepository = DataRepository()
    }

    fun getParentDataFromDB(categoryId:Int): LiveData<DataDBModel.Category> {
        val mShoppingDao= RoomViewModelKotlinSampleApplication.database!!.shoppingDao()
        return mShoppingDao.getSingleCategory(categoryId)
    }


    fun getSingleCatProductFromDB(productId:Int): LiveData<List<ProductDBModel.Product>> {
        val mShoppingDao = RoomViewModelKotlinSampleApplication.database!!.productDao()
        return mShoppingDao.getSingleCatProduct(productId)
    }

    fun getMultipleCatProductFromDB(productIdList:List<Int>): LiveData<List<ProductDBModel.Product>> {
        val mShoppingDao = RoomViewModelKotlinSampleApplication.database!!.productDao()
        return mShoppingDao.getMultipleCatProduct(productIdList)
    }

   override fun onCleared() {
        job.cancel()// cancel the job
        super.onCleared()
    }
}