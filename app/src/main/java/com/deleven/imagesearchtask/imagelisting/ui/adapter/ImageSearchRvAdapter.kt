package com.deleven.imagesearchtask.imagelisting.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deleven.imagesearchtask.R
import com.deleven.imagesearchtask.base.utils.getDeviceWidth
import com.deleven.imagesearchtask.imagelisting.models.ImageModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_row_search_item.view.*

class ImageSearchRvAdapter (mContext: Context, private var showList: MutableList<ImageModel>,
                            private val onItemClickListener: IShowListClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface IShowListClick{
        fun onItemClick(imageModel: ImageModel)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.view_row_search_item,parent,false)
        view.searchImageItemIV.apply {
            layoutParams.width = this.getDeviceWidth()
            layoutParams.height = this.getDeviceWidth()/2
            this.setBackgroundColor(android.support.v4.content.ContextCompat.getColor(this.context, R.color.testGray))
        }
        return  ImageListingVH( view, onItemClickListener)
    }

    override fun getItemCount() = showList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val showModel = showList[position]
        (holder as ImageListingVH).bindData(showModel)
    }


    private class ImageListingVH constructor(view: View, val onItemClickListener: IShowListClick): RecyclerView.ViewHolder(view){

        var searchIv = itemView.searchImageItemIV
        var screenWidth: Int = itemView.getDeviceWidth()


        fun bindData(imageModel: ImageModel){

            imageModel.webFormatUrl?.let {
                Picasso.with(itemView.context).load(imageModel.webFormatUrl).resize(screenWidth, screenWidth/2).noFade().into(searchIv)
            }

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(imageModel)
            }

        }

    }

    fun addItems(dataItems: MutableList<ImageModel>){
        showList = dataItems
        notifyDataSetChanged()
    }
}