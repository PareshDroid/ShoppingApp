package com.example.shoppingapp.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.RoomViewModelKotlinSampleApplication
import com.example.shoppingapp.model.DataDBModel
import com.example.shoppingapp.model.DataModel
import com.example.shoppingapp.repository.DataRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CategoryViewModel(): ViewModel(), CoroutineScope {

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

    fun getChildDataFromDB(categoryIdList:List<Int>):LiveData<List<DataDBModel.Category>>{
        val mShoppingDao= RoomViewModelKotlinSampleApplication.database!!.shoppingDao()
        return mShoppingDao.getMultipleCategory(categoryIdList)
    }

    //Placing data in DB using coroutines
    fun putAllDataInDatabase(data: DataModel.Result):MutableLiveData<Boolean>{
        val status: MutableLiveData<Boolean> = MutableLiveData()
        GlobalScope.launch(Dispatchers.Main) {
            status.value=dataRepository.putAllDataInDB(data)

        }
        return status
    }

    //find all the parents from all the category in the list
    fun getParentChildListData(shoppingData: DataModel.Result):HashMap<Int,List<Int>>{
        val categoryList=shoppingData.categories
        val categoryBooleanMap:HashMap<Int,Boolean> = HashMap()
        for(category in categoryList){
            categoryBooleanMap.put(category.category_id,false)  //using boolean to find parent
        }

        for(category in categoryList){
            if(!category.child_categories.isNullOrEmpty()){
                val childCategories = category.child_categories
                for(child_category in childCategories){
                    if(categoryBooleanMap.containsKey(child_category)){
                        categoryBooleanMap.put(child_category,true)
                    }
                }
            }
        }

        val parentChild:HashMap<Int,List<Int>> = HashMap()

        for(key in categoryBooleanMap.keys){
            if(categoryBooleanMap[key]==false){
                for(item in categoryList){
                    if(item.category_id==key){
                        parentChild.put(key,item.child_categories)
                    }
                }
            }
        }

        return parentChild
    }

    //fetching whole data from Network
    fun getAllCategories(): MutableLiveData<DataModel.Result> {
        val mCategoryData= dataRepository.getAllShoppingCategoryData()
        return mCategoryData
    }

    override fun onCleared() {
        job.cancel()// cancel the job
        super.onCleared()
    }
}