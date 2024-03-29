package com.example.shoppingapp.features.mainpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.shoppingapp.R
import com.example.shoppingapp.features.BaseActivity
import com.example.shoppingapp.features.subcategory.SubCategoryActivity
import com.example.shoppingapp.model.DataDBModel
import com.example.shoppingapp.model.DataModel
import com.example.shoppingapp.model.ParentDataItemModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var categoryModel: CategoryViewModel

    lateinit var mExpandableListView : ExpandableListView
    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<DataDBModel.Category> ? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.shoppingapp.R.layout.activity_main)

        mExpandableListView = findViewById(R.id.expandableListView)

        categoryModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        val dialog = setProgressDialog(this, "Loading..")
        dialog.show()

        //absorb all the data from view model
        categoryModel.getAllCategories().observe(this,Observer<DataModel.Result>{ categoryData ->


            //put all the data in database
            categoryModel.putAllDataInDatabase(categoryData).observe(this,Observer<Boolean>{ status ->

                if(status){
                    //finding the parent of all the categories from the list
                    val parentChildList = categoryModel.getParentChildListData(categoryData)

                    val parentList : List<ParentDataItemModel.ParentDataItem>

                    val expandableList = HashMap<DataDBModel.Category,List<DataDBModel.Category>>()

                    for(key in parentChildList.keys){


                        //getting parent data and child data from database and displaying in the mainactivity
                        categoryModel.getParentDataFromDB(key).observe(this,Observer<DataDBModel.Category>{ catgegory ->

                            //categoryList.add(catgegory.toString())

                            categoryModel.getChildDataFromDB(parentChildList[key]!!).observe(this, Observer<List<DataDBModel.Category>> { productList ->

                                //prodList.put(i,productList)

                                expandableList.put(catgegory,productList)

                                Log.d("parent child",parentChildList.size.toString())
                                if(expandableList.size==parentChildList.size){
                                    if (mExpandableListView != null) {

                                        dialog.dismiss()

                                        val listData = expandableList
                                        titleList = ArrayList(listData.keys)
                                        adapter = CustomExpandableListAdapter(
                                            this,
                                            titleList as ArrayList<DataDBModel.Category>,
                                            listData
                                        )
                                        expandableListView!!.setAdapter(adapter)

                                        expandableListView!!.setOnGroupExpandListener { groupPosition -> Toast.makeText(applicationContext, (titleList as ArrayList<DataDBModel.Category>)[groupPosition].name, Toast.LENGTH_SHORT).show() }

                                        expandableListView!!.setOnGroupCollapseListener { groupPosition -> Toast.makeText(applicationContext, (titleList as ArrayList<DataDBModel.Category>)[groupPosition].name, Toast.LENGTH_SHORT).show() }

                                        expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->

                                            val intent = Intent(this, SubCategoryActivity::class.java)
                                            // To pass any data to next activity
                                            intent.putExtra("keyIdentifier", listData[(titleList as ArrayList<DataDBModel.Category>)[groupPosition]]!!.get(childPosition).childCategories)
                                            // start your next activity
                                            startActivity(intent)

                                            false
                                        }
                                    }

                                }
                            })

                        })

                    }
                }

            })
        })
    }

}
