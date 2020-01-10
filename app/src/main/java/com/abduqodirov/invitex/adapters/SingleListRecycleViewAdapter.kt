package com.abduqodirov.invitex.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abduqodirov.invitex.MembersManager
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.databinding.ItemEmptyListBinding
import com.abduqodirov.invitex.models.Mehmon
import com.abduqodirov.invitex.databinding.ItemListBinding
import com.abduqodirov.invitex.databinding.MemberNameHeaderBinding

private const val ITEM_VIEW_TYPE_MEMBER_NAME = 0
private const val ITEM_VIEW_TYPE_ITEM = 1
private const val ITEM_VIEW_TYPE_EMPTY = 2

class SingleListRecycleViewAdapter(
    val aytilganClickListener: AytilganClickListener,
    val holdMenuClickListener: HoldMenuClickListener,
    val collapseClickListener: CollapseClickListener
) :
    ListAdapter<Mehmon,
            RecyclerView.ViewHolder>(MehmonDiffCallBack()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).toifa) {
            "mezbon" -> ITEM_VIEW_TYPE_MEMBER_NAME
            "empty" -> ITEM_VIEW_TYPE_EMPTY
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_MEMBER_NAME -> MemberNameViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> MehmonViewHolder.from(parent)
            ITEM_VIEW_TYPE_EMPTY -> TextViewViewHolder.from(parent)
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
                holder.bind(member = mehmonItem, collapseClickListener = collapseClickListener)
            }

            is TextViewViewHolder -> {
                holder.bind(mehmonItem)
            }

        }

    }

    class TextViewViewHolder private constructor(val binding: ItemEmptyListBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(mehmon: Mehmon) {
            binding.mehmon = mehmon
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TextViewViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemEmptyListBinding.inflate(layoutInflater, parent, false)
                return TextViewViewHolder(binding)
            }
        }

    }

    class MemberNameViewHolder private constructor(val binding: MemberNameHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(member: Mehmon, collapseClickListener: CollapseClickListener) {
            binding.member = member

            binding.expandCollapseButton.isChecked = MembersManager.membersCollapsed.get(member.ism) ?: false

            binding.expandCollapseButton.setOnCheckedChangeListener { buttonView, isCollapsed ->
                Log.i("sev", "Checkbox triggered and its value: $isCollapsed")
                collapseClickListener.onCollapsed(member = member, isCollapsed = isCollapsed)
            }

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


            binding.rootOfItem.setOnLongClickListener {
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

class CollapseClickListener(val collapsClickListener: (member: Mehmon, isCollapsed: Boolean) -> Unit) {
    fun onCollapsed(member: Mehmon, isCollapsed: Boolean) = collapsClickListener(member, isCollapsed)
}