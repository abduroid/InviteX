package com.abduqodirov.invitex.mainList

import android.opengl.Visibility
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.abduqodirov.invitex.database.Mehmon

@BindingAdapter("mehmonIsm")
fun TextView.setMehmonIsm(item: Mehmon?) {
    item?.let {
        text = item.ism
    }
}