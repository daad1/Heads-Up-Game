package com.example.headsup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.headsup.databinding.ItemRowBinding

class RVAdapter(val userList: ArrayList<UsersItem>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val Binding: ItemRowBinding) : RecyclerView.ViewHolder(Binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.Binding.apply {
            tvName.text = userList[position].name
            tvTaboo1.text = userList[position].taboo1
            tvTaboo2.text = userList[position].taboo2
            tvTaboo3.text = userList[position].taboo3
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}