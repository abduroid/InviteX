package com.abduqodirov.invitex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abduqodirov.invitex.databinding.ItemMemeberListBinding

class MezbonRecyclerAdapter :
    ListAdapter<String, MezbonRecyclerAdapter.MezbonViewHolder>(MezbonDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MezbonViewHolder {
        return MezbonViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MezbonViewHolder, position: Int) {
        val mezbonIsm = getItem(position)

        holder.bind(mezbonIsm)
    }

    class MezbonViewHolder private constructor(val binding: ItemMemeberListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(memberName: String) {
            binding.mezbonIsm = memberName
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MezbonViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMemeberListBinding.inflate(layoutInflater, parent, false)

                return MezbonViewHolder(binding)
            }
        }

    }

}

class MezbonDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}