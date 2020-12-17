package com.abduqodirov.invitex.adapters

import android.content.Context
import android.content.res.TypedArray
import com.abduqodirov.invitex.MembersManager
import kotlin.math.roundToInt

//@BindingAdapter("chizilgan")
//fun TextView.setChizilgan(mehmon: Mehmon?) {
//    mehmon?.let {
//        paintFlags = if (mehmon.isAytilgan)
//            paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//        else
//            0
//    }
//}

//@BindingAdapter("memberBackground")
//fun ConstraintLayout.setMemberBackground(member: Mehmon?) {
//
//    member?.let {
//
//        setBackgroundColor(
//            getColorFromMembers(
//                it.ism,
//                resources.obtainTypedArray(R.array.mezbonColors)
//            )
//        )
//    }
//
//}

private fun getColorFromMembers(memberIsm: String, colors: TypedArray): Int {

    val indexOfMember = MembersManager.members.getValue(memberIsm)

    val color: Int = colors.getColor(indexOfMember, 0)

    return color
}

//@BindingAdapter("collapsed")
//fun ConstraintLayout.setCollapsed(mehmon: Mehmon?) {
//    mehmon?.let {
//
//        layoutParams.height = if (mehmon.isCollapsed) {
//            0
//        } else {
//            Constraints.LayoutParams.WRAP_CONTENT
//        }
//
//    }
//}

fun dpToPx(dp: Int, context: Context): Int {

    val density: Float = context.resources.displayMetrics.density

    return (dp * density).roundToInt()
}