package com.example.suantony.movieappsproject.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suantony.movieappsproject.R
import com.example.suantony.movieappsproject.data.source.remote.response.ReviewItem
import kotlinx.android.synthetic.main.item_review.view.*
import java.util.*

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private var listData = ArrayList<ReviewItem>()

    fun setData(newListData: List<ReviewItem>?) {
        if (newListData == null) return
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount() = listData.size

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(review: ReviewItem) {
            with(itemView) {
                tv_author.text = review.author
                tv_content.text = review.content
            }
        }
    }

}