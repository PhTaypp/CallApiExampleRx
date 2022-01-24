package com.example.callapiexamplerx.adapter

import com.example.callapiexamplerx.model.Users
import java.lang.String
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.callapiexamplerx.R


class UserAdapter(c: Context) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val context = c
    var listUserOnItemClickListener: OnClickListener? = null

    private var listUsers: List<Users> = mutableListOf()

    override fun getItemCount(): Int {
        return listUsers.size
    }

    fun setData(listUsers: List<Users>) {
        this.listUsers = listUsers
        notifyItemRangeChanged(0, this.listUsers.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val u: Users = listUsers[position]
        val id = u.id.toString()
        val url = u.avatar_url
        Log.i("url", " url :   $url")
        Glide.with(context).load(url).into(holder.ivAvatar)
        holder.tvLoginName.text = u.login
        holder.tvId.text = String.valueOf(id)
        holder.itemView.setOnClickListener {
            listUserOnItemClickListener?.onClickItem(u)
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLoginName: TextView = itemView.findViewById<View>(R.id.tv_login_name) as TextView
        var tvId: TextView = itemView.findViewById<View>(R.id.tv_id) as TextView
        var ivAvatar: ImageView = itemView.findViewById<View>(R.id.iv_avatar) as ImageView
    }

    interface OnClickListener {
        fun onClickItem(u : Users)
    }
}