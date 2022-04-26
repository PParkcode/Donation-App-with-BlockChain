package com.example.testing1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing1.MoreItemBinding
import com.example.testing1.R
import com.example.testing1.RecommItemBinding

import com.example.testing1.model.Campaign
import kotlinx.android.synthetic.main.recomm_item.view.*
import java.util.*


//arrayList<Int>에서 List<Campaing> 형태로 고치면 될듯 or CampaignList 형태로
/*
class RecommViewPagerAdapter(recommList: ArrayList<Int>) : RecyclerView.Adapter<RecommViewPagerAdapter.PagerViewHolder>() {



    var item = recommList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))


    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.img.setImageResource(item[position])
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.recomm_item, parent, false)){

        val img = itemView.recomm_img!!

    }
}
 */

                                                                        // RecyclerView.Adapter<RecommViewPagerAdapter.PagerViewHolder>()
class RecommViewPagerAdapter(val campaignItemClick: (Campaign) -> Unit): ListAdapter<Campaign, RecommViewPagerAdapter.RecommViewHolder>(CampDiffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<RecommItemBinding>(layoutInflater,viewType,parent,false)
        return RecommViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.recomm_item
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: RecommViewHolder, position: Int) {
        val campaign=getItem(position)
        holder.bind(campaign)
    }

    inner class RecommViewHolder(private val binding: RecommItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(campaign: Campaign) {
            binding.recommCampaign = campaign
            binding.executePendingBindings() //데이터가 수정되면 즉각 바인딩

            binding.root.setOnClickListener {
                campaignItemClick(campaign)
            }
        }
    }



    companion object CampDiffUtil: DiffUtil.ItemCallback<Campaign>(){
        override fun areItemsTheSame(oldItem: Campaign, newItem: Campaign): Boolean {
            //각 아이템들의 고유한 값을 비교해야 한다.
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Campaign, newItem: Campaign): Boolean {
            return oldItem==newItem
        }
    }


}