package ru.rykunov.testappwithapi

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.rykunov.testappwithapi.databinding.ActivityGoodsDetailsBinding

class GoodsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoodsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener{
            finish()
        }

        val intent = intent
        val name = intent.getStringExtra(MainActivity.GOODS_NAME)
        val code = intent.getStringExtra(MainActivity.GOODS_CODE)
        val count = intent.getStringExtra(MainActivity.GOODS_COUNT)
        val price = intent.getStringExtra(MainActivity.GOODS_PRICE)
        val rosnPrice = intent.getStringExtra(MainActivity.GOODS_ROSNPRICE)
        val alcohol = intent.getStringExtra(MainActivity.GOODS_ALCOHOL)
        val alcoholDegree = intent.getStringExtra(MainActivity.GOODS_ALCOHOL_DEGREE)

        with(binding){
            tvDetailsName.setText(name)
            tvDetailsCode.setText(code)
            tvDetailsCount.setText(count.toString())
            tvDetailsPrice.setText("$price ₽")
            tvDetailsRosnPrice.setText("$rosnPrice ₽")
            if (alcohol != null){
                tvalcohol.visibility = View.VISIBLE
                tvalcoholdegree.visibility = View.VISIBLE
                tvDetailsAlcohol.visibility = View.VISIBLE
                tvDetailsAlcoholDegree.visibility = View.VISIBLE
                tvDetailsAlcohol.setText(alcohol)
                tvDetailsAlcoholDegree.setText("$alcoholDegree %")
            }
        }

    }
}