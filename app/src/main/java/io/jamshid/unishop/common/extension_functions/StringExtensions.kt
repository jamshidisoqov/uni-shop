package io.jamshid.unishop.common.extension_functions

import android.app.Activity
import android.content.Intent
import android.net.Uri

// Created by Isoqov Jamshid on 5/13/2022.

fun String.getOnlyDigits(): String {
    val builder = StringBuilder()
    for (i in this) {
        if (i == '.') break
        if (i.isDigit()) {
            builder.append(i)
        }
    }
    return builder.toString()
}


fun String.getDateFormat(): String {
    return this.substring(0, 19) + " " + this.substring(30)
}

fun String.dialPhone(activity: Activity) {
    val phone = "+998$this"
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phone)))
    activity.startActivity(intent)
}

fun String.toSummFormat(): String {
    var text = this.getOnlyDigits().reversed()
    text = text.subSequence(0, text.length).chunked(3).joinToString(" ")
    return text.reversed()+" UZS"
}