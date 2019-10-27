package com.example.shoppingapp.model

object ParentDataItemModel {

    data class ParentDataItem(val catId:Int, val name:String, val childCategories:String, val childDataList:ArrayList<ChildDataItemModel.ChildDataItem>)
}