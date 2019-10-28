package com.example.shoppingapp.features.subcategory

import android.content.Intent
import android.os.Bundle
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.shoppingapp.R
import com.example.shoppingapp.features.productdetail.ProductDetailActivity
import com.example.shoppingapp.model.DataDBModel
import com.example.shoppingapp.model.ProductDBModel

class SubCategoryActivity: AppCompatActivity() {

    private lateinit var categoryModel: SubCategoryViewModel
    lateinit var msubCat_ExpandableListView : ExpandableListView
    internal var titleList: List<DataDBModel.Category> ? = null
    internal var adapter: ExpandableListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.shoppingapp.R.layout.activity_subcategory)

        msubCat_ExpandableListView = findViewById(R.id.subcategory_expandableListView)

        val extraData = intent.getStringExtra("keyIdentifier")

        val catList=extraData.substring(1,extraData.length-1).replace("\\s".toRegex(),"").split(",")

        val categoryList = mutableListOf<Int>()

        for(category in catList){
          categoryList.add(category.toInt())
        }

        categoryModel = ViewModelProviders.of(this).get(SubCategoryViewModel::class.java)

        val expandableList = HashMap<DataDBModel.Category,List<ProductDBModel.Product>>()

        for(category in categoryList){
            categoryModel.getParentDataFromDB(category).observe(this, Observer<DataDBModel.Category> { categoryData ->

                categoryModel.getSingleCatProductFromDB(categoryData.category_id!!).observe(this, Observer<List<ProductDBModel.Product>> { productList ->

                    expandableList.put(categoryData,productList)

                    if(expandableList.size==categoryList.size){
                        if(msubCat_ExpandableListView!=null){
                            val listData = expandableList
                            titleList = ArrayList(listData.keys)
                            adapter = SubCategoryExpandableListAdapter(this, titleList as ArrayList<DataDBModel.Category>, listData)
                            msubCat_ExpandableListView!!.setAdapter(adapter)

                            msubCat_ExpandableListView!!.setOnGroupExpandListener { groupPosition -> Toast.makeText(applicationContext, (titleList as ArrayList<DataDBModel.Category>)[groupPosition].name, Toast.LENGTH_SHORT).show() }

                            msubCat_ExpandableListView!!.setOnGroupCollapseListener { groupPosition -> Toast.makeText(applicationContext, (titleList as ArrayList<DataDBModel.Category>)[groupPosition].name, Toast.LENGTH_SHORT).show() }

                            msubCat_ExpandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->

                                val intent = Intent(this, ProductDetailActivity::class.java)
                                intent.putExtra("KEY_TAX_NAME", listData[(titleList as ArrayList<DataDBModel.Category>)[groupPosition]]!!.get(childPosition).tax_name)
                                intent.putExtra("KEY_TAX_VALUE", listData[(titleList as ArrayList<DataDBModel.Category>)[groupPosition]]!!.get(childPosition).tax_value)
                                intent.putExtra("KEY_PRODUCT_ID", listData[(titleList as ArrayList<DataDBModel.Category>)[groupPosition]]!!.get(childPosition).product_id)
                                intent.putExtra("KEY_PRODUCT_NAME", listData[(titleList as ArrayList<DataDBModel.Category>)[groupPosition]]!!.get(childPosition).product_name)
                                startActivity(intent)
                                false
                            }
                        }
                    }
                })
            })
        }

    }
}