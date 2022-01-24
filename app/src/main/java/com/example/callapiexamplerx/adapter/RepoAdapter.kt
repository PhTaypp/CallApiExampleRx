package com.example.callapiexamplerx.adapter

import java.lang.String
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.callapiexamplerx.R
import com.example.callapiexamplerx.model.Item
import com.example.callapiexamplerx.model.RepoModel


class RepoAdapter(c: Context) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    private val context = c
    var listRepoOnItemClickListener: OnItemRepoClickListener? = null

    private var listItem: List<Item> = mutableListOf()

    override fun getItemCount(): Int {
        return listItem.size
    }

    fun setListRepo(repoModel : RepoModel) {
        this.listItem = repoModel.item
        notifyItemRangeChanged(0, this.listItem.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
        return RepoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item: Item = listItem[position]
        val id = item.id
        val licenseUrl = item.owner.avatarUrl

        val fullName = item.fullName
        Log.i("url", " url :   $licenseUrl")
        Glide.with(context).load(licenseUrl).into(holder.ivAvatar)
        holder.tvFullName.text = fullName
        holder.tvId.text = String.valueOf(id)

        holder.itemView.setOnClickListener {
            listRepoOnItemClickListener?.onClickItem(item)
        }
    }

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFullName = itemView.findViewById<View>(R.id.tv_full_name) as TextView
        var tvId = itemView.findViewById<View>(R.id.tv_node_id) as TextView
        var ivAvatar = itemView.findViewById<View>(R.id.iv_avatar_url) as ImageView
    }

    interface OnItemRepoClickListener {
        fun onClickItem(item: Item)
    }
}