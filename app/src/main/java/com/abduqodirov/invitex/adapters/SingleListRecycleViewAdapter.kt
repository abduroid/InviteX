package com.abduqodirov.invitex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abduqodirov.invitex.models.Mehmon
import com.abduqodirov.invitex.databinding.ItemListBinding
import com.abduqodirov.invitex.databinding.MemberNameHeaderBinding

private const val ITEM_VIEW_TYPE_MEMBER_NAME = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class SingleListRecycleViewAdapter(
    val aytilganClickListener: AytilganClickListener,
    val holdMenuClickListener: HoldMenuClickListener
) :
    ListAdapter<Mehmon,
            RecyclerView.ViewHolder>(MehmonDiffCallBack()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).toifa) {
            "mezbon" -> ITEM_VIEW_TYPE_MEMBER_NAME
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_MEMBER_NAME -> MemberNameViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> MehmonViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val mehmonItem = getItem(position)
        when (holder) {
            is MehmonViewHolder -> {
                holder.bind(
                    item = mehmonItem,
                    aytilganClickListener = aytilganClickListener,
                    holdMenuClickListener = holdMenuClickListener
                        //TODO fuck element va memberlarga qo'yilmasligi kerak
                )
            }

            is MemberNameViewHolder -> {
                holder.bind(member = mehmonItem)
            }
        }

    }

    class MemberNameViewHolder private constructor(val binding: MemberNameHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(member: Mehmon) {
            binding.member = member
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MemberNameViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MemberNameHeaderBinding.inflate(layoutInflater, parent, false)

                return MemberNameViewHolder(binding)
            }
        }
    }

    class MehmonViewHolder private constructor(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Mehmon,
            aytilganClickListener: AytilganClickListener,
            holdMenuClickListener: HoldMenuClickListener
        ) {
            if (item.ism == "fuck") {
                binding.root.visibility = View.INVISIBLE
            } else {
                binding.root.visibility = View.VISIBLE
                //TODO databinding variable for height and other appearance attributes
            }

            binding.root.setOnLongClickListener {
                holdMenuClickListener.onHold(mehmon = item)

                return@setOnLongClickListener true
            }

            //TODO watch about how RecyclerView re-use views again
            binding.mehmon = item
            binding.aytilganClickListener = aytilganClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MehmonViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListBinding.inflate(layoutInflater, parent, false)

                return MehmonViewHolder(
                    binding
                )
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

class HoldMenuClickListener(val holdMenuClickListener: (mehmon: Mehmon) -> Unit) {
    fun onHold(mehmon: Mehmon) = holdMenuClickListener(mehmon)
}

//class CollapseClickListener(val collapsClickListener: (member: Mehmon) -> Unit) {
//    fun onCollapsed(member: Mehmon) = collapsClickListener(member)
//}