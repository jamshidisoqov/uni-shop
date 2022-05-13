package io.jamshid.unishop.presentation.feature_main.feature_products.fragment_add_product.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import io.jamshid.unishop.R
import io.jamshid.unishop.databinding.DialogCategoryFragmentBinding
import io.jamshid.unishop.domain.models.Category
import io.jamshid.unishop.presentation.feature_main.feature_products.fragment_add_product.AddProductViewModel

class NewCategoryDialog(
    private val viewModel: AddProductViewModel
) : DialogFragment() {

    private var _binding: DialogCategoryFragmentBinding? = null
    private val binding: DialogCategoryFragmentBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        _binding = DialogCategoryFragmentBinding.inflate(inflater, container, false).apply {
            btnAddCategory.setOnClickListener {
                val categoryName = edCategoryName.text.toString()
                viewModel.addCategory(Category(0, categoryName))
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}