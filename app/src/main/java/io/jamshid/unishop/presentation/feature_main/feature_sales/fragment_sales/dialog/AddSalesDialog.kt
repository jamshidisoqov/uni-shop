package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import io.jamshid.unishop.R
import io.jamshid.unishop.common.extension_functions.getOnlyDigits
import io.jamshid.unishop.databinding.DilogAddProductBinding
import io.jamshid.unishop.domain.models.Product
import io.jamshid.unishop.domain.models.transfers.BasketProductModel
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales.SalesViewModel
import io.jamshid.unishop.utils.MaskWatcherNothing
import io.jamshid.unishop.utils.MaskWatcherPayment

//TZ
/*quantity kichik yoki teng
*satilip atrgan summa minimumdan kop max az boliwi kerak
*/

class AddSalesDialog(
    private val viewModel: SalesViewModel,
    private var product: Product
) : DialogFragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        return DilogAddProductBinding.inflate(inflater, container, false).apply {

            tvMax.text = "Максимальная цена:${product.maximumPrice}"
            tvMin.text = "Минимальная цена:${product.minimumPrice}"
            tvCost.text = "Себестоимость:${product.price}"
            edPrices.setText(product.maximumPrice.toString())

            imageView2.setOnClickListener {
                if (tvMax.isVisible) {
                    tvMin.visibility = View.GONE
                    tvMax.visibility = View.GONE
                    imageView2.setImageResource(R.drawable.ic_add)
                } else {
                    tvMin.visibility = View.VISIBLE
                    tvMax.visibility = View.VISIBLE
                    imageView2.setImageResource(R.drawable.ic_close)
                }
            }


            edPrices.addTextChangedListener(MaskWatcherPayment(edPrices))
            edCountProduct.addTextChangedListener(MaskWatcherNothing(edCountProduct))

            btnAddBasket.setOnClickListener {
                var quantity = 0
                if (edCountProduct.text.toString().isNotEmpty())
                    quantity = edCountProduct.text.toString().getOnlyDigits().toInt()
                val cost = edPrices.text.toString().getOnlyDigits().toDouble()
                if (cost >= product.minimumPrice && cost <= product.maximumPrice) {
                    if (quantity > 0 && quantity <= product.quantity) {
                        viewModel.addProduct(
                            BasketProductModel(
                                id = product.id,
                                product = product,
                                quantity = quantity,
                                cost = cost
                            )
                        )
                        dialog!!.dismiss()
                    } else {
                        edCountProduct.error = "Mahsulot soni notog'ri kiritildi"
                    }
                } else {
                    edPrices.error = "Mahsulot summasi notog'ri kiritildi"
                }
            }

            btnCloseDialog.setOnClickListener {
                dialog!!.dismiss()
            }
        }.root
    }
}
