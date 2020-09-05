package vn.com.qqbee.customer

import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.com.qqbee.R
import vn.com.qqbee.core.utils.recycler.RecyclerBaseAdapter
import vn.com.qqbee.customer.entities.Customer
import vn.com.qqbee.trimFalse
import kotlinx.android.synthetic.main.fragment_customer.*
import kotlinx.android.synthetic.main.item_view_customer.view.*

class CustomerAdapter(
    private val fragment: CustomerFragment,
    items: ArrayList<Any>
) : RecyclerBaseAdapter(items, fragment.rv) {

    companion object {
        const val TAG: String = "CustomerAdapter"

        private const val VIEW_TYPE_ITEM = 0
    }

    private val rowItems: ArrayList<Customer> = ArrayList(
        items.filterIsInstance<Customer>()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = inflater.inflate(
                    R.layout.item_view_customer,
                    parent,
                    false
                )
                return CustomerViewHolder(view)
            }
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(baseHolder: RecyclerView.ViewHolder, basePosition: Int) {
        super.onBindViewHolder(baseHolder, basePosition)
        val position = baseHolder.adapterPosition
        when (getItemViewType(basePosition)) {
            VIEW_TYPE_ITEM -> {
                val holder = baseHolder as CustomerViewHolder
                val item = items[position] as Customer

                val imageSmall = item.imageSmall.trimFalse()
                val name = item.name.trimFalse()

                fragment.glideRequests.asBitmap().load(
                    if (imageSmall.isNotEmpty())
                        Base64.decode(imageSmall, Base64.DEFAULT)
                    else
                        fragment.activity.app.getLetterTile(if (name.isNotEmpty()) name else "X")
                ).dontAnimate().circleCrop().into(holder.itemView.imageSmall)

                holder.itemView.name.text = name
                holder.itemView.parent_name.text = item.parentName.trimFalse()
                val phone = item.phone.trimFalse()
                val mobile = item.mobile.trimFalse()
                holder.itemView.phonemobile.text = phone + (if (phone.isNotEmpty() and mobile.isNotEmpty()) " / " else "") + mobile

                if (!holder.itemView.clRoot.hasOnClickListeners()) {
                    holder.itemView.clRoot.setOnClickListener {
                        // val clickedPosition = holder.adapterPosition
                        // val clickedItem = items[clickedPosition] as Customer
                    }
                }
            }
        }
    }

    val rowItemCount: Int get() = rowItems.size

    override fun getItemViewType(position: Int): Int {
        val o = items[position]
        if (o is Customer) {
            return VIEW_TYPE_ITEM
        }
        return super.getItemViewType(position)
    }

    private fun updateRowItems() {
        updateSearchItems()
        rowItems.clear()
        rowItems.addAll(
            ArrayList(
                items.filterIsInstance<Customer>()
            )
        )
    }

    fun addRowItems(rowItems: ArrayList<Customer>) {
        this.rowItems.addAll(rowItems)
        addAll(rowItems.toMutableList<Any>() as ArrayList<Any>)
    }

    override fun clear() {
        rowItems.clear()
        super.clear()
    }
}
