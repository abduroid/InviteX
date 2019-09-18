package com.abduqodirov.invitex.singleList

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.abduqodirov.invitex.database.Mehmon

@BindingAdapter("chizilgan")
fun TextView.setChizilgan(mehmon: Mehmon?) {
    mehmon?.let {
        paintFlags = if (mehmon.isAytilgan)
            paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else
            0
    }
}