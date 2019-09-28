package com.abduqodirov.invitex.singleList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.databinding.ItemListBinding

class SingleListRecycleViewAdapter(val aytilganClickListener: AytilganClickListener):
    ListAdapter<Mehmon,
            SingleListRecycleViewAdapter.ViewHolder>(MehmonDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item = item, aytilganClickListener = aytilganClickListener)
    }


    class ViewHolder private constructor(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Mehmon, aytilganClickListener: AytilganClickListener) {
            binding.mehmon = item
            binding.aytilganClickListener = aytilganClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class MehmonDiffCallBack : DiffUtil.ItemCallback<Mehmon>() {
    override fun areItemsTheSame(oldItem: Mehmon, newItem: Mehmon): Boolean {
        return oldItem.mehmonId == newItem.mehmonId
    }

    //TODO animatsiyani to'g'rilash kk
    override fun areContentsTheSame(oldItem: Mehmon, newItem: Mehmon): Boolean {
        return oldItem == newItem
    }
}

class AytilganClickListener(val aytilganClickListener: (mehmon: Mehmon) -> Unit) {
    fun onChecked(mehmon: Mehmon) = aytilganClickListener(mehmon)
}
