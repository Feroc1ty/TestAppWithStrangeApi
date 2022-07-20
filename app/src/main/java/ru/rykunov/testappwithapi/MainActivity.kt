package ru.rykunov.testappwithapi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import ru.rykunov.testappwithapi.adapter.GoodsAdapter
import ru.rykunov.testappwithapi.databinding.ActivityMainBinding
import ru.rykunov.testappwithapi.pojo.Goods
import ru.rykunov.testappwithapi.viewmodel.ViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var homeMvvm: ViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var goodsItemsAdapter: GoodsAdapter
    lateinit var apiData: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        goodsItemsAdapter = GoodsAdapter(this)
        setContentView(binding.root)

        homeMvvm = ViewModelProviders.of(this)[ViewModel::class.java]
        runBlocking {
            apiData = homeMvvm.readApiData()
        }
        prepareGoodsRcView()
        homeMvvm.parseGoodsList(apiData)
        observeGoodsItemsLiveData()
        onGoodsItemClick()
    }

    private fun onGoodsItemClick(){
        goodsItemsAdapter.onItemClick = {goods->
            val intent = Intent(this, GoodsDetailsActivity::class.java)
            intent.putExtra(GOODS_NAME, goods.name)
            intent.putExtra(GOODS_CODE, goods.code)
            intent.putExtra(GOODS_COUNT, goods.count.toString())
            intent.putExtra(GOODS_PRICE, goods.price.toString())
            intent.putExtra(GOODS_ROSNPRICE, (
                    if (goods.count > 0) (goods.price*goods.count).toString()
                    else (goods.price).toString())
                    )

            for (attribute in goods.attributes){
                intent.putExtra(attribute.name, attribute.data)
            }
            startActivity(intent)
        }
    }
    /*
    Подготовка Recycler View
    */
    private fun prepareGoodsRcView(){
        binding.rcGoods.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = goodsItemsAdapter
        }
    }

    private fun observeGoodsItemsLiveData(){
        homeMvvm.observeGoodsLiveData().observe(this){
            goodsList ->
            goodsItemsAdapter.setGoods(goodsList as ArrayList<Goods>)
        }
    }

    companion object{
        const val GOODS_NAME = "goodsName"
        const val GOODS_CODE = "goodsCode"
        const val GOODS_COUNT = "goodsCount"
        const val GOODS_PRICE = "goodsPrice"
        const val GOODS_ROSNPRICE = "goodsRosnPrice"
        const val GOODS_ALCOHOL = "goodsAlcohol"
        const val GOODS_ALCOHOL_DEGREE = "goodsAlcoholDegree"
    }

}

