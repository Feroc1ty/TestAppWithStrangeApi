package ru.rykunov.testappwithapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.rykunov.testappwithapi.MainActivity
import ru.rykunov.testappwithapi.databinding.GoodsItemsBinding
import ru.rykunov.testappwithapi.pojo.Goods

class GoodsAdapter(mainActivity: MainActivity): RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>() {
    private var goodsList = ArrayList<Goods>()
    lateinit var onItemClick: ((Goods) -> Unit)

    fun setGoods(goodslistarray: ArrayList<Goods>){
        this.goodsList = goodslistarray
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        return GoodsViewHolder(GoodsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        holder.binding.tvName.setText(goodsList[position].name)
        holder.binding.tvCode.setText(goodsList[position].code)
        holder.binding.tvCount.setText(goodsList[position].count.toString())
        holder.binding.tvPrice.setText("${goodsList[position].price} ₽")

        holder.binding.goodsCard.setOnClickListener{
            onItemClick.invoke(goodsList[position])
        }
    }

    override fun getItemCount(): Int {
        return goodsList.size
    }

    class GoodsViewHolder(val binding: GoodsItemsBinding):RecyclerView.ViewHolder(binding.root)
}