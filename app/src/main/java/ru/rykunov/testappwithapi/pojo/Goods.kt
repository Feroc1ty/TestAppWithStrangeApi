package ru.rykunov.testappwithapi.pojo

data class Goods(
    val id: Int,
    val code: String,
    val name: String,
    val recipeName: String,
    val price: Double,
    val count: Double,
    var attributes: ArrayList<GoodsAttribute>
)
