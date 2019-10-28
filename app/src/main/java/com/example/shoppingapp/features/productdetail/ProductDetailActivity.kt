package com.example.shoppingapp.features.productdetail

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.features.BaseActivity
import com.example.shoppingapp.model.VariantDBModel

class ProductDetailActivity:BaseActivity() {

    lateinit var mTaxName : TextView
    lateinit var mProductName : TextView
    lateinit var mTaxValue : TextView
    lateinit var mVariantRecyclerView : RecyclerView

    private lateinit var productDetailModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        mTaxName = findViewById(R.id.tax_name)
        mTaxValue = findViewById(R.id.tax_value)
        mProductName = findViewById(R.id.product_name)
        mVariantRecyclerView = findViewById(R.id.variant_recyclerView)

        val taxName = intent.getStringExtra("KEY_TAX_NAME")
        val taxValue= intent.getStringExtra("KEY_TAX_VALUE")
        val productId = intent.getIntExtra("KEY_PRODUCT_ID",1)
        val productName = intent.getStringExtra("KEY_PRODUCT_NAME")

        mTaxName.text = resources.getString(R.string.tax_name)+taxName
        mTaxValue.text = resources.getString(R.string.tax_value)+taxValue
        mProductName.text = resources.getString(R.string.product_name)+productName

        productDetailModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)

        val dialog = setProgressDialog(this, "Loading..")
        dialog.show()

        productDetailModel.getProductVariantFromDB(productId.toInt()).observe(this, Observer<List<VariantDBModel.Variant>> { variantDataList ->

            dialog.dismiss()
            val mListAdapter = VariantAdapter(variantDataList)
            val mLayoutManager = LinearLayoutManager(this)
            mVariantRecyclerView.setLayoutManager(mLayoutManager)
            mVariantRecyclerView.setItemAnimator(DefaultItemAnimator())
            mVariantRecyclerView.setAdapter(mListAdapter)
        })
    }
}