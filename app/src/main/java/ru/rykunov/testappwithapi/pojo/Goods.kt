package ru.rykunov.testappwithapi.pojo

data class Goods(
    val id: Int,
    val code: String,
    val name: String,
    val recipeName: String,
    val price: Float,
    val count: Float,
    var attributes: ArrayList<GoodsAttribute>
)
