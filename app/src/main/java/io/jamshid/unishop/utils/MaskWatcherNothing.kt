package io.jamshid.unishop.utils

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.jamshid.unishop.R
import java.util.*

class MaskWatcherNothing(private val editText: EditText) : TextWatcher {
    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    @SuppressLint("SetTextI18n")
    override fun afterTextChanged(editable: Editable) {
        try {
            editText.removeTextChangedListener(this)
            val value: String = editText.text.toString()
            val dimen: String = editText.context?.getString(R.string.count)!!
            if (value != "") {
                if (value.startsWith(".")) {
                    editText.setText("0.")
                }
                if (value.startsWith("0") && !value.startsWith("0.")) {
                    editText.setText("")
                }

                val str: String = editText.text.toString().replace(" ", "")
                if (str == editText.context?.getString(R.string.count)) editText.setText("")
                else {
                    var d = ""
                    for (i in str) if (i.isDigit()) d += i
                    val count = d.toDecimalFormat()
                    editText.setText(editText.context?.getString(R.string.count_text, count))
                }
                editText.setSelection(editText.text.toString().length - dimen.length - 1)
            }
            editText.addTextChangedListener(this)
            return
        } catch (ex: Exception) {
            ex.printStackTrace()
            editText.addTextChangedListener(this)
        }
    }

    private fun String.toDecimalFormat(): String {
        val lst = StringTokenizer(this, ".")
        var str1 = this
        var str2 = ""
        if (lst.countTokens() > 1) {
            str1 = lst.nextToken()
            str2 = lst.nextToken()
        }
        var str3 = ""
        var i = 0
        var j = -1 + str1.length
        if (str1[-1 + str1.length] == '.') {
            j--
            str3 = "."
        }
        var k = j
        while (true) {
            if (k < 0) {
                if (str2.isNotEmpty()) str3 = "$str3.$str2"
                return str3
            }
            if (i == 3) {
                str3 = " $str3"
                i = 0
            }
            str3 = str1[k].toString() + str3
            i++
            k--
        }
    }
}
