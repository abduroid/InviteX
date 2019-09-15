package com.abduqodirov.invitex.singleList

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.abduqodirov.invitex.database.Mehmon

@BindingAdapter("mehmonIsm")
fun TextView.setMehmonIsm(item: Mehmon?) {
    item?.let {
        text = item.ism
    }
}