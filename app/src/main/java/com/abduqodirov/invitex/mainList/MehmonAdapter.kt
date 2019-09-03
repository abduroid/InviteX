package com.abduqodirov.invitex.mainList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.databinding.ItemListBinding

class MehmonAdapter: ListAdapter<Mehmon, MehmonAdapter.ViewHolder>(
    MehmonDiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item = item)
    }

    class ViewHolder private constructor(val binding: ItemListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Mehmon) {
            binding.mehmon = item
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

}

class MehmonDiffCallBack: DiffUtil.ItemCallback<Mehmon>() {

    override fun areItemsTheSame(oldItem: Mehmon, newItem: Mehmon): Boolean {
        return oldItem.mehmonId == newItem.mehmonId
    }

    override fun areContentsTheSame(oldItem: Mehmon, newItem: Mehmon): Boolean {
        return oldItem == newItem
    }
}