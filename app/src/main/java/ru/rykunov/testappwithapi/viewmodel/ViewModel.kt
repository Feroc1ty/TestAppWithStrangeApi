package ru.rykunov.testappwithapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.rykunov.testappwithapi.pojo.Goods
import ru.rykunov.testappwithapi.pojo.GoodsAttribute
import java.net.URL


class ViewModel : ViewModel() {
    private val url = "https://edo.ilexx.ru/test/catalog.spr"
    private var goodsListLiveData = MutableLiveData<List<Goods>>()


    suspend fun readApiData() = withContext(Dispatchers.IO) {
        URL(url).readText()
    }


    fun parseGoodsList(apiGoodsList: String) {

        val goodsList = apiGoodsList.replace("\uFEFF", "")
        var goodsData = ArrayList<Goods>()
        var goods: Goods? = null
        for (line in goodsList.lines()) {
            if (line.isEmpty()) continue
            if (goods == null || line[0].isDigit()) {
                val objectparams = line.split(";")
                goods = Goods(
                    objectparams[0].toInt(),
                    objectparams[1],
                    objectparams[2],
                    objectparams[3],
                    objectparams[4].toFloat(),
                    objectparams[5].toFloat(),
                    attributes = arrayListOf()
                )
                goodsData.add(goods)
            }
            if (line.matches(Regex("<goods_attr id=\"\\d+\" attr_id=\"22\">.+"))) {
                val startDataPattern = "<goods_attr id=\"\\d+\" attr_id=\"\\d+\">".toRegex()
                val endDataPattern = "</goods_attr>"
                val attributeData = (line.replace(startDataPattern, "")).replace(endDataPattern, "")
                goods.attributes.add(GoodsAttribute(22, attributeData, "goodsAlcohol"))
            }
            if (line.matches(Regex("<goods_attr id=\"\\d+\" attr_id=\"27\">.+"))) {
                val startDataPattern = "<goods_attr id=\"\\d+\" attr_id=\"\\d+\">".toRegex()
                val endDataPattern = "</goods_attr>"
                val attributeData = (line.replace(startDataPattern, "")).replace(endDataPattern, "")
                goods.attributes.add(GoodsAttribute(27, attributeData, "goodsAlcoholDegree"))
            }

        }


        goodsListLiveData.value = goodsData
    }


    fun observeGoodsLiveData(): LiveData<List<Goods>> {
        return goodsListLiveData
    }
}