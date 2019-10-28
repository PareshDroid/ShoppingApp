package com.example.shoppingapp.features.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.RoomViewModelKotlinSampleApplication
import com.example.shoppingapp.model.DataDBModel
import com.example.shoppingapp.model.ProductDBModel
import com.example.shoppingapp.model.VariantDBModel
import com.example.shoppingapp.repository.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class ProductDetailViewModel(): ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

    private var job: Job

    var dataRepository: DataRepository

    init{
        job = Job() //creating the job
        dataRepository = DataRepository()
    }

    fun getProductVariantFromDB(productId:Int): LiveData<List<VariantDBModel.Variant>> {
        val mShoppingDao = RoomViewModelKotlinSampleApplication.database!!.variantDao()
        return mShoppingDao.getProductVariant(productId)
    }

    override fun onCleared() {
        job.cancel()// cancel the job
        super.onCleared()
    }
}