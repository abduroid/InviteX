package com.abduqodirov.invitex.adapters

import android.content.Context
import android.graphics.Paint
import android.text.Layout
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.databinding.BindingAdapter
import com.abduqodirov.invitex.models.Mehmon
import kotlin.math.roundToInt

@BindingAdapter("chizilgan")
fun TextView.setChizilgan(mehmon: Mehmon?) {
    mehmon?.let {
        paintFlags = if (mehmon.isAytilgan)
            paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else
            0
    }
}

@BindingAdapter("collapsed")
fun ConstraintLayout.setCollapsed(mehmon: Mehmon?) {
    mehmon?.let {

        maxHeight = if (mehmon.isCollapsed) {
            0
        } else {
            dpToPx(80, context)
        }

    }
}

fun dpToPx(dp: Int, context: Context): Int {

    val density: Float = context.resources.displayMetrics.density

    return (dp * density).roundToInt()
}