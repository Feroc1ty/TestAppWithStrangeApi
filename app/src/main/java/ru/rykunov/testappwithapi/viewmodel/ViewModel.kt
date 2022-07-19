package ru.rykunov.testappwithapi.viewmodel

import androidx.core.text.htmlEncode
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.rykunov.testappwithapi.pojo.Goods
import ru.rykunov.testappwithapi.pojo.GoodsAttr
import java.net.URL


class ViewModel: ViewModel() {
    private val url = "https://edo.ilexx.ru/test/catalog.spr"
    private var goodsListLiveData = MutableLiveData<List<Goods>>()


    suspend fun readApiData() = withContext(Dispatchers.IO) {
        URL(url).readText()
    }


    fun getGoodsList(apiGoodsList: String){

        var goodsList = apiGoodsList.replace("\uFEFF", "")
        var goodsData = ArrayList<Goods>()
        var goods: Goods = Goods(0,"","", "", 0.0, 0.0, arrayListOf())
        for (line in goodsList.lines()) {
            if (line.isNotEmpty()){
                if(line[0].isDigit()){
                    val objectparams = line.toString().split(";")
                    goods = Goods(
                        objectparams[0].toInt(),
                        objectparams[1],
                        objectparams[2],
                        objectparams[3],
                        objectparams[4].toDouble(),
                        objectparams[5].toDouble(),
                        attrs = arrayListOf()
                    )
                }
            if (line.matches(Regex("<goods_attr id=\"\\d+\" attr_id=\"22\">.+"))) {
                val startDataPattern = "<goods_attr id=\"\\d+\" attr_id=\"\\d+\">".toRegex()
                val endDataPattern = "</goods_attr>"
                var attrData = (line.replace(startDataPattern, "")).replace(endDataPattern, "")
                goods.attrs.add(GoodsAttr(22, attrData))
            }
            if (line.matches(Regex("<goods_attr id=\"\\d+\" attr_id=\"27\">.+"))) {
                val startDataPattern = "<goods_attr id=\"\\d+\" attr_id=\"\\d+\">".toRegex()
                val endDataPattern = "</goods_attr>"
                var attrData = (line.replace(startDataPattern, "")).replace(endDataPattern, "")
                goods.attrs.add(GoodsAttr(27, attrData))
                goodsData.add(goods!!)
            }

        }}


        goodsListLiveData.value = goodsData
    }



    fun observeGoodsLiveData(): LiveData<List<Goods>>{
        return goodsListLiveData
    }
}