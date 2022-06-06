package io.jamshid.unishop.presentation.feature_main.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import io.jamshid.unishop.R
import io.jamshid.unishop.databinding.DialogSuccessBinding

class SuccessDialog(private val message: String) : DialogFragment(R.layout.dialog_success) {
    private lateinit var binding: DialogSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.shape_dialog)
        return inflater.inflate(R.layout.dialog_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DialogSuccessBinding.bind(view)

        binding.apply {
            tvMessage.text = message

            btnClose.setOnClickListener {
                onPositiveButtonClick.invoke()
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss.invoke()
    }

    private var onPositiveButtonClick: () -> Unit = {}
    fun setOnPositiveButtonClickListener(onPositiveButtonClick: () -> Unit) {
        this.onPositiveButtonClick = onPositiveButtonClick
    }

    private var onDismiss: () -> Unit = {}
    fun setOnDismissListener(onDismiss: () -> Unit) {
        this.onDismiss = onDismiss
    }
}
